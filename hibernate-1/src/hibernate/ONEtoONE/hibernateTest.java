package hibernate.ONEtoONE;

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
	/**
	 * 	Get:需注意one-to-one要到hbm.xml文件中設置property-ref屬性已指定關聯字段
	 * 	P.S.
	 * 	若沒設定,在獲取時會使用關聯的主鍵->manager0_.MANAGER_ID=department1_.DEPARTMENT_ID 
	 * 	但很明顯,我們需要獲取的是->manager0_.MANAGER_ID=department1_.MANAGER_ID
	 * */
	@Test
	public void testGet() {
//		Department department=session.get(Department.class, 1);
//		System.out.println(department.getDepartment_name());
		Manager manager = session.get(Manager.class, 1);
		System.out.println(manager.getManager_name());
	}
}
