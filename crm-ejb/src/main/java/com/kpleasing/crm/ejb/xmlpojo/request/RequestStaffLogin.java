package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestStaffLogin extends Request implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -2480510550822226068L;
	
	private String appcode;
	
	private String login_id;
	
	private String password;

	public String getAppcode() {
		return appcode;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
