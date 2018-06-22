package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetBankInfo;

public interface SetBankInfoService {

	
	/**
	 * 
	 * @param setBankInfoRequset
	 * @param setBankInfoResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateSetBankInfoResponseXML(RequestSetBankInfo setBankInfoRequset,
			ResponseSetBankInfo setBankInfoResponse, TbSecurityKey param) throws CRMException;

	
	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateSetBankInfoErrorXML(String code, String message);

}
