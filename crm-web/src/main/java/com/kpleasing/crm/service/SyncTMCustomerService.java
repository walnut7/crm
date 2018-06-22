package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncTMCustomer;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncTMCustomer;

public interface SyncTMCustomerService {

	
	/**
	 * 
	 * @param syncTmCustRequset
	 * @param syncTmCustResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateSyncTMCustomerResponseXML(RequestSyncTMCustomer syncTmCustRequset,
			ResponseSyncTMCustomer syncTmCustResponse, TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateSyncTMCustomerErrorXML(String code, String description);

}
