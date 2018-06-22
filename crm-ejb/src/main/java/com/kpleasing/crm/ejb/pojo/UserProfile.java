package com.kpleasing.crm.ejb.pojo;

import java.io.Serializable;

public class UserProfile implements Serializable {
	
	/**	 * 	 */
	private static final long serialVersionUID = 4621885582568403776L;
	
	private int staffId;

	private String username;
	
	private String password;
	
	private String loginStatus;
	
	private String loginInfo;

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}
}
