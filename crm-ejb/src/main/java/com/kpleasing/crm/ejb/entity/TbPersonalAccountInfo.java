package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_PERSONAL_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="TB_PERSONAL_ACCOUNT_INFO")
@NamedQuery(name="TbPersonalAccountInfo.findAll", query="SELECT t FROM TbPersonalAccountInfo t")
public class TbPersonalAccountInfo  extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="acc_name", length=30)
	private String accName;

	@Column(name="acc_no", length=30)
	private String accNo;

	@Column(name="acc_status", length=20)
	private String accStatus;

	@Column(name="bank_code", length=20)
	private String bankCode;

	@Column(name="bank_full_name", length=60)
	private String bankFullName;

	@Column(name="branch_bank_name", length=60)
	private String branchBankName;

	@Column(name="bank_phone", length=60)
	private String bankPhone;
	
	@Column(length=20)
	private String currency;

	@Column(name="cust_id")
	private Integer custId;

	@Column(name="is_withhold_acc", length=20)
	private String isWithholdAcc;

	@Column(name="withhold_unit", length=60)
	private String withholdUnit;
	
	@Column(name="abbreviation_card_no", length=60)
	private String abbreviationCardNo;
	
	@Column(name="is_yjzf_bind")
	private int isYjzfBind;

	public TbPersonalAccountInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		if(null != accName){
			this.accName = accName.trim();
		}
	}

	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		if(null != accNo){
			this.accNo = accNo.trim();
		}
	}

	public String getAccStatus() {
		return this.accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankFullName() {
		return this.bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		if(null != bankFullName){
			this.bankFullName = bankFullName.trim();
		}
	}

	public String getBranchBankName() {
		return this.branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		if(null != branchBankName){
			this.branchBankName = branchBankName.trim();
		}
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getIsWithholdAcc() {
		return this.isWithholdAcc;
	}

	public void setIsWithholdAcc(String isWithholdAcc) {
		this.isWithholdAcc = isWithholdAcc;
	}

	public String getWithholdUnit() {
		return this.withholdUnit;
	}

	public void setWithholdUnit(String withholdUnit) {
		this.withholdUnit = withholdUnit;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}
	
	public String getAbbreviationCardNo() {
		return this.abbreviationCardNo;
	}
	
	public void setAbbreviationCardNo(String abbreviationCardNo) {
		this.abbreviationCardNo = abbreviationCardNo;
	}

	public int getIsYjzfBind() {
		return isYjzfBind;
	}

	public void setIsYjzfBind(int isYjzfBind) {
		this.isYjzfBind = isYjzfBind;
	}

}