package com.kpleasing.crm.ejb.config;

import java.io.Serializable;
import java.util.List;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;



public class Configurate implements Serializable {
	
    /**	 * 	 */
	private static final long serialVersionUID = 5258518369380768553L;
	
	public String ESB_SERVER_URL = null;
    public String DES_KEY = null;
    public String DES_IV = null;
    public String ESB_DES_KEY = null;
    public String ESB_DES_IV = null;
    public String ESB_SIGN_KEY = null;
    public String ESB_SEC_CODE = null;
    public String ESB_SEC_VALUE = null;
    public String MODEL_FILE_PATH = null;
    public String LOCAL_ATTACH_PATH = null;
    public String REMOTE_ATTACH_PATH = null;
    public String EWECHAT_URL = null;
    public String NAVIGATION_CONF_PATH = null;
    
    private List<TbSecurityKey> securityKeyList;
    
	public List<TbSecurityKey> getSecurityKeyList() {
		return securityKeyList;
	}

	public void setSecurityKeyList(List<TbSecurityKey> securityKeyList) {
		this.securityKeyList = securityKeyList;
	}

	
	/**
	 * 根据apiCode确认配置
	 * @param apiCode
	 * @return
	 */
	public TbSecurityKey getSecurityKey(String apiCode) {
		List<TbSecurityKey> tbSecurKeyList = this.getSecurityKeyList();
		if(tbSecurKeyList != null) {
			for(TbSecurityKey tbSecurKey : tbSecurKeyList) {
				if(apiCode.equals(tbSecurKey.getApiName())) {
					return tbSecurKey; 
				}
			}
		}
		return null;
	}
	
}
