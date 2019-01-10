package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the R_STAFF_INFO_ROLE database table.
 * 
 */
@Entity
@Table(name="R_STAFF_INFO_ROLE")
@NamedQuery(name="RStaffInfoRole.findAll", query="SELECT r FROM RStaffInfoRole r")
public class RStaffInfoRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="role_id")
	private int roleId;

	@Column(name="staff_id")
	private int staffId;

	public RStaffInfoRole() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStaffId() {
		return this.staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

}