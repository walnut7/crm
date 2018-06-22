package com.kpleasing.crm.ejb.xmlpojo.request;

import java.io.Serializable;

public class RequestRegister extends Request implements Serializable {
	
	/**	 * 	 */
	private static final long serialVersionUID = 1117127016389303181L;

	private String phone;
	
	private String channel_type;
	
	private String channel_id;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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
