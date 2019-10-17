package hibernate.HQL;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 *  	HQL + �G�Žw�s
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
	public void testHQL() {
		//HQL�y�y
		String hql="FROM Employee e Where e.salary >:min_salary AND e.department = :id_dep";
		//�Ы�Query
		Query query = session.createQuery(hql);
		//Set department
		My_Department department = new My_Department();
		department.setId(2);
		//�t�m�d�߼ƾ�
		query.setInteger("min_salary", 5000)
			 .setEntity("id_dep", department);
		
		List<Employee> list = query.list();
		System.out.println(list);
	}
	/**
	 *  	�����d��
	 * */
	@Test
	public void testHQLpage() {
		String hql="FROM Employee";
		Query query =session.createQuery(hql);
		
		int page=0;
		int page_size=3;
		
		query.setFirstResult(page*page_size)
			 .setMaxResults(page_size);
		List<Employee> list = query.list();
		
		System.out.println(list);
		
	}
	/**
	 * 		�R�Wquery���gHQL
	 * */
	
	@Test
	public void testHQLName() {
		Query query =session.getNamedQuery("salaryEmps");
		
		query.setInteger("minVal", 5000)
			 .setInteger("maxVal", 10000);
	
		List<Employee> list = query.list();
		System.out.println(list);
		
	}
	/**
	 * 	��v�d��
	 * */
	@Test
	public void testFieldQuery() {
		String hql = "SELECT e.name,e.salary FROM Employee e WHERE e.department = ?";
		Query query = session.createQuery(hql);
		
		My_Department department = new My_Department();
		department.setId(1);
		
		query.setEntity(0,department);
		List<Object[]> list = query.list();
		for(Object[] obj:list) {	
			System.out.println(Arrays.asList(obj));
		}
	}
	/**
	 * 		����d��
	 * */
	@Test
	public void testGroupBy() {
		String hql = "Select min(e.salary) , max(e.salary),avg(e.salary),e.department.name From Employee e "
				+ "Group By e.department Having min(salary) > :minSal";
		Query query = session.createQuery(hql);
		query.setInteger("minSal", 1000);

		List<Object[]> list = query.list();
		
		for(Object[] obj:list) {
			System.out.println(Arrays.asList(obj));
		}
	}
	/**
	 * 		�������~�s��
	 * 	
	 * 	�`�N:�ݨϥ� DISTINCT �h��
	 * 
	 * 
	 * */
	@Test
	public void testLeftJoinFetch() {
		String hql = "SELECT DISTINCT m FROM My_Department m Left Join Fetch m.employee";
		Query query = session.createQuery(hql);
		List<Employee> list = query.list();
		
		System.out.println(list.size());
	}
	/**
	 * 		�������s��
	 * */
	@Test
	public void testInnerJoinFetch() {
		String hql = "SELECT DISTINCT e FROM Employee e Inner Join Fetch e.department";
		Query query = session.createQuery(hql);
		
		List<Employee> list = query.list();
		
		System.out.println(list.size());
		for(Employee employee : list) {
			System.out.println(employee.getName()+","+employee.getSalary()+","+employee.getDepartment().getName());
		}
	}
	/**
	 * 	�ɶ��W�w�s��
	 * 
	 * */
	
	@Test
	public void testUpdateTimeStampCache() {
		String hql = "SELECT DISTINCT m FROM My_Department m Left Join Fetch m.employee";
		Query query = session.createQuery(hql);
		query.setCacheable(true);
		
		List<Employee> list = query.list();
		
		System.out.println(list.size());
		Employee employee = session.get(Employee.class, 1);
		employee.setSalary(16000);
		
		list = query.list();
		
		System.out.println(list.size());
	}
	
}