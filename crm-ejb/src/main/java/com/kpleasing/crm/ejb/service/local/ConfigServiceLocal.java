package com.kpleasing.crm.ejb.service.local;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.config.Configurate;

@Local
public interface ConfigServiceLocal {

	/**
	 * 
	 * @return
	 */
	public Configurate getConfig();

}
