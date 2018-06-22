package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_PERMISSION database table.
 * 
 */
@Entity
@Table(name="TB_PERMISSION")
@NamedQuery(name="TbPermission.findAll", query="SELECT t FROM TbPermission t")
public class TbPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="permission_id", unique=true, nullable=false)
	private int permissionId;

	@Column(length=255)
	private String memo;

	@Column(name="permission_code", length=20)
	private String permissionCode;

	@Column(name="permission_name", length=60)
	private String permissionName;

	public TbPermission() {
	}

	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}