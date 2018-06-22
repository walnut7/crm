package com.kpleasing.crm.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.entity.TbStaffInfo;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestStaffLogin;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseStaffLogin;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.StaffLoginService;


@Service("StaffLoginService")
public class StaffLoginServiceImpl implements StaffLoginService {
	
	private static Logger logger = Logger.getLogger(StaffLoginServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateStaffLoginResponseXML(RequestStaffLogin staffLoginRequest, ResponseStaffLogin staffLoginResponse,
			TbSecurityKey param) throws CRMException {
		try {
			staffLoginResponse.setReturn_code("SUCCESS");
			staffLoginResponse.setReturn_desc("成功！");
			staffLoginResponse.setReq_serial_no(staffLoginRequest.getReq_serial_no());
			staffLoginResponse.setReq_date(staffLoginRequest.getReq_date());
			staffLoginResponse.setRes_serial_no(StringUtil.getSerialNo32());
			staffLoginResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(staffLoginRequest, param);

			TbStaffInfo tbStaff = ejbService.getApiServ().getStaffLoginInfo(staffLoginRequest);
			staffLoginResponse.setResult_code("SUCCESS");
			staffLoginResponse.setResult_desc("用户登录成功！");
			staffLoginResponse.setStaff_name(tbStaff.getStaffName());
			staffLoginResponse.setStaff_no(tbStaff.getStaffNo());
			staffLoginResponse.setIs_modify_pwd(String.valueOf(tbStaff.getIsModifyPwd()));
			staffLoginResponse.setPhone(tbStaff.getPhone());
			staffLoginResponse.setStaff_email(tbStaff.getEmail());

			staffLoginResponse.setSign(Security.getSign(staffLoginResponse, param.getSignKey()));

		} catch (CRMException e) {
			staffLoginResponse.setResult_code(e.getCode());
			staffLoginResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(staffLoginResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(staffLoginResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(staffLoginResponse);
	}

	private void verification(RequestStaffLogin staffLoginRequest, TbSecurityKey param) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 生成成功响应报文
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(ResponseStaffLogin resp) {
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
		.append("<staff_name>").append(resp.getStaff_name()).append("</staff_name>")
		.append("<staff_no>").append(resp.getStaff_no()).append("</staff_no>")
		.append("<staff_email>").append(resp.getStaff_email()).append("</staff_email>")
		.append("<is_modify_pwd>").append(resp.getIs_modify_pwd()).append("</is_modify_pwd>")
		.append("<phone>").append(resp.getPhone()).append("</phone>")
		.append("</body></crm>");
		
		logger.info("CRM接口STAFF_LOGIN响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
	
	
	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(ResponseStaffLogin resp) {
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
		
		logger.info("CRM接口STAFF_LOGIN响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	@Override
	public String generateStaffLoginErrorXML(String code, String message) {
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
	
		logger.info("CRM接口STAFF_LOGIN响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
