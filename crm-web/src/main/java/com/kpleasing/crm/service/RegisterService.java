package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestRegister;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseRegister;

public interface RegisterService {

	/**
	 * 生成响应XML报文
	 * @param regiResponse
	 * @return
	 * @throws CRMException 
	 */
	public String generateRegisterResponseXML(RequestRegister request, ResponseRegister response, TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateRegisterErrorXML(String code, String description);

}
