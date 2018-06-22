package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbApplicationUnitEAOLocal;
import com.kpleasing.crm.ejb.entity.TbApplicationUnit;

@Stateless
@LocalBean
public class TbApplicationUnitEAO extends BaseEao<TbApplicationUnit, Integer> implements TbApplicationUnitEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbApplicationUnitEAO() {
    	super(TbApplicationUnit.class);
    }
}
