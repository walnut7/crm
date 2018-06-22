package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;

public class ResponseSyncTMCustomer extends Response implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -6071925312650712957L;
	
	private String result_code;
	
	private String result_desc;

	private String cust_id;
	
	private String account_id;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
}
