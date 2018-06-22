package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbRoleEAOLocal;
import com.kpleasing.crm.ejb.entity.TbRole;

@Stateless
@LocalBean
public class TbRoleEAO extends BaseEao<TbRole, Integer> implements TbRoleEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbRoleEAO() {
    	super(TbRole.class);
    }
}
