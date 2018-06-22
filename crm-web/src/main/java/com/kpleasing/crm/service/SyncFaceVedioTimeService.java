package com.kpleasing.crm.service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncFaceVedioTime;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncFaceVedioTime;

public interface SyncFaceVedioTimeService {
	
	/**
	 * 
	 * @param syncFaceVedioTimeRequset
	 * @param syncFaceVedioTimeResponse
	 * @param param
	 * @return
	 * @throws CRMException 
	 */
	public String generateSyncFaceVedioTimeResponseXML(RequestSyncFaceVedioTime syncFaceVedioTimeRequset,
			ResponseSyncFaceVedioTime syncFaceVedioTimeResponse, TbSecurityKey param) throws CRMException;

	
	/**
	 * 
	 * @param code
	 * @param description
	 * @return
	 */
	public String generateSyncFaceVedioTimeErrorXML(String code, String description);

}
