package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_SECURITY_KEY database table.
 * 
 */
@Entity
@Table(name="TB_SECURITY_KEY")
@NamedQuery(name="TbSecurityKey.findAll", query="SELECT t FROM TbSecurityKey t")
public class TbSecurityKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="API_NAME", nullable=false, length=32)
	private String apiName;

	@Column(name="SIGN_KEY", nullable=false, length=32)
	private String signKey;

	@Column(name="SYS_CODE", nullable=false, length=20)
	private String sysCode;

	@Column(name="SYS_NAME", nullable=false, length=20)
	private String sysName;

	public TbSecurityKey() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApiName() {
		return this.apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getSignKey() {
		return this.signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getSysCode() {
		return this.sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

}