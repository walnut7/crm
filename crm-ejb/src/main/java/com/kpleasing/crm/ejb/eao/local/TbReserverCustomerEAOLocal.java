package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbReserverCustomer;

@Local
public interface TbReserverCustomerEAOLocal extends BaseEaoLocal<TbReserverCustomer, Integer>  {

	public List<TbReserverCustomer> findReserverList(TbReserverCustomer tbReserverCustomer);

	public int findReserverListByCount(TbReserverCustomer tbReserverCustomer);

}
