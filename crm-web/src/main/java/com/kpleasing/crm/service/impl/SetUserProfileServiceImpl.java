package com.kpleasing.crm.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.pojo.Notify;
import com.kpleasing.crm.ejb.service.local.NotifyServiceLocal;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetUserProfile;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SetUserProfileService;


@Service("SetUserProfileService")
public class SetUserProfileServiceImpl implements SetUserProfileService {
	
	private static Logger logger = Logger.getLogger(SetUserProfileServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateSetUserProfileResponseXML(RequestSetUserProfile setProfileRequest,
			ResponseSetUserProfile setProfileResponse, TbSecurityKey param) throws CRMException {
		try {
			setProfileResponse.setReturn_code("SUCCESS");
			setProfileResponse.setReturn_desc("成功！");
			setProfileResponse.setReq_serial_no(setProfileRequest.getReq_serial_no());
			setProfileResponse.setReq_date(setProfileRequest.getReq_date());
			setProfileResponse.setRes_serial_no(StringUtil.getSerialNo32());
			setProfileResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(setProfileRequest, param);

			ejbService.getApiServ().setUserProfile(setProfileRequest);
			setProfileResponse.setResult_code("SUCCESS");
			setProfileResponse.setResult_desc("更新成功！");

			setProfileResponse.setSign(Security.getSign(setProfileResponse, param.getSignKey()));
			
			// 发送下单通知
			final NotifyServiceLocal notifyServ = ejbService.getNotifyServ();
			final Notify __notify = new Notify(); 
			__notify.setCustName(setProfileRequest.getCust_name());
			__notify.setCertCode(setProfileRequest.getCert_code());
			__notify.setPhone(setProfileRequest.getPhone());
			new Thread() {
				public void run() {
					notifyServ.notifly(__notify);
				}
			}.start();
			
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(setProfileResponse);
	}

	
	/**
	 * 生成成功响应报文
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(ResponseSetUserProfile resp) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(resp.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(resp.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(resp.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(resp.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(resp.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(resp.getRes_date()).append("</res_date>")
		.append("<sign>").append(resp.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(resp.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(resp.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SET_USER_PROFILE响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	/**
	 * 
	 * @param setProfileRequest
	 * @param param
	 */
	private void verification(RequestSetUserProfile setProfileRequest, TbSecurityKey param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generateSetUserProfileErrorXML(String code, String message) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append("FAILED").append("</return_code>")
		.append("<return_desc>").append("处理出错").append("</return_desc>")
		.append("<res_serial_no>").append(StringUtil.getSerialNo32()).append("</res_serial_no>")
		.append("<res_date>").append(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)).append("</res_date>")
		.append("</head><body>")
		.append("<result_code>").append(code).append("</result_code>")
		.append("<result_desc>").append(message).append("</result_desc>")
		.append("</body></crm>");
	
		logger.info("CRM接口GET_USER_PROFILE响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
}
