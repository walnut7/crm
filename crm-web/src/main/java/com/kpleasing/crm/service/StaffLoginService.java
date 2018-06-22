package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestStaffLogin;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseStaffLogin;

public interface StaffLoginService {

	/**
	 * 生成响应XML报文
	 * @param staffLoginRequest
	 * @param regiResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	String generateStaffLoginResponseXML(RequestStaffLogin staffLoginRequest, ResponseStaffLogin staffLoginResponse,
			TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	String generateStaffLoginErrorXML(String code, String message);

}
