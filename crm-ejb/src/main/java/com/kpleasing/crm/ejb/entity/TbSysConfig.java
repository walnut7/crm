package com.kpleasing.crm.ejb.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TB_SYS_CONFIG database table.
 * 
 */
@Entity
@Table(name="TB_SYS_CONFIG")
@NamedQuery(name="TbSysConfig.findAll", query="SELECT t FROM TbSysConfig t")
public class TbSysConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="`KEY`", length=20)
	private String key;

	@Column(length=80)
	private String value;

	public TbSysConfig() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}