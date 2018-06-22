package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TB_STAFF_INFO database table.
 * 
 */
@Entity
@Table(name="TB_STAFF_INFO")
@NamedQuery(name="TbStaffInfo.findAll", query="SELECT t FROM TbStaffInfo t")
public class TbStaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="staff_id")
	private int staffId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="eff_time")
	private Date effTime;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	private Date endTime;

	@Column(name="is_modify_pwd")
	private byte isModifyPwd;

	private String login;

	private String phone;

	private String pwd;

	private String qq;

	@Column(name="staff_name")
	private String staffName;

	@Column(name="staff_no")
	private String staffNo;

	private byte status;

	@Column(name="try_times")
	private byte tryTimes;

	@Column(name="user_type")
	private byte userType;

	private String wx;

	public TbStaffInfo() {
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public Date getEffTime() {
		return this.effTime;
	}

	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public byte getIsModifyPwd() {
		return this.isModifyPwd;
	}

	public void setIsModifyPwd(byte isModifyPwd) {
		this.isModifyPwd = isModifyPwd;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNo() {
		return this.staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getTryTimes() {
		return this.tryTimes;
	}

	public void setTryTimes(byte tryTimes) {
		this.tryTimes = tryTimes;
	}

	public byte getUserType() {
		return this.userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getWx() {
		return this.wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

}