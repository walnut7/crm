package com.kpleasing.crm.ejb.pojo;

/**
 * 
 * @author howard.huang
 *
 */
public class Notify {
	
	private String custName;
	private String phone;
	private String certType;
	private String certCode;
	
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the certType
	 */
	public String getCertType() {
		return certType;
	}

	/**
	 * @param certType the certType to set
	 */
	public void setCertType(String certType) {
		this.certType = certType;
	}

	/**
	 * @return the certCode
	 */
	public String getCertCode() {
		return certCode;
	}

	/**
	 * @param certCode the certCode to set
	 */
	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
}
