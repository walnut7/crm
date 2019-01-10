package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.service.local.APIServiceLocal;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.service.local.CustomerServiceLocal;
import com.kpleasing.crm.ejb.service.local.LoginServiceLocal;
import com.kpleasing.crm.ejb.service.local.NavigationServiceLocal;
import com.kpleasing.crm.ejb.service.local.NotifyServiceLocal;
import com.kpleasing.crm.ejb.service.local.SystemServiceLocal;

public interface EjbService {
	
	public ConfigServiceLocal getConfigServ();

	public LoginServiceLocal getLoginServ();
	
	public APIServiceLocal getApiServ();
	
	public SystemServiceLocal getSysServ();
	
	public CustomerServiceLocal getCustServ();

	public NotifyServiceLocal getNotifyServ();
	
	public NavigationServiceLocal getNavigationServ();

}
