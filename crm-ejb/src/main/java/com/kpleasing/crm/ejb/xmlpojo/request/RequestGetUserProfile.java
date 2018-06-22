package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestGetUserProfile extends Request implements Serializable {

	/** **/
	private static final long serialVersionUID = -2629924153776365930L;
	
	private String channel_type; 
	
	private String channel_id;

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	} 
}
