package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncCustomerInfo;

public interface SyncCustomerInfoService {

	/**
	 * 生成
	 * @param syncCustInfo
	 * @param syncCustInfoResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateSyncCustInfoResponseXML(RequestSetUserInfo syncCustInfo,
			ResponseSyncCustomerInfo syncCustInfoResponse, TbSecurityKey param) throws CRMException, Exception;
	
	

	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateSyncCustInfoErrorXML(String code, String description);



	/**
	 * 同步客户信息
	 * @param custId
	 */
	public String syncCustomerInfo(int custId,String flag) throws Exception ;



	

}
