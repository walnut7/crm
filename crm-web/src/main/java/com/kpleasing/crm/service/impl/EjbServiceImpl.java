package com.kpleasing.crm.service.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.service.local.APIServiceLocal;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.service.local.CustomerServiceLocal;
import com.kpleasing.crm.ejb.service.local.LoginServiceLocal;
import com.kpleasing.crm.ejb.service.local.NotifyServiceLocal;
import com.kpleasing.crm.ejb.service.local.SystemServiceLocal;
import com.kpleasing.crm.service.EjbService;


@Service
public class EjbServiceImpl implements EjbService {
	
	private static InitialContext context;
	private static LoginServiceLocal loginServ;
	private static APIServiceLocal apiServ;
	private static SystemServiceLocal sysServ;
	private static CustomerServiceLocal custServ;
	private static ConfigServiceLocal confServ;
	private static NotifyServiceLocal notifyServ;
	
	@Override
	public LoginServiceLocal getLoginServ() {
		if (loginServ == null) {
			try {
				loginServ = (LoginServiceLocal) lookupEJB("java:app/crm-ejb/LoginService!com.kpleasing.crm.ejb.service.local.LoginServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return loginServ;
	}
	
	@Override
	public APIServiceLocal getApiServ() {
		if (apiServ == null) {
			try {
				apiServ = (APIServiceLocal) lookupEJB("java:app/crm-ejb/APIService!com.kpleasing.crm.ejb.service.local.APIServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return apiServ;
	}
	
	
	@Override
	public SystemServiceLocal getSysServ() {
		if (sysServ == null) {
			try {
				sysServ = (SystemServiceLocal) lookupEJB("java:app/crm-ejb/SystemService!com.kpleasing.crm.ejb.service.local.SystemServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return sysServ;
	}
	
	@Override
	public ConfigServiceLocal getConfigServ() {
		if (confServ == null) {
			try {
				confServ = (ConfigServiceLocal) lookupEJB("java:app/crm-ejb/ConfigService!com.kpleasing.crm.ejb.service.local.ConfigServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return confServ;
	}

	@Override
	public CustomerServiceLocal getCustServ() {
		if (custServ == null) {
			try {
				custServ = (CustomerServiceLocal) lookupEJB("java:app/crm-ejb/CustomerService!com.kpleasing.crm.ejb.service.local.CustomerServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return custServ;
	}
	
	@Override
	public NotifyServiceLocal getNotifyServ() {
		if (custServ == null) {
			try {
				notifyServ = (NotifyServiceLocal) lookupEJB("java:app/crm-ejb/NotifyService!com.kpleasing.crm.ejb.service.local.NotifyServiceLocal");
			} catch (NamingException e) {                  
				// TODO
				e.printStackTrace();
			}
		}
		return notifyServ;
	}
	
	/**
	 * 
	 * @param jndiName
	 * @return
	 * @throws NamingException
	 */
	private Object lookupEJB(String jndiName) throws NamingException {
		if (context == null) {
			context = new InitialContext();
		}
		return context.lookup(jndiName);
	}
}
