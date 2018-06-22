package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestSyncContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3597619369444140787L;

	private String cid;

	private String contact_name;

	private String is_important_contact;

	private String contact_cert_type;

	private String contact_cert_code;

	private String relation;

	private String contact_work_unit;

	private String contact_email;

	private String contact_fax;

	private String contact_phone;

	private String contact_addr;

	private String is_send_sms;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getIs_important_contact() {
		return is_important_contact;
	}

	public void setIs_important_contact(String is_important_contact) {
		this.is_important_contact = is_important_contact;
	}

	public String getContact_cert_type() {
		return contact_cert_type;
	}

	public void setContact_cert_type(String contact_cert_type) {
		this.contact_cert_type = contact_cert_type;
	}

	public String getContact_cert_code() {
		return contact_cert_code;
	}

	public void setContact_cert_code(String contact_cert_code) {
		this.contact_cert_code = contact_cert_code;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getContact_work_unit() {
		return contact_work_unit;
	}

	public void setContact_work_unit(String contact_work_unit) {
		this.contact_work_unit = contact_work_unit;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getContact_fax() {
		return contact_fax;
	}

	public void setContact_fax(String contact_fax) {
		this.contact_fax = contact_fax;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_addr() {
		return contact_addr;
	}

	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
	}

	public String getIs_send_sms() {
		return is_send_sms;
	}

	public void setIs_send_sms(String is_send_sms) {
		this.is_send_sms = is_send_sms;
	}
	
}
