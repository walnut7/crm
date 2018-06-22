package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the R_CUST_ATTACHMENT database table.
 * 
 */
@Entity
@Table(name="R_CUST_ATTACHMENT")
@NamedQuery(name="RCustAttachment.findAll", query="SELECT t FROM RCustAttachment t")
public class RCustAttachment extends Pagination implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;
	
	@Column(name="cust_flag",unique=true, nullable=false)
	private int custFlag;
	
	@Column(name="cust_id",unique=true, nullable=false)
	private int custId;
	
	@Column(name="attachment_id",unique=true, nullable=false)
	private int attachmentId;
	
	@Column(name="flag",unique=true, nullable=false)
	private int flag;
	
	@Transient
	private TbAttachmentInfo tbAttachmentInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustFlag() {
		return custFlag;
	}

	public void setCustFlag(int custFlag) {
		this.custFlag = custFlag;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public TbAttachmentInfo getTbAttachmentInfo() {
		return tbAttachmentInfo;
	}

	public void setTbAttachmentInfo(TbAttachmentInfo tbAttachmentInfo) {
		this.tbAttachmentInfo = tbAttachmentInfo;
	}

}
