package com.kpleasing.crm.ejb.eao.local;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;

@Local
public interface TbPersonalCustomerDetailInfoEAOLocal extends BaseEaoLocal<TbPersonalCustomerDetailInfo, Integer> {

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TbPersonalCustomerDetailInfo findCustDetailByCustId(int id);

}
