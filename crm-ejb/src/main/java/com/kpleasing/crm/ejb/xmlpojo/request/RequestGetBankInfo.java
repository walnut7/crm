package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestGetBankInfo extends Request implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 2630133868646185315L;
	private String cust_id;
	private String account;

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
