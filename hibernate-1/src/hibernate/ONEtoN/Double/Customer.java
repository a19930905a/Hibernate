package hibernate.ONEtoN.Double;

import java.util.HashSet;
import java.util.Set;

public class Customer {

	private Integer customer_id;
	private String customer_name;
	
	//���V�@��h
	/**
	 * 	�`�N:
	 * 	1.�n�����X�ɭn�ϥα��f����,�]���̫� Hibernate ����ɱN��^���O Hibernate ���m�����X�ݩ�
	 * 	2.�i���l�ƥH�קKNullPointer���`
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
