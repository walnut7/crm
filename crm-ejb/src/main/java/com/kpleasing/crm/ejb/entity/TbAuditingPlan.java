package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TB_AUDITING_PLAN database table.
 * 
 */
@Entity
@Table(name="TB_AUDITING_PLAN")
@NamedQuery(name="TbAuditingPlan.findAll", query="SELECT t FROM TbAuditingPlan t")
public class TbAuditingPlan extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="audi_flag")
	private byte audiFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	private Date createAt;

	@Column(name="cust_id")
	private int custId;

	@Column(name="cust_name")
	private String custName;

	@Column(name="first_date")
	private String firstDate;

	private String remark;

	@Column(name="second_date")
	private String secondDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_at")
	private Date updateAt;

	public TbAuditingPlan() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAudiFlag() {
		return this.audiFlag;
	}

	public void setAudiFlag(byte audiFlag) {
		this.audiFlag = audiFlag;
	}

	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getCustId() {
		return this.custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFirstDate() {
		return this.firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecondDate() {
		return this.secondDate;
	}

	public void setSecondDate(String secondDate) {
		this.secondDate = secondDate;
	}

	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}