package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbFuncGroupEAOLocal;
import com.kpleasing.crm.ejb.entity.TbFuncGroup;

@Stateless
@LocalBean
public class TbFuncGroupEAO extends BaseEao<TbFuncGroup, Integer> implements TbFuncGroupEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbFuncGroupEAO() {
    	super(TbFuncGroup.class);
    }
}
