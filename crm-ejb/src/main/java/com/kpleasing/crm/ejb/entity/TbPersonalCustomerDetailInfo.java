package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TB_PERSONAL_CUSTOMER_DETAIL_INFO database table.
 * 
 */
@Entity
@Table(name="TB_PERSONAL_CUSTOMER_DETAIL_INFO")
@NamedQuery(name="TbPersonalCustomerDetailInfo.findAll", query="SELECT t FROM TbPersonalCustomerDetailInfo t")
public class TbPersonalCustomerDetailInfo  extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="annual_income")
	private Double annualIncome;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name="cert_addr", length=60)
	private String certAddr;

	@Column(name="cert_org", length=60)
	private String certOrg;

	@Column(name="company_nature", length=20)
	private String companyNature;

	@Column(name="contact_addr", length=60)
	private String contactAddr;

	@Column(name="cust_id", nullable=false)
	private Integer custId;

	@Column(name="cust_name_spell", length=20)
	private String custNameSpell;

	@Column(name="drive_model", length=20)
	private String driveModel;

	@Column(name="edu_level", length=20)
	private String eduLevel;

	@Column(length=60)
	private String email;

	@Column(name="entry_year", length=20)
	private String entryYear;

	@Column(name="family_tel", length=20)
	private String familyTel;

	@Column(length=20)
	private String gender;

	@Column(name="income_from", length=20)
	private String incomeFrom;

	@Column(name="income_status", length=20)
	private String incomeStatus;

	@Column(length=20)
	private String industry;

	@Column(name="live_status", length=20)
	private String liveStatus;

	@Column(name="marr_status", length=20)
	private String marrStatus;

	@Column(name="max_quota", length=20)
	private String maxQuota;

	@Column(length=20)
	private String nation;

	@Column(length=20)
	private String position;

	@Column(name="regular_deposit_amt", length=20)
	private String regularDepositAmt;

	@Column(name="rel_flag", length=20)
	private String relFlag;

	@Column(name="spouse_annual_income")
	private Double spouseAnnualIncome;

	@Column(name="spouse_cert_code", length=20)
	private String spouseCertCode;

	@Column(name="spouse_cert_type", length=20)
	private String spouseCertType;

	@Column(name="spouse_contact_addr", length=60)
	private String spouseContactAddr;

	@Column(name="spouse_income_from", length=20)
	private String spouseIncomeFrom;

	@Column(name="spouse_name", length=20)
	private String spouseName;

	@Column(name="spouse_phone", length=15)
	private String spousePhone;

	@Column(name="spouse_work_unit", length=60)
	private String spouseWorkUnit;

	@Column(name="unit_tel", length=20)
	private String unitTel;

	@Column(name="work_unit", length=60)
	private String workUnit;

	@Column(name="work_year")
	private Integer workYear;

	@Column(name="zip_code", length=16)
	private String zipCode;
	
	@Column(name="work_addr", length=16)
	private String workAddr;

	public TbPersonalCustomerDetailInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		if(null == birthday || "null".equals(birthday) || "".equals(birthday)) {
		//	this.birthday = null;
		} else {
			this.birthday = birthday;
		}
	}

	public String getCertAddr() {
		return this.certAddr;
	}

	public void setCertAddr(String certAddr) {
		if(null != certAddr){
			this.certAddr = certAddr.trim();
		}
	}

	public String getCertOrg() {
		return this.certOrg;
	}

	public void setCertOrg(String certOrg) {
		if(null != certOrg){
			this.certOrg = certOrg.trim();
		}
	}

	public String getCompanyNature() {
		return this.companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public String getContactAddr() {
		return this.contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		if(null != contactAddr){
			this.contactAddr = contactAddr.trim();
		}
	}

	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustNameSpell() {
		return this.custNameSpell;
	}

	public void setCustNameSpell(String custNameSpell) {
		if(null != custNameSpell){
			this.custNameSpell = custNameSpell.trim();
		}
	}

	public String getDriveModel() {
		return this.driveModel;
	}

	public void setDriveModel(String driveModel) {
		this.driveModel = driveModel;
	}

	public String getEduLevel() {
		return this.eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		if(null != email){
			this.email = email.trim();
		}
	}

	public String getEntryYear() {
		return this.entryYear;
	}

	public void setEntryYear(String entryYear) {
		if(null != entryYear){
			this.entryYear = entryYear.trim();
		}
	}

	public String getFamilyTel() {
		return this.familyTel;
	}

	public void setFamilyTel(String familyTel) {
		if(null != familyTel){
			this.familyTel = familyTel.trim();
		}
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIncomeFrom() {
		return this.incomeFrom;
	}

	public void setIncomeFrom(String incomeFrom) {
		this.incomeFrom = incomeFrom;
	}

	public String getIncomeStatus() {
		return this.incomeStatus;
	}

	public void setIncomeStatus(String incomeStatus) {
		this.incomeStatus = incomeStatus;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getLiveStatus() {
		return this.liveStatus;
	}

	public void setLiveStatus(String liveStatus) {
		this.liveStatus = liveStatus;
	}

	public String getMarrStatus() {
		return this.marrStatus;
	}

	public void setMarrStatus(String marrStatus) {
		this.marrStatus = marrStatus;
	}

	public String getMaxQuota() {
		return this.maxQuota;
	}

	public void setMaxQuota(String maxQuota) {
		this.maxQuota = maxQuota;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		if(null != nation){
			this.nation = nation.trim();
		}
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		if(null != position){
			this.position = position.trim();
		}
	}

	public String getRegularDepositAmt() {
		return this.regularDepositAmt;
	}

	public void setRegularDepositAmt(String regularDepositAmt) {
		if(null!=regularDepositAmt) {
			this.regularDepositAmt = regularDepositAmt.trim();
		}
	}

	public String getRelFlag() {
		return this.relFlag;
	}

	public void setRelFlag(String relFlag) {
		this.relFlag = relFlag;
	}

	public Double getSpouseAnnualIncome() {
		return this.spouseAnnualIncome;
	}

	public void setSpouseAnnualIncome(Double spouseAnnualIncome) {
		this.spouseAnnualIncome = spouseAnnualIncome;
	}

	public String getSpouseCertCode() {
		return this.spouseCertCode;
	}

	public void setSpouseCertCode(String spouseCertCode) {
		if(null != spouseCertCode){
			this.spouseCertCode = spouseCertCode.trim();
		}
	}

	public String getSpouseCertType() {
		return this.spouseCertType;
	}

	public void setSpouseCertType(String spouseCertType) {
		if(null != spouseCertType){
			this.spouseCertType = spouseCertType.trim();
		}
	}

	public String getSpouseContactAddr() {
		return this.spouseContactAddr;
	}

	public void setSpouseContactAddr(String spouseContactAddr) {
		if(null != spouseContactAddr){
			this.spouseContactAddr = spouseContactAddr.trim();
		}
	}

	public String getSpouseIncomeFrom() {
		return this.spouseIncomeFrom;
	}

	public void setSpouseIncomeFrom(String spouseIncomeFrom) {
		this.spouseIncomeFrom = spouseIncomeFrom;
	}

	public String getSpouseName() {
		return this.spouseName;
	}

	public void setSpouseName(String spouseName) {
		if(null != spouseName){
			this.spouseName = spouseName.trim();
		}
	}

	public String getSpousePhone() {
		return this.spousePhone;
	}

	public void setSpousePhone(String spousePhone) {
		if(null != spousePhone){
			this.spousePhone = spousePhone.trim();
		}
	}

	public String getSpouseWorkUnit() {
		return this.spouseWorkUnit;
	}

	public void setSpouseWorkUnit(String spouseWorkUnit) {
		if(null != spouseWorkUnit){
			this.spouseWorkUnit = spouseWorkUnit.trim();
		}
	}

	public String getUnitTel() {
		return this.unitTel;
	}

	public void setUnitTel(String unitTel) {
		if(null != unitTel){
			this.unitTel = unitTel.trim();
		}
	}

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		if(null != workUnit){
			this.workUnit = workUnit.trim();
		}
	}

	public Integer getWorkYear() {
		return this.workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		if(null != zipCode){
			this.zipCode = zipCode.trim();
		}
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		if(null != workAddr){
			this.workAddr = workAddr.trim();
		}
	}

}