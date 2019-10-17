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
 *  	�h��@���pTest
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
	 * 	Save : �b���� session.save �y�y��,���ӥ��ϥΩ�"1"����H,�Y�v���ϥΦb"�h"����H�N�ɭP�t�εL�k�P�O"�h"����H��
	 * 		   �Q�����p"1"����H���ƭ�,�ӻݭn�b�̫�h�I�sUpdate��k
	 * 
	 * */
	@Test
	public void testSave() {
		Customer customer=new Customer();
		customer.setCustomer_name("Alan");
		
		Order order1 = new Order();
		Order order2 = new Order();
		order1.setOrder_name("�q��1");
		order2.setOrder_name("�q��2");
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		session.save(customer);
		session.save(order1);
		session.save(order2);
	}
	/**
	 * 	Get:
	 * 	1.�Y�u�d��"�h"�����ݮ�,�u�|�d��"�h"�����ݪ���H,�ä��|�d�����p��"1"��H
	 * 	2.�b�ݭn�ϥΨ����p����H��,�~�|�o�eSQL�y�y
	 * 	3.�Y�b�d��"1"��H��,�A��"�h"���@�ݾɦV"1"���P��session�Q����,�N�ɭPLayerInstantiationException���`
	 * 	4.�b���"�h"��H��,�q�{���p�U���p��"1"�N�|�O�N�z��H
	 * 
	 * */
	@Test
	public void testGet() {
		Order order=session.get(Order.class, 1);
		System.out.println(order.getOrder_name());
		
		System.out.println(order.getCustomer().getClass().getName());	//hibernate.ONEtoN.Customer_$$_jvstc0e_1
		
		//session.close();				�N�ɭP LayerInstantiationException
		
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomer_name());
	}
	/**
	 * 	Delete:�p�G�n�R��"1"�ݪ��P��,�ӼƾڻP"�h"�����ݦ������p�F��,�N�L�k�R��
	 * 			org.hibernate.exception.ConstraintViolationException
	 * 			
	 * 			!!!���D�bhbm.xml�]�m���p(cascade)!!!
	 * */
	@Test
	public void testDelete() {
		Customer customer = session.get(Customer.class, 1);
		
		session.delete(customer);
		
	}

}
