package com.kpleasing.crm.ejb.xmlpojo.response;

public class Response {
	private String return_code;
	private String return_desc;
	private String req_serial_no;
	private String req_date;
	private String res_serial_no;
	private String res_date;
	private String sign;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_desc() {
		return return_desc;
	}

	public void setReturn_desc(String return_desc) {
		this.return_desc = return_desc;
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

	public String getRes_serial_no() {
		return res_serial_no;
	}

	public void setRes_serial_no(String res_serial_no) {
		this.res_serial_no = res_serial_no;
	}

	public String getRes_date() {
		return res_date;
	}

	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
