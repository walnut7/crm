package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TB_PARAMETER_CONFIG database table.
 * 
 */
@Entity
@Table(name="TB_PARAMETER_CONFIG")
@NamedQuery(name="TbParameterConfig.findAll", query="SELECT t FROM TbParameterConfig t")
public class TbParameterConfig extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at", nullable=false)
	private Date createAt;

	@Column(name="del_flag")
	private byte delFlag;

	@Column(length=255)
	private String memo;

	@Column(name="node_code", nullable=false, length=60)
	private String nodeCode;

	@Column(name="node_value", nullable=false, length=60)
	private String nodeValue;

	@Column(nullable=false, length=20)
	private String operator;

	@Column(name="parent_node_id")
	private int parentNodeId;

	private byte sort;

	@Column(nullable=false)
	private byte status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at", nullable=false)
	private Date updateAt;

	public TbParameterConfig() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public byte getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNodeCode() {
		return this.nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeValue() {
		return this.nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getParentNodeId() {
		return this.parentNodeId;
	}

	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public byte getSort() {
		return this.sort;
	}

	public void setSort(byte sort) {
		this.sort = sort;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}