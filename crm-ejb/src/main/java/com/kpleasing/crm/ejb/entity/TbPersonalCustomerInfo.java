package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TB_PERSONAL_CUSTOMER_INFO database table.
 * 
 */
@Entity
@Table(name="TB_PERSONAL_CUSTOMER_INFO")
@NamedQuery(name="TbPersonalCustomerInfo.findAll", query="SELECT t FROM TbPersonalCustomerInfo t")
public class TbPersonalCustomerInfo extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cust_id", unique=true, nullable=false)
	private int custId;

	@Column(name="cert_code", length=20)
	private String certCode;

	@Column(name="cert_type", length=20)
	private String certType;

	@Column(length=20)
	private String channel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at", nullable=false)
	private Date createAt;

	@Column(name="cust_memo", length=60)
	private String custMemo;

	@Column(name="cust_name", length=20)
	private String custName;

	@Column(name="cust_status", length=20)
	private String custStatus;

	@Column(name="cust_type", length=20)
	private String custType;

	@Column(length=255)
	private String memo;

	@Column(nullable=false, length=15)
	private String phone;
	
	@Transient
	private TbPersonalCustomerDetailInfo tbPersonalCustomerDetailInfo;
	
	@Transient
	private List<TbPersonalContactRelation> tbPersonalContactRelationList;
	
	@Transient
	private List<TbPersonalAccountInfo> tbPersonalAccountInfoList;
	
	@Transient
	private List<RCustAttachment> rCustAttachmentList;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateAt;

	@Column(name="wx_open_id", length=32)
	private String wxOpenId;

	public TbPersonalCustomerInfo() {
	}

	public int getCustId() {
		return this.custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCertCode() {
		return this.certCode;
	}

	public void setCertCode(String certCode) {
		if(null != certCode){
			this.certCode = certCode.trim();
		}
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCustMemo() {
		return this.custMemo;
	}

	public void setCustMemo(String custMemo) {
		if(null != custMemo){
			this.custMemo = custMemo.trim();
		}
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		if(null != custName){
			this.custName = custName.trim();
		}
	}

	public String getCustStatus() {
		return this.custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		if(null != memo){
			this.memo = memo.trim();
		}
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		if(null != phone){
			this.phone = phone.trim();
		}
	}

	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getWxOpenId() {
		return this.wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public TbPersonalCustomerDetailInfo getTbPersonalCustomerDetailInfo() {
		return tbPersonalCustomerDetailInfo;
	}

	public void setTbPersonalCustomerDetailInfo(TbPersonalCustomerDetailInfo tbPersonalCustomerDetailInfo) {
		this.tbPersonalCustomerDetailInfo = tbPersonalCustomerDetailInfo;
	}

	public List<TbPersonalContactRelation> getTbPersonalContactRelationList() {
		return tbPersonalContactRelationList;
	}

	public void setTbPersonalContactRelationList(List<TbPersonalContactRelation> tbPersonalContactRelationList) {
		this.tbPersonalContactRelationList = tbPersonalContactRelationList;
	}

	public List<TbPersonalAccountInfo> getTbPersonalAccountInfoList() {
		return tbPersonalAccountInfoList;
	}

	public void setTbPersonalAccountInfoList(List<TbPersonalAccountInfo> tbPersonalAccountInfoList) {
		this.tbPersonalAccountInfoList = tbPersonalAccountInfoList;
	}
	
	public List<RCustAttachment> getRCustAttachmentList() {
		return rCustAttachmentList;
	}
	
	public void setRCustAttachmentList(List<RCustAttachment> rCustAttachmentList) {
		this.rCustAttachmentList = rCustAttachmentList;
	}
}