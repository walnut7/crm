package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_FUNC_GROUP database table.
 * 
 */
@Entity
@Table(name="TB_FUNC_GROUP")
@NamedQuery(name="TbFuncGroup.findAll", query="SELECT t FROM TbFuncGroup t")
public class TbFuncGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fun_id")
	private int funId;

	@Column(name="app_id")
	private int appId;

	@Column(name="func_group_code")
	private String funcGroupCode;

	@Column(name="func_group_name")
	private String funcGroupName;

	private String memo;

	public TbFuncGroup() {
	}

	public int getFunId() {
		return this.funId;
	}

	public void setFunId(int funId) {
		this.funId = funId;
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getFuncGroupCode() {
		return this.funcGroupCode;
	}

	public void setFuncGroupCode(String funcGroupCode) {
		this.funcGroupCode = funcGroupCode;
	}

	public String getFuncGroupName() {
		return this.funcGroupName;
	}

	public void setFuncGroupName(String funcGroupName) {
		this.funcGroupName = funcGroupName;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}