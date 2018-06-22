package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.pojo.Notify;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.service.local.NotifyServiceLocal;
import com.kpleasing.crm.ejb.util.HttpHelper;

@Stateless
@LocalBean
public class NotifyService implements Serializable, NotifyServiceLocal {

	/** * */
	private static final long serialVersionUID = 6513010644228738688L;
	private static Logger logger = Logger.getLogger(NotifyService.class);
	
	@EJB
	private ConfigServiceLocal configServ;

	@Override
	public void notifly(Notify notify) {
		try {
			StringBuilder msg = new StringBuilder();
			msg.append("<?xml version=\"1.0\"?>")
			.append("<notify><head>")
			/*.append("<return_code>").append(resp.getReturn_code()).append("</return_code>")
			.append("<return_desc>").append(resp.getReturn_desc()).append("</return_desc>")
			.append("<req_serial_no>").append(resp.getReq_serial_no()).append("</req_serial_no>")
			.append("<req_date>").append(resp.getReq_date()).append("</req_date>")
			.append("<res_serial_no>").append(resp.getRes_serial_no()).append("</res_serial_no>")
			.append("<res_date>").append(resp.getRes_date()).append("</res_date>")
			.append("<sign>").append(resp.getSign()).append("</sign>")
			.append("</head><body>")
			.append("<result_code>").append(resp.getResult_code()).append("</result_code>")
			.append("<result_desc>").append(resp.getResult_desc()).append("</result_desc>")*/
			.append("</body></notify>");
			Configurate conf = configServ.getConfig();
			StringBuilder params = new StringBuilder();
			params.append("cust_name=").append(notify.getCustName())
				  .append("&phone=").append(notify.getPhone())
				  .append("&cert_code=").append(notify.getCertCode());
			String url = conf.EWECHAT_URL + "/notify?"+params.toString();
			HttpHelper.doHttpPost(url, "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
}