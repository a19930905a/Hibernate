package hibernate.NtoN;

import java.util.HashSet;
import java.util.Set;

public class Category {

	private Integer C_id;
	private String C_name;
	
	private Set<Item> items = new HashSet();

	public Integer getC_id() {
		return C_id;
	}

	public void setC_id(Integer c_id) {
		C_id = c_id;
	}

	public String getC_name() {
		return C_name;
	}

	public void setC_name(String c_name) {
		C_name = c_name;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}


	
	
}
