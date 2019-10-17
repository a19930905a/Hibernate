package hibernate.hello;

import java.sql.Date;

public class News {

	private Integer id;
	private String title;
	private String author;
	
	private Date date;
	//實作 hbm.xml formula 屬性
	private String test_formula;
	//實作 hbm.xml component
	private Info info;

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getTest_formula() {
		return test_formula;
	}

	public void setTest_formula(String test_formula) {
		this.test_formula = test_formula;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public News(String title, String author, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
	}

	public News() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", test_formula="
				+ test_formula + ", info=" + info + "]";
	}


	
	
}
