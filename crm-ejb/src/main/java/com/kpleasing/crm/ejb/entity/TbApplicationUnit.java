package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_APPLICATION_UNIT database table.
 * 
 */
@Entity
@Table(name="TB_APPLICATION_UNIT")
@NamedQuery(name="TbApplicationUnit.findAll", query="SELECT t FROM TbApplicationUnit t")
public class TbApplicationUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="app_id")
	private int appId;

	@Column(name="app_code")
	private String appCode;

	@Column(name="app_name")
	private String appName;

	public TbApplicationUnit() {
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}