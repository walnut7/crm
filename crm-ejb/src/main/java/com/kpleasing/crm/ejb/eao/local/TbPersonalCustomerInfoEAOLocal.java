package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;

@Local
public interface TbPersonalCustomerInfoEAOLocal extends BaseEaoLocal<TbPersonalCustomerInfo, Integer> {

	/**
	 * 根据客户手机号查询
	 * @param phone
	 * @return
	 */
	public TbPersonalCustomerInfo findCustomerByPhone(String phone);

	
	/**
	 * 根据微信OPEN_ID查询
	 * @param phone
	 * @param channelId
	 * @return
	 */
	public TbPersonalCustomerInfo findCustomerByOpenId(String openId);


	/**
	 * 根据手机号和微信OPEN_ID查询
	 * @param phone
	 * @param channelId
	 * @return
	 */
	public TbPersonalCustomerInfo findCustomerByPhoneOrOpenId(String phone, String openId);


	/**
	 * 
	 * @param id
	 * @return
	 */
	public TbPersonalCustomerInfo getUserInfo(int id);
	
	/**
	 * 根据条件查询客户列表
	 * @param customerInfo
	 * @return
	 */
	public List<TbPersonalCustomerInfo> findCustomerListByProperty(TbPersonalCustomerInfo customerInfo);

	/**
	 * 根据条件查询所有记录数
	 * @param customerInfo
	 * @return
	 */
	public int findCustomerListByCount(TbPersonalCustomerInfo customerInfo);


	/**
	 * 根据证件类型，证件号码查询
	 * @param cert_type
	 * @param cert_code
	 * @return
	 */
	public TbPersonalCustomerInfo findCustomerByCertInfo(String cert_type, String cert_code);


	
	/**
	 * 根据CUST_ID查询
	 * @param channelId
	 * @return
	 */
	public TbPersonalCustomerInfo findCustomerByCustId(Integer custId);
}
