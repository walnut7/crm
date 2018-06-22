package com.kpleasing.crm.ejb.pojo;

import java.io.Serializable;

public class BankInfo implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -6199467287731042491L;
	private String account_name;
	private String account_id;
	private String account_no;
	private String short_account_no;
	private String bank_name;
	private String branch_bank_name;
	private String bank_code;
	private String bank_phone;
	private String withhold_unit;
	private String is_withhold_acc;
	private String is_yjzf_bind;
	
	public String getAccount_name() {
		return account_name;
	}
	
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
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

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBranch_bank_name() {
		return branch_bank_name;
	}

	public void setBranch_bank_name(String branch_bank_name) {
		this.branch_bank_name = branch_bank_name;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_phone() {
		return bank_phone;
	}

	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}

	public String getWithhold_unit() {
		return withhold_unit;
	}

	public void setWithhold_unit(String withhold_unit) {
		this.withhold_unit = withhold_unit;
	}

	public String getIs_withhold_acc() {
		return is_withhold_acc;
	}

	public void setIs_withhold_acc(String is_withhold_acc) {
		this.is_withhold_acc = is_withhold_acc;
	}

	public String getIs_yjzf_bind() {
		return is_yjzf_bind;
	}

	public void setIs_yjzf_bind(String is_yjzf_bind) {
		this.is_yjzf_bind = is_yjzf_bind;
	}
}
