package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbSysConfigEAOLocal;
import com.kpleasing.crm.ejb.entity.TbSysConfig;

@Stateless
@LocalBean
public class TbSysConfigEAO extends BaseEao<TbSysConfig, Integer> implements TbSysConfigEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbSysConfigEAO() {
    	super(TbSysConfig.class);
    }
}
