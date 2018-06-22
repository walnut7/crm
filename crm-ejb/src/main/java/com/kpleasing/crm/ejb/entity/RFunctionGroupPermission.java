package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_FUNCTION_GROUP_PERMISSION database table.
 * 
 */
@Entity
@Table(name="R_FUNCTION_GROUP_PERMISSION")
@NamedQuery(name="RFunctionGroupPermission.findAll", query="SELECT r FROM RFunctionGroupPermission r")
public class RFunctionGroupPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="fun_id")
	private int funId;

	@Column(name="permission_id")
	private int permissionId;

	public RFunctionGroupPermission() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFunId() {
		return this.funId;
	}

	public void setFunId(int funId) {
		this.funId = funId;
	}

	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

}