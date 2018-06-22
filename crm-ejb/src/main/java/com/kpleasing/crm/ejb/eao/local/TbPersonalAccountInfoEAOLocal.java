package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;

@Local
public interface TbPersonalAccountInfoEAOLocal extends BaseEaoLocal<TbPersonalAccountInfo, Integer>  {

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<TbPersonalAccountInfo> findByCustId(Integer custId);

	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public List<TbPersonalAccountInfo> findDefAccountByCustId(Integer custId);

	
	/**
	 * 
	 * @param custId
	 * @param accountNo
	 * @return
	 */
	public List<TbPersonalAccountInfo> findAccountByAccountNo(Integer custId, Integer accountNo);


	/**
	 * 
	 * @param valueOf
	 */
	public void updateDefaultWithholderFlag(Integer custId);


	
	/**
	 * 
	 * @param accountNo 
	 * @param valueOf
	 * @param account_no
	 * @return
	 */
	public TbPersonalAccountInfo findByCustIdAndAccount(Integer custId, String accountName, String accountNo);

}
