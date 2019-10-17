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
		Department department=new Department();
		department.setDepartment_name("�t�J");
		Manager manager =new Manager();
		manager.setManager_name("Alan");
		//�]�w���p���Y
		manager.setDepartment(department);
		department.setManager(manager);
		
		session.save(manager);
		session.save(department);
	}
	/**
	 * 	Get:�ݪ`�None-to-one�n��hbm.xml��󤤳]�mproperty-ref�ݩʤw���w���p�r�q
	 * 	P.S.
	 * 	�Y�S�]�w,�b����ɷ|�ϥ����p���D��->manager0_.MANAGER_ID=department1_.DEPARTMENT_ID 
	 * 	���ܩ���,�ڭ̻ݭn������O->manager0_.MANAGER_ID=department1_.MANAGER_ID
	 * */
	@Test
	public void testGet() {
//		Department department=session.get(Department.class, 1);
//		System.out.println(department.getDepartment_name());
		Manager manager = session.get(Manager.class, 1);
		System.out.println(manager.getManager_name());
	}
}
