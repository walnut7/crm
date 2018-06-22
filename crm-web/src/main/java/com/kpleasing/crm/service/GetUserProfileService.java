package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserProfile;

public interface GetUserProfileService {

	
	/**
	 *  生成响应XML报文
	 * @param getProfileRequest
	 * @param getProfileResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	String generateGetUserProfileResponseXML(RequestGetUserProfile getProfileRequest,
			ResponseGetUserProfile getProfileResponse, TbSecurityKey param) throws CRMException;

	
	/**
	 * 出错报文
	 * @param code
	 * @param description
	 * @return
	 */
	String generateGetUserProfileErrorXML(String code, String message);

}
