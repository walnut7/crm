package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbParameterConfigEAOLocal;
import com.kpleasing.crm.ejb.entity.TbParameterConfig;

@Stateless
@LocalBean
public class TbParameterConfigEAO extends BaseEao<TbParameterConfig, Integer> implements TbParameterConfigEAOLocal {

	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbParameterConfigEAO() {
    	super(TbParameterConfig.class);
    }
}
