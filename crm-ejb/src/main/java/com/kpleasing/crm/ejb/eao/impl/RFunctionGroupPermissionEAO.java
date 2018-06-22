package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.RFunctionGroupPermissionEAOLocal;
import com.kpleasing.crm.ejb.entity.RFunctionGroupPermission;

@Stateless
@LocalBean
public class RFunctionGroupPermissionEAO extends BaseEao<RFunctionGroupPermission, Integer> implements RFunctionGroupPermissionEAOLocal {

	/**
     * @see BaseEao#BaseEao()
     */
    public RFunctionGroupPermissionEAO() {
    	super(RFunctionGroupPermission.class);
    }

}
