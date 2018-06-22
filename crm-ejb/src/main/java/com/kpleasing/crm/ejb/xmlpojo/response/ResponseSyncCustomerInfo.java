package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;

public class ResponseSyncCustomerInfo extends Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3593054060258886596L;
	
	private String result_code;
	
	private String result_desc;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	
}
