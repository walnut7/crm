package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestSetBankInfo extends Request implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -1694546051497453298L;
	private String cust_id;
	private String account_name;
	private String account_no;
	private String short_account_no;
	private String bank_phone;
	private String is_yjzf_bind;
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getShort_account_no() {
		return short_account_no;
	}

	public void setShort_account_no(String short_account_no) {
		this.short_account_no = short_account_no;
	}

	public String getBank_phone() {
		return bank_phone;
	}

	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}

	public String getIs_yjzf_bind() {
		return is_yjzf_bind;
	}

	public void setIs_yjzf_bind(String is_yjzf_bind) {
		this.is_yjzf_bind = is_yjzf_bind;
	}
}
