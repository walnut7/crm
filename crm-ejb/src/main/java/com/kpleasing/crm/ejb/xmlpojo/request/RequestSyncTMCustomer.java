package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestSyncTMCustomer extends Request implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -2473325109223726885L;
	
	private String cust_name;
	
	private String phone;
	
	private String cert_type;
	
	private String cert_code;
	
	private String alipay_no;

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_code() {
		return cert_code;
	}

	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	public String getAlipay_no() {
		return alipay_no;
	}

	public void setAlipay_no(String alipay_no) {
		this.alipay_no = alipay_no;
	}
}
