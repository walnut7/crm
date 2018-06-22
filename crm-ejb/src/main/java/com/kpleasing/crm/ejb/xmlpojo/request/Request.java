package com.kpleasing.crm.ejb.xmlpojo.request;

public class Request {
	
	private String api_code;
	
	private String req_serial_no;
	
	private String req_date;
	
	private String security_code;
	
	private String security_value;
	
	private String sign;

	public String getApi_code() {
		return api_code;
	}

	public void setApi_code(String api_code) {
		this.api_code = api_code;
	}

	public String getReq_serial_no() {
		return req_serial_no;
	}

	public void setReq_serial_no(String req_serial_no) {
		this.req_serial_no = req_serial_no;
	}

	public String getReq_date() {
		return req_date;
	}

	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getSecurity_value() {
		return security_value;
	}

	public void setSecurity_value(String security_value) {
		this.security_value = security_value;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
