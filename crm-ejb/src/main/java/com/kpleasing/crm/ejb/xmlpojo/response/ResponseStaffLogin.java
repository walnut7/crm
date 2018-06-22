package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;

public class ResponseStaffLogin extends Response implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -4140537602340986667L;

	private String result_code;
	
	private String result_desc;
	
	private String staff_name;
	
	private String staff_no;
	
	private String staff_email;
	
	private String is_modify_pwd;
	
	private String phone;
	

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

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getStaff_email() {
		return staff_email;
	}

	public void setStaff_email(String staff_email) {
		this.staff_email = staff_email;
	}

	public String getIs_modify_pwd() {
		return is_modify_pwd;
	}

	public void setIs_modify_pwd(String is_modify_pwd) {
		this.is_modify_pwd = is_modify_pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
