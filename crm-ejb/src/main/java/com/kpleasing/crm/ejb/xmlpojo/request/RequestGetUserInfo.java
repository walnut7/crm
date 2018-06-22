package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestGetUserInfo extends Request implements Serializable {

	/**	 **/
	private static final long serialVersionUID = 965944298147144364L;
	
	private String cust_id;

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
}
