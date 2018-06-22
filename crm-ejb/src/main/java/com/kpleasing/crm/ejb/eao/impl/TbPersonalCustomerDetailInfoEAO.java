package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerDetailInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;


@Stateless
@LocalBean
public class TbPersonalCustomerDetailInfoEAO extends BaseEao<TbPersonalCustomerDetailInfo, Integer> implements TbPersonalCustomerDetailInfoEAOLocal {

	private final static String CUST_ID = "custId";
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbPersonalCustomerDetailInfoEAO() {
    	super(TbPersonalCustomerDetailInfo.class);
    }

	@Override
	public TbPersonalCustomerDetailInfo findCustDetailByCustId(int custId) {
		List<TbPersonalCustomerDetailInfo> custDetailList = this.findByProperty(CUST_ID, custId);
		return (custDetailList!=null && custDetailList.size()>0)?custDetailList.get(0):null;
	}
}
