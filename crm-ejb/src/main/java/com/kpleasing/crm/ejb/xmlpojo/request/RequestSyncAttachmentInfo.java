package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestSyncAttachmentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629931232712175492L;
	
	private String attach_type;
	
	private String attach_name;
	
	private String attach_url;
	
	private String file_type_code;

	public String getAttach_type() {
		return attach_type;
	}

	public void setAttach_type(String attach_type) {
		this.attach_type = attach_type;
	}

	public String getAttach_name() {
		return attach_name;
	}

	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}

	public String getAttach_url() {
		return attach_url;
	}

	public void setAttach_url(String attach_url) {
		this.attach_url = attach_url;
	}

	public String getFile_type_code() {
		return file_type_code;
	}

	public void setFile_type_code(String file_type_code) {
		this.file_type_code = file_type_code;
	}

}
