package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbPermissionEAOLocal;
import com.kpleasing.crm.ejb.entity.TbPermission;

@Stateless
@LocalBean
public class TbPermissionEAO extends BaseEao<TbPermission, Integer> implements TbPermissionEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbPermissionEAO() {
    	super(TbPermission.class);
    }
}
