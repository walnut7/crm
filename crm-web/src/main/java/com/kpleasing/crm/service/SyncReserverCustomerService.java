package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestReserverCar;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseReserverCar;

public interface SyncReserverCustomerService {

	/**
	 * 生成RESERVER_CAR响应报文
	 * @param reserverCarRequset
	 * @param reserverCarResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateSyncReserverCustomerResponseXML(RequestReserverCar reserverCarRequset,
			ResponseReserverCar reserverCarResponse, TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateSyncReserverCustomerErrorXML(String code, String description);

}
