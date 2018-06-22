package com.kpleasing.crm.ejb.exception;

public class CRMException extends Exception {

	/**	 * 	 */
	private static final long serialVersionUID = -1677892122963139748L;

	private String code;

	private String description;

	public CRMException() { super(); }
	
	public CRMException(String code, String description) {
		this.setCode(code);
		this.setDescription(description);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
