package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbPersonalContactRelationEAOLocal;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;


@Stateless
@LocalBean
public class TbPersonalContactRelationEAO extends BaseEao<TbPersonalContactRelation, Integer> implements TbPersonalContactRelationEAOLocal {

	private final static String CUST_ID = "custId";
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbPersonalContactRelationEAO() {
    	super(TbPersonalContactRelation.class);
    }

    
	public List<TbPersonalContactRelation> findCustContactRelationByCustId(int custId) {
		return this.findByProperty(CUST_ID, custId);
	}
}
