package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserInfo;

public interface GetUserInfoService {

	
	/**
	 * 
	 * @param getProfileRequest
	 * @param getProfileResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateGetUserInfoResponseXML(RequestGetUserInfo getUserRequest, ResponseGetUserInfo getUserResponse,
			TbSecurityKey param) throws CRMException;
	
	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateGetUserInfoErrorXML(String code, String message);



	

}
