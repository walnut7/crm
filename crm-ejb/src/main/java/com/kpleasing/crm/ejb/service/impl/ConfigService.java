package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.eao.local.TbSecurityKeyEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbSysConfigEAOLocal;
import com.kpleasing.crm.ejb.entity.TbSysConfig;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;

@Stateless
@LocalBean
public class ConfigService implements Serializable, ConfigServiceLocal {

	/**	 * 	 */	
	private static final long serialVersionUID = -7145769151335480928L;
	private static Logger logger = Logger.getLogger(ConfigService.class);
	
	private Configurate config = null;
	
	@EJB
	private TbSecurityKeyEAOLocal securKeyEao;
	
	@EJB
	private TbSysConfigEAOLocal sysConfigEao;

	
	@Override
	public Configurate getConfig() {
		if(null == config) {
			logger.info("初始化话配置参数.");
			config = new Configurate();
			List<TbSysConfig> list = sysConfigEao.findAll();
			Map<String, String> map = new HashMap<String, String>();
			for (TbSysConfig conf : list) {
				map.put(conf.getKey(), conf.getValue());
			}
			
			config.ESB_SERVER_URL = map.get("ESB_SERVER_URL");
			config.DES_KEY = map.get("DES_KEY");
			config.DES_IV = map.get("DES_IV");
			config.ESB_DES_KEY = map.get("ESB_DES_KEY");
			config.ESB_DES_IV = map.get("ESB_DES_IV");
			config.ESB_SIGN_KEY = map.get("ESB_SIGN_KEY");
			config.ESB_SEC_CODE = map.get("ESB_SEC_CODE");
			config.ESB_SEC_VALUE = map.get("ESB_SEC_VALUE");
			config.MODEL_FILE_PATH = map.get("MODEL_FILE_PATH");
			config.LOCAL_ATTACH_PATH = map.get("LOCAL_ATTACH_PATH");
			config.REMOTE_ATTACH_PATH = map.get("REMOTE_ATTACH_PATH");
			config.EWECHAT_URL = map.get("EWECHAT_URL");
			
			config.setSecurityKeyList(securKeyEao.findAll());
		}
		return config;
	}
}
