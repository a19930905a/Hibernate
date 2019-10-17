package hibernate.hello;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class hibernateTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@BeforeEach
	public void init() {
		//1.�إ� Configuartion Ū�� cfg.xml ���
		Configuration configuration= new Configuration().configure();
		//2.�إߤu�t
		sessionFactory=configuration.buildSessionFactory();
		//3.�q�u�tŪ�����
		session = sessionFactory.openSession();
		//4.�ϥΨ�ƫإߨư�
		transaction = session.beginTransaction();
	}
	
	@AfterEach
	public void destroy() {
		//6.����ư�
		transaction.commit();
		//7.�����귽
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void test() {
		//5.�ϥΨ��
		News news = new News("hibernate","Alan",new Date(new java.util.Date().getTime()));
		session.save(news);
	}
	/**
	 * get , load �ϧO
	 * 1.get���ߧY�˯� , load �������˯�  ( load�ϥΥN�z��H )
	 * 
	 * 2.load�i��|�ߥX LayerInstantiationException , ���`:�b��l�ƥN�z��H�e�o�w�g���� Session
	 * 
	 * 3. �Y���L��������
	 *   get �|��^null
	 * 	 load �|�b��l�ƹ�H�ɩߥX���`,���Y���ϥθӹ�H�����ݩʫh���|�ߥX���`
	 * 
	 * */
	@Test
	public void testGet() {
		News news = session.get(News.class,1);
		System.out.println(news);
	}
	
	@Test
	public void testLoad() {
		News news = session.load(News.class, 2);
		System.out.println(news.getClass().getName());//�N�z��Hhibernate.hello.News_$$_jvst959_0
		System.out.println(news);
	}
	
	/**
	 * 	save �M persist :��̬Ұ���INSERT��k
	 *	
	 *	�ϧO:	
	 *	�b�ϥ�save��k�e�Y�]�mID,���|����INSERT��k
	 *	���b�ϥ�persist��k�e�Y�]�mID,�h�|�ߥX���`
	 * */
	@Test
	public void testSave() {
		News news = new News("Spring","Alan",new Date(new java.util.Date().getTime()));
		session.save(news);
	}
	@Test
	public void testPersist() {
		News news = new News("Spring","Alan",new Date(new java.util.Date().getTime()));
		news.setId(10);			//org.hibernate.PersistentObjectException
		session.persist(news);
	}

	/**
	 * update:
	 * 1.��s���[�ƹ�H��,�ä��ݭn�ե�update��k,�]���bTransaction.commit()�ɷ|������ flush
	 * flush:�T�{����e�Ӫ��A�Msession�w�s�������A�@���@�P,�Y���@�P�N�|�ϥ�update��k
	 * 2.��s�@�Ӵ�����H�N�����ϥ�update��k,update��k�i�H�N������H�ܦ����[�ƹ�H
	 * 
	 * 	�`�N:
	 * 	1.�Y�ƾڪ��L�����ƾ�,�ϥ�update�h�|�ߥX���`
	 * 	2.�bupdate���p��@�ӹC����H��,�Ysession���w�s�����ۦPID�����[�ƹ�H�N�|�ߥX���`
	 * */
	@Test
	public void testUpdate() {
		News news = session.get(News.class,1);
		news.setAuthor("Alan");
		news.setTitle("Hibernate");
	}
	/**
	 * Formula�ݩ�(hbm.xml):
	 * (SELECT CONCAT(ID,':',title) FROM news n WHERE n.id = id)
	 * 
	 * */
	@Test
	public void testFormula() {
		News news = session.get(News.class,1);
		System.out.println(news.getTest_formula());
	}
	/**
	 * component
	 * 
	 * */
	@Test
	public void testComponent() {
		News news = new News();
		Info info = new Info();
		//�]�winfo
		info.setDetail("���ե�");
		info.setVersion("1.0.0");
		//�]�wnews
		news.setAuthor("Alan");
		news.setTitle("Java");
		news.setDate(new Date(new java.util.Date().getTime()));
		news.setInfo(info);
		
		session.save(news);
	}
}
