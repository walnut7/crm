package com.kpleasing.crm.ejb.pojo;

import java.util.List;

public class Item {
	
	private String name;
	private String label;
	private String url;
	private List<Security> securitys;
	
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Security> getSecuritys() {
		return securitys;
	}
	
	public void setSecuritys(List<Security> securitys) {
		this.securitys = securitys;
	}
}
