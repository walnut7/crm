package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbStaffInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.TbStaffInfo;

@Stateless
@LocalBean
public class TbStaffInfoEAO extends BaseEao<TbStaffInfo, Integer> implements TbStaffInfoEAOLocal {

	private final static String FIELD_LOGIN = "login";
	
	private final static String FIELD_PASSWORD = "pwd";
	
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbStaffInfoEAO() {
    	super(TbStaffInfo.class);
    }

    
	@Override
	public TbStaffInfo getStaffInfoByNameAndPWD(String login, String password) {
		List<TbStaffInfo> staffList = this.findByProperty(new Object[] { FIELD_LOGIN,  FIELD_PASSWORD }, login, password);
		return (staffList!=null && staffList.size()>0)?staffList.get(0):null;
	}


}
