package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbSecurityKeyEAOLocal;
import com.kpleasing.crm.ejb.entity.TbSecurityKey;

@Stateless
@LocalBean
public class TbSecurityKeyEAO extends BaseEao<TbSecurityKey, Integer> implements TbSecurityKeyEAOLocal {

	/**
     * @see BaseEao#BaseEao()
     */
    public TbSecurityKeyEAO() {
    	super(TbSecurityKey.class);
    }
}
