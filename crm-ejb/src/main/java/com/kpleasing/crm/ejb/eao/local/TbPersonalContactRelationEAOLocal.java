package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;

@Local
public interface TbPersonalContactRelationEAOLocal extends BaseEaoLocal<TbPersonalContactRelation, Integer>  {

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<TbPersonalContactRelation> findCustContactRelationByCustId(int id);

}
