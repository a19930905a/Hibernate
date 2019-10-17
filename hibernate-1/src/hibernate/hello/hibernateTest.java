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
		//1.建立 Configuartion 讀取 cfg.xml 文件
		Configuration configuration= new Configuration().configure();
		//2.建立工廠
		sessionFactory=configuration.buildSessionFactory();
		//3.從工廠讀取函數
		session = sessionFactory.openSession();
		//4.使用函數建立事務
		transaction = session.beginTransaction();
	}
	
	@AfterEach
	public void destroy() {
		//6.提交事務
		transaction.commit();
		//7.關閉資源
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void test() {
		//5.使用函數
		News news = new News("hibernate","Alan",new Date(new java.util.Date().getTime()));
		session.save(news);
	}
	/**
	 * get , load 區別
	 * 1.get為立即檢索 , load 為延遲檢索  ( load使用代理對象 )
	 * 
	 * 2.load可能會拋出 LayerInstantiationException , 異常:在初始化代理對象前卻已經關閉 Session
	 * 
	 * 3. 若表中無對應紀錄
	 *   get 會返回null
	 * 	 load 會在初始化對象時拋出異常,但若不使用該對象任何屬性則不會拋出異常
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
		System.out.println(news.getClass().getName());//代理對象hibernate.hello.News_$$_jvst959_0
		System.out.println(news);
	}
	
	/**
	 * 	save 和 persist :兩者皆執行INSERT方法
	 *	
	 *	區別:	
	 *	在使用save方法前若設置ID,仍會執行INSERT方法
	 *	但在使用persist方法前若設置ID,則會拋出異常
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
	 * 1.更新持久化對象時,並不需要調用update方法,因為在Transaction.commit()時會先執行 flush
	 * flush:確認提交前該表狀態和session緩存中的狀態一不一致,若不一致就會使用update方法
	 * 2.更新一個游離對象就必須使用update方法,update方法可以將游離對象變成持久化對象
	 * 
	 * 	注意:
	 * 	1.若數據表中無對應數據,使用update則會拋出異常
	 * 	2.在update關聯到一個遊離對象時,若session的緩存中有相同ID的持久化對象將會拋出異常
	 * */
	@Test
	public void testUpdate() {
		News news = session.get(News.class,1);
		news.setAuthor("Alan");
		news.setTitle("Hibernate");
	}
	/**
	 * Formula屬性(hbm.xml):
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
		//設定info
		info.setDetail("測試用");
		info.setVersion("1.0.0");
		//設定news
		news.setAuthor("Alan");
		news.setTitle("Java");
		news.setDate(new Date(new java.util.Date().getTime()));
		news.setInfo(info);
		
		session.save(news);
	}
}
