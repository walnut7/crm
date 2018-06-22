package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetUserProfile;

public interface SetUserProfileService {

	
	/**
	 * 生成响应XML报文
	 * @param setProfileRequest
	 * @param getProfileResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	String generateSetUserProfileResponseXML(RequestSetUserProfile setProfileRequest,
			ResponseSetUserProfile setProfileResponse, TbSecurityKey param) throws CRMException;

	
	/**
	 * 出错报文
	 * @param code
	 * @param description
	 * @return
	 */
	String generateSetUserProfileErrorXML(String code, String message);

}
