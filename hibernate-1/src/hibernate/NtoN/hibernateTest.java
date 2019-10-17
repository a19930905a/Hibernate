package hibernate.NtoN;

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
 *  	���V�h��@���pTest
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
		Item item1 = new Item();
		item1.setI_name("�q����");
		Item item2 = new Item();
		item2.setI_name("�j����");
		
		Category category = new Category();
		category.setC_name("�q���Ϋ~");
		
		category.getItems().add(item1);
		category.getItems().add(item2);
		
		session.save(category);
		session.save(item1);
		session.save(item2);
	}
	
}
