package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;

public class ResponseGetUserProfile extends Response implements Serializable {

	/**	**/
	private static final long serialVersionUID = -2610386925341744643L;
	private String result_code;
	private String result_desc;
	private String cust_id;
	private String cust_name;
	private String phone;
	private String cert_type;
	private String cert_code;
	private String wx_id;


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

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getPhone() {
		if (null==this.phone) {
			return "";
		}
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCert_type() {
		if (null==this.cert_type) {
			return "";
		}
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_code() {
		if (null==this.cert_code) {
			return "";
		}
		return cert_code;
	}

	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	public String getWx_id() {
		if (null==this.wx_id) {
			return "";
		}
		return wx_id;
	}

	public void setWx_id(String wx_id) {
		this.wx_id = wx_id;
	}
}
