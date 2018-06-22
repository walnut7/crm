package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TB_ATTACHMENT_INFO database table.
 * 
 */
@Entity
@Table(name="TB_ATTACHMENT_INFO")
@NamedQuery(name="TbAttachmentInfo.findAll", query="SELECT t FROM TbAttachmentInfo t")
public class TbAttachmentInfo extends Pagination implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="attachment_id", unique=true, nullable=false)
	private int attachmentId;
	
	@Column(name="file_name", length=60)
	private String fileName;
	
	@Column(name="file_type_code", length=20)
	private String fileTypeCode;
	
	@Column(name="attachment_name", length=60)
	private String attachmentName;
	
	@Column(name="attachment_type", length=20)
	private String attachmentType;
	
	@Column(name="local_path", length=255)
	private String localPath;
	
	@Column(name="remote_path", nullable=false,length=255)
	private String remotePath;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at", nullable=false)
	private Date createAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateAt;
	
	@Column(name="remark", length=255)
	private String remark;

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
