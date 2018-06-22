package com.kpleasing.crm.ejb.xmlpojo.response;

import java.io.Serializable;
import java.util.List;

import com.kpleasing.crm.ejb.pojo.BankInfo;

public class ResponseGetBankInfo extends Response implements Serializable {

	/**	 *	 */
	private static final long serialVersionUID = 8260186478780900290L;
	private String result_code;
	private String result_desc;
	private String cust_id;
	private List<BankInfo> accounts;
	
	
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

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public List<BankInfo> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankInfo> accounts) {
		this.accounts = accounts;
	}
}
