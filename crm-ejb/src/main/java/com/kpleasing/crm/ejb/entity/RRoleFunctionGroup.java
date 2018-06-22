package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_ROLE_FUNCTION_GROUP database table.
 * 
 */
@Entity
@Table(name="R_ROLE_FUNCTION_GROUP")
@NamedQuery(name="RRoleFunctionGroup.findAll", query="SELECT r FROM RRoleFunctionGroup r")
public class RRoleFunctionGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="fun_id")
	private int funId;

	@Column(name="role_id")
	private int roleId;

	public RRoleFunctionGroup() {
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

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}