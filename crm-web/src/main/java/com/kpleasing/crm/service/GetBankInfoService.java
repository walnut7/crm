package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetBankInfo;

public interface GetBankInfoService {

	
	/**
	 * 
	 * @param bankInfoRequset
	 * @param bankInfoResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateBankInfoResponseXML(RequestGetBankInfo bankInfoRequset, ResponseGetBankInfo bankInfoResponse,
			TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateBankInfoErrorXML(String code, String message);

}
