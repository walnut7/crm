package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.RRoleFunctionGroupEAOLocal;
import com.kpleasing.crm.ejb.entity.RRoleFunctionGroup;


@Stateless
@LocalBean
public class RRoleFunctionGroupEAO extends BaseEao<RRoleFunctionGroup, Integer> implements RRoleFunctionGroupEAOLocal {

	/**
     * @see BaseEao#BaseEao()
     */
    public RRoleFunctionGroupEAO() {
    	super(RRoleFunctionGroup.class);
    }
    
    
}
