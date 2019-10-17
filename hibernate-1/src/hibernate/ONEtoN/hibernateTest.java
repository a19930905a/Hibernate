package hibernate.ONEtoN;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 *  	多對一關聯Test
 * 
 * */

class hibernateTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@BeforeEach
	public void init() {
		Configuration configuration = new Configuration().configure();
		sessionFactory=configuration.buildSessionFactory();
		session=sessionFactory.openSession();
		transaction=session.beginTransaction();
	}
	
	@AfterEach
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	/**
	 * 	Save : 在執行 session.save 語句時,應該先使用於"1"的對象,若率先使用在"多"的對象將導致系統無法判別"多"的對象中
	 * 		   被相關聯"1"的對象的數值,而需要在最後多呼叫Update方法
	 * 
	 * */
	@Test
	public void testSave() {
		Customer customer=new Customer();
		customer.setCustomer_name("Alan");
		
		Order order1 = new Order();
		Order order2 = new Order();
		order1.setOrder_name("訂單1");
		order2.setOrder_name("訂單2");
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		session.save(customer);
		session.save(order1);
		session.save(order2);
	}
	/**
	 * 	Get:
	 * 	1.若只查詢"多"的那端時,只會查詢"多"的那端的對象,並不會查詢關聯的"1"對象
	 * 	2.在需要使用到關聯的對象時,才會發送SQL語句
	 * 	3.若在查詢"1"對象時,再由"多"的一端導向"1"的同時session被關閉,將導致LayerInstantiationException異常
	 * 	4.在獲取"多"對象時,默認情況下關聯的"1"將會是代理對象
	 * 
	 * */
	@Test
	public void testGet() {
		Order order=session.get(Order.class, 1);
		System.out.println(order.getOrder_name());
		
		System.out.println(order.getCustomer().getClass().getName());	//hibernate.ONEtoN.Customer_$$_jvstc0e_1
		
		//session.close();				將導致 LayerInstantiationException
		
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomer_name());
	}
	/**
	 * 	Delete:如果要刪掉"1"端的同時,該數據與"多"的那端有相關聯了話,將無法刪除
	 * 			org.hibernate.exception.ConstraintViolationException
	 * 			
	 * 			!!!除非在hbm.xml設置級聯(cascade)!!!
	 * */
	@Test
	public void testDelete() {
		Customer customer = session.get(Customer.class, 1);
		
		session.delete(customer);
		
	}

}
