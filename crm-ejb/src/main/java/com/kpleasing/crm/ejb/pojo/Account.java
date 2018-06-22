package com.kpleasing.crm.ejb.pojo;

import java.io.Serializable;

public class Account implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 1088487881320016352L;
	
	private String acc_name;
	
	private String acc_no;
	
	private String bank_code;
	
	private String bank_full_name;
	
	private String branch_bank_name;
	
	private String currency;
	
	private String withhold_unit;
	
	private String is_withhold_acc;
	
	private String acc_status;

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_full_name() {
		return bank_full_name;
	}

	public void setBank_full_name(String bank_full_name) {
		this.bank_full_name = bank_full_name;
	}

	public String getBranch_bank_name() {
		return branch_bank_name;
	}

	public void setBranch_bank_name(String branch_bank_name) {
		this.branch_bank_name = branch_bank_name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getAcc_status() {
		return acc_status;
	}

	public void setAcc_status(String acc_status) {
		this.acc_status = acc_status;
	}


}
