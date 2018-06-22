package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TB_RESERVER_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="TB_RESERVER_CUSTOMER")
@NamedQuery(name="TbReserverCustomer.findAll", query="SELECT t FROM TbReserverCustomer t")
public class TbReserverCustomer extends Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	private Date createAt;

	@Column(name="cust_id")
	private int custId;

	@Column(name="cust_name")
	private String custName;

	private String gender;
	
	@Column(name="phone")
	private String phone;

	private String memo;

	private String operator;

	@Column(name="product_desc")
	private String productDesc;

	@Column(name="product_title")
	private String productTitle;

	@Column(name="reserver_store")
	private String reserverStore;

	@Temporal(TemporalType.DATE)
	@Column(name="reserver_time")
	private Date reserverTime;

	public TbReserverCustomer() {
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

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductTitle() {
		return this.productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getReserverStore() {
		return this.reserverStore;
	}

	public void setReserverStore(String reserverStore) {
		this.reserverStore = reserverStore;
	}

	public Date getReserverTime() {
		return this.reserverTime;
	}

	public void setReserverTime(Date reserverTime) {
		this.reserverTime = reserverTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}