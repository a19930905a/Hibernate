package hibernate.ONEtoONE.primary;

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
		Department department=new Department();
		department.setDepartment_name("飆仔");
		Manager manager =new Manager();
		manager.setManager_name("Alan");
		//設定關聯關係
		manager.setDepartment(department);
		department.setManager(manager);
		
		session.save(manager);
		session.save(department);
	}

	@Test
	public void testGet() {
		Department department=session.get(Department.class, 1);
		System.out.println(department.getDepartment_name());
		Manager manager = session.get(Manager.class, 1);
		System.out.println(manager.getManager_name());
	}
}
