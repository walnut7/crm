package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_ROLE database table.
 * 
 */
@Entity
@Table(name="TB_ROLE")
@NamedQuery(name="TbRole.findAll", query="SELECT t FROM TbRole t")
public class TbRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;

	private String memo;

	@Column(name="role_code")
	private String roleCode;

	@Column(name="role_name")
	private String roleName;

	public TbRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}