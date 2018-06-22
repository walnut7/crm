package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestSyncAccountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690160727494372987L;
	
	private String aid;
	
	private String acc_name;
	
	private String acc_no;
	
	private String bank_code;
	
	private String bank_full_name;
	
	private String branch_bank_name;
	
	private String bank_phone;
	
	private String abbreviation_card_no;
	
	private String is_yjzf_bind;
	
	private String currency;
	
	private String withhold_unit;
	
	private String is_withhold_acc;
	
	private String acc_status;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

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

	public String getBank_phone() {
		return bank_phone;
	}

	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
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

	public String getAbbreviation_card_no() {
		return abbreviation_card_no;
	}

	public void setAbbreviation_card_no(String abbreviation_card_no) {
		this.abbreviation_card_no = abbreviation_card_no;
	}

	public String getIs_yjzf_bind() {
		return is_yjzf_bind;
	}

	public void setIs_yjzf_bind(String is_yjzf_bind) {
		this.is_yjzf_bind = is_yjzf_bind;
	}
		
}
