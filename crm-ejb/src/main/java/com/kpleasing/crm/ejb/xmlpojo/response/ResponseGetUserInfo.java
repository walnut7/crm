package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;
import java.util.List;

import com.kpleasing.crm.ejb.pojo.Account;
import com.kpleasing.crm.ejb.pojo.Contact;

public class ResponseGetUserInfo extends Response implements Serializable {

	/** 	 **/
	private static final long serialVersionUID = 2980077498425816926L;

	private String result_code;
	
	private String result_desc;
	
	private String cust_name;
	
	private String cust_name_spell;
	
	private String cert_type;
	
	private String cert_code;
	
	private String phone;
	
	private String cust_type;
	
	private String channel_source;
	
	private String register_time;
	
	private String birthday;
	
	private String gender;
	
	private String nation;
	
	private String drive_model;
	
	private String annual_income;
	
	private String rel_flag;
	
	private String income_from;
	
	private String income_status;
	
	private String entry_year;
	
	private String live_status;
	
	private String work_unit;
	
	private String position;
	
	private String edu_level;
	
	private String marr_status;
	
	private String spouse_name;
	
	private String spouse_cert_type;
	
	private String spouse_cert_code;
	
	private String spouse_phone;
	
	private String spouse_income_from;
	
	private String spouse_annual_income;
	
	private String spouse_work_unit;
	
	private String spouse_contact_addr;
	
	private String industry;
	
	private String max_quota;
	
	private String company_nature;
	
	private String work_year;
	
	private String unit_tel;
	
	private String email;
	
	private String cert_org;
	
	private String regular_deposit_amt;
	
	private String zip_code;
	
	private String cert_addr;
	
	private String family_tel;
	
	private String contact_addr;
	
	private List<Contact> contacts;
	
	private List<Account> accounts;

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

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_name_spell() {
		return cust_name_spell;
	}

	public void setCust_name_spell(String cust_name_spell) {
		this.cust_name_spell = cust_name_spell;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getChannel_source() {
		return channel_source;
	}

	public void setChannel_source(String channel_source) {
		this.channel_source = channel_source;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDrive_model() {
		return drive_model;
	}

	public void setDrive_model(String drive_model) {
		this.drive_model = drive_model;
	}

	public String getAnnual_income() {
		return annual_income;
	}

	public void setAnnual_income(String annual_income) {
		this.annual_income = annual_income;
	}

	public String getRel_flag() {
		return rel_flag;
	}

	public void setRel_flag(String rel_flag) {
		this.rel_flag = rel_flag;
	}

	public String getIncome_from() {
		return income_from;
	}

	public void setIncome_from(String income_from) {
		this.income_from = income_from;
	}

	public String getIncome_status() {
		return income_status;
	}

	public void setIncome_status(String income_status) {
		this.income_status = income_status;
	}

	public String getEntry_year() {
		return entry_year;
	}

	public void setEntry_year(String entry_year) {
		this.entry_year = entry_year;
	}

	public String getLive_status() {
		return live_status;
	}

	public void setLive_status(String live_status) {
		this.live_status = live_status;
	}

	public String getWork_unit() {
		return work_unit;
	}

	public void setWork_unit(String work_unit) {
		this.work_unit = work_unit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEdu_level() {
		return edu_level;
	}

	public void setEdu_level(String edu_level) {
		this.edu_level = edu_level;
	}

	public String getMarr_status() {
		return marr_status;
	}

	public void setMarr_status(String marr_status) {
		this.marr_status = marr_status;
	}

	public String getSpouse_name() {
		return spouse_name;
	}

	public void setSpouse_name(String spouse_name) {
		this.spouse_name = spouse_name;
	}

	public String getSpouse_cert_type() {
		return spouse_cert_type;
	}

	public void setSpouse_cert_type(String spouse_cert_type) {
		this.spouse_cert_type = spouse_cert_type;
	}

	public String getSpouse_cert_code() {
		return spouse_cert_code;
	}

	public void setSpouse_cert_code(String spouse_cert_code) {
		this.spouse_cert_code = spouse_cert_code;
	}

	public String getSpouse_phone() {
		return spouse_phone;
	}

	public void setSpouse_phone(String spouse_phone) {
		this.spouse_phone = spouse_phone;
	}

	public String getSpouse_income_from() {
		return spouse_income_from;
	}

	public void setSpouse_income_from(String spouse_income_from) {
		this.spouse_income_from = spouse_income_from;
	}

	public String getSpouse_annual_income() {
		return spouse_annual_income;
	}

	public void setSpouse_annual_income(String spouse_annual_income) {
		this.spouse_annual_income = spouse_annual_income;
	}

	public String getSpouse_work_unit() {
		return spouse_work_unit;
	}

	public void setSpouse_work_unit(String spouse_work_unit) {
		this.spouse_work_unit = spouse_work_unit;
	}

	public String getSpouse_contact_addr() {
		return spouse_contact_addr;
	}

	public void setSpouse_contact_addr(String spouse_contact_addr) {
		this.spouse_contact_addr = spouse_contact_addr;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getMax_quota() {
		return max_quota;
	}

	public void setMax_quota(String max_quota) {
		this.max_quota = max_quota;
	}

	public String getCompany_nature() {
		return company_nature;
	}

	public void setCompany_nature(String company_nature) {
		this.company_nature = company_nature;
	}

	public String getWork_year() {
		return work_year;
	}

	public void setWork_year(String work_year) {
		this.work_year = work_year;
	}

	public String getUnit_tel() {
		return unit_tel;
	}

	public void setUnit_tel(String unit_tel) {
		this.unit_tel = unit_tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCert_org() {
		return cert_org;
	}

	public void setCert_org(String cert_org) {
		this.cert_org = cert_org;
	}

	public String getRegular_deposit_amt() {
		return regular_deposit_amt;
	}

	public void setRegular_deposit_amt(String regular_deposit_amt) {
		this.regular_deposit_amt = regular_deposit_amt;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getCert_addr() {
		return cert_addr;
	}

	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getFamily_tel() {
		return family_tel;
	}

	public void setFamily_tel(String family_tel) {
		this.family_tel = family_tel;
	}

	public String getContact_addr() {
		return contact_addr;
	}

	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
