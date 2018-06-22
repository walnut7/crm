package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_PERSONAL_CONTACT_RELATION database table.
 * 
 */
@Entity
@Table(name="TB_PERSONAL_CONTACT_RELATION")
@NamedQuery(name="TbPersonalContactRelation.findAll", query="SELECT t FROM TbPersonalContactRelation t")
public class TbPersonalContactRelation  extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="contact_addr", length=60)
	private String contactAddr;

	@Column(name="contact_cert_code", length=20)
	private String contactCertCode;

	@Column(name="contact_cert_type", length=20)
	private String contactCertType;

	@Column(name="contact_email", length=60)
	private String contactEmail;

	@Column(name="contact_fax", length=20)
	private String contactFax;

	@Column(name="contact_name", length=20)
	private String contactName;

	@Column(name="contact_phone", length=20)
	private String contactPhone;

	@Column(name="contact_work_unit", length=60)
	private String contactWorkUnit;

	@Column(name="cust_id")
	private Integer custId;

	@Column(name="is_important_contact", length=20)
	private String isImportantContact;

	@Column(name="is_send_sms", length=20)
	private String isSendSms;

	@Column(length=20)
	private String relation;

	public TbPersonalContactRelation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContactAddr() {
		return this.contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		if(null != contactAddr){
			this.contactAddr = contactAddr.trim();
		}
	}

	public String getContactCertCode() {
		return this.contactCertCode;
	}

	public void setContactCertCode(String contactCertCode) {
		if(null != contactCertCode){
			this.contactCertCode = contactCertCode.trim();
		}
	}

	public String getContactCertType() {
		return this.contactCertType;
	}

	public void setContactCertType(String contactCertType) {
		this.contactCertType = contactCertType;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		if(null != contactEmail){
			this.contactEmail = contactEmail.trim();
		}
	}

	public String getContactFax() {
		return this.contactFax;
	}

	public void setContactFax(String contactFax) {
		if(null != contactFax){
			this.contactFax = contactFax.trim();
		}
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		if(null != contactName){
			this.contactName = contactName.trim();
		}
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		if(null != contactPhone){
			this.contactPhone = contactPhone.trim();
		}
	}

	public String getContactWorkUnit() {
		return this.contactWorkUnit;
	}

	public void setContactWorkUnit(String contactWorkUnit) {
		if(null != contactWorkUnit){
			this.contactWorkUnit = contactWorkUnit.trim();
		}
	}

	public Integer getCustId() {
		return this.custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getIsImportantContact() {
		return this.isImportantContact;
	}

	public void setIsImportantContact(String isImportantContact) {
		this.isImportantContact = isImportantContact;
	}

	public String getIsSendSms() {
		return this.isSendSms;
	}

	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

}