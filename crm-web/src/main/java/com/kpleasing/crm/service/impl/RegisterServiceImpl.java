package com.kpleasing.crm.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.exception.InputParamException;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestRegister;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseRegister;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.RegisterService;

@Service("RegisterService")
public class RegisterServiceImpl implements RegisterService {
	
	private static Logger logger = Logger.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateRegisterResponseXML(RequestRegister regiRequest, ResponseRegister regiResponse, TbSecurityKey param) throws CRMException {
		try {
			regiResponse.setReturn_code("SUCCESS");
			regiResponse.setReturn_desc("成功！");
			regiResponse.setReq_serial_no(regiRequest.getReq_serial_no());
			regiResponse.setReq_date(regiRequest.getReq_date());
			regiResponse.setRes_serial_no(StringUtil.getSerialNo32());
			regiResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			
			logger.info("开始校验内容.......");
			verification(regiRequest, param);
			
			int custId = ejbService.getApiServ().Register(regiRequest);
			regiResponse.setResult_code("SUCCESS");
			regiResponse.setResult_desc("注册成功");
			regiResponse.setPhone(regiRequest.getPhone());
			regiResponse.setCust_id(String.valueOf(custId));
			
			regiResponse.setSign(Security.getSign(regiResponse, param.getSignKey()));
			
		} catch(InputParamException e) {
			regiResponse.setResult_code(e.getCode());
			regiResponse.setResult_desc(e.getDescription());
			regiResponse.setReq_serial_no(e.getReq_serial_no());
			regiResponse.setReq_date(e.getReq_date());
			try {
				Security.getSign(regiResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(regiResponse);
		} catch (CRMException e) {
			regiResponse.setResult_code(e.getCode());
			regiResponse.setResult_desc(e.getDescription());
			try {
				regiResponse.setSign(Security.getSign(regiResponse, param.getSignKey()));
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(regiResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
			
		return generateSuccessfulResponseXML(regiResponse);
	}

	
	/**
	 * 报文校验
	 * @param regiRequest
	 * @param param
	 * @throws InputParamException 
	 */
	private void verification(RequestRegister regiRequest, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!regiRequest.getSecurity_code().equals(param.getSysName()) || !regiRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = regiRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		regiRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(regiRequest, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(regiRequest.getPhone())) {
			throw new InputParamException("手机号码不能为空！", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
		
		if(StringUtils.isBlank(regiRequest.getChannel_type())) {
			throw new InputParamException("渠道类型【channel_type】不能为空！", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
		
		if(regiRequest.getChannel_type().equals("0") || regiRequest.getChannel_type().equals("1")) {
			if(regiRequest.getChannel_type().equals("1") && StringUtils.isBlank(regiRequest.getChannel_id())) {
				throw new InputParamException("【channel_id】不能为空，请提供微信OPEN_ID！", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
			}
		} else {
			throw new InputParamException("渠道类型【channel_type】参数错误！", regiRequest.getReq_serial_no(), regiRequest.getReq_date());
		}
	}


	/**
	 * 生成成功响应报文
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(ResponseRegister resp) {
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
		.append("<cust_id>").append(resp.getCust_id()).append("</cust_id>")
		.append("<phone>").append(resp.getPhone()).append("</phone>")
		.append("</body></crm>");
		
		logger.info("CRM接口REGISTER响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
	
	
	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(ResponseRegister resp) {
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
		
		logger.info("CRM接口REGISTER响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	@Override
	public String generateRegisterErrorXML(String code, String msg) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append("FAILED").append("</return_code>")
		.append("<return_desc>").append("处理出错").append("</return_desc>")
		.append("<res_serial_no>").append(StringUtil.getSerialNo32()).append("</res_serial_no>")
		.append("<res_date>").append(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)).append("</res_date>")
		.append("</head><body>")
		.append("<result_code>").append(code).append("</result_code>")
		.append("<result_desc>").append(msg).append("</result_desc>")
		.append("</body></crm>");
	
		logger.info("CRM接口REGISTER响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
}
