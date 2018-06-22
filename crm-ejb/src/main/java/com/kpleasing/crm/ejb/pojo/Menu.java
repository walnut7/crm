package com.kpleasing.crm.ejb.pojo;

import java.util.List;

public class Menu {
	
	private String name;
	private String label;
	private String ref;
	private List<Menu> sub_menus;
	private List<Item> items;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public List<Menu> getSub_menus() {
		return sub_menus;
	}

	public void setSub_menus(List<Menu> sub_menus) {
		this.sub_menus = sub_menus;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
