package hibernate.ONEtoN.Double;

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
 *  	雙向多對一關聯Test
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
		//設置關聯關係
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);

		
		session.save(customer);
		session.save(order1);
		session.save(order2);
	}

	/**
	 * 	Get:對"多"的一端會使用延遲加載,並且返回值為 class org.hibernate.collection.internal.PersistentSet
	 * 		是Hibernate內置的集合對象,該對象具有延遲加載及代理對象的功能
	 * 
	 * */
	@Test
	public void testGet() {
		Customer customer =session.get(Customer.class,1);
		System.out.println(customer.getCustomer_name());
		
		System.out.println(customer.getOrders().getClass());
	}


}
