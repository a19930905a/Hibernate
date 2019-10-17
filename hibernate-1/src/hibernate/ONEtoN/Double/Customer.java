package hibernate.ONEtoN.Double;

import java.util.HashSet;
import java.util.Set;

public class Customer {

	private Integer customer_id;
	private String customer_name;
	
	//雙向一對多
	/**
	 * 	注意:
	 * 	1.聲明集合時要使用接口類型,因為最後 Hibernate 獲取時將返回的是 Hibernate 內置的集合屬性
	 * 	2.進行初始化以避免NullPointer異常
	 * */
	private Set<Order> orders = new HashSet<>();
	
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	
	
}
