package com.kpleasing.crm.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.exception.InputParamException;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.util.XMLHelper;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserProfile;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.GetUserProfileService;


@Service("GetUserProfileService")
public class GetUserProfileServiceImpl implements GetUserProfileService {
	
	private static Logger logger = Logger.getLogger(GetUserProfileServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateGetUserProfileResponseXML(RequestGetUserProfile getProfileRequest,
			ResponseGetUserProfile getProfileResponse, TbSecurityKey param) throws CRMException {
		try {
			getProfileResponse.setReturn_code("SUCCESS");
			getProfileResponse.setReturn_desc("成功！");
			getProfileResponse.setReq_serial_no(getProfileRequest.getReq_serial_no());
			getProfileResponse.setReq_date(getProfileRequest.getReq_date());
			getProfileResponse.setRes_serial_no(StringUtil.getSerialNo32());
			getProfileResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(getProfileRequest, param);

			TbPersonalCustomerInfo tbCustInfo = ejbService.getApiServ().getUserProfile(getProfileRequest);
			getProfileResponse.setResult_code("SUCCESS");
			getProfileResponse.setResult_desc("成功！");
			getProfileResponse.setCert_type(tbCustInfo.getCertType());
			getProfileResponse.setCert_code(tbCustInfo.getCertCode());
			getProfileResponse.setWx_id(tbCustInfo.getWxOpenId());
			getProfileResponse.setCust_id(String.valueOf(tbCustInfo.getCustId()));
			getProfileResponse.setCust_name(tbCustInfo.getCustName());
			getProfileResponse.setPhone(tbCustInfo.getPhone());

			getProfileResponse.setSign(Security.getSign(getProfileResponse, param.getSignKey()));

			return generateSuccessfulResponseXML(getProfileResponse);
			
		} catch (CRMException e) {
			getProfileResponse.setResult_code(e.getCode());
			getProfileResponse.setResult_desc(e.getDescription());
			try {
				getProfileResponse.setSign(Security.getSign(getProfileResponse, param.getSignKey()));
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getDescription(), e);
			return generateFailedResponseXML(getProfileResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
		
	}

	
	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(ResponseGetUserProfile resp) {
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
		
		logger.info("CRM接口GET_USER_PROFILE响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	/**
	 *  生成成功响应报文
	 * @param resp
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private String generateSuccessfulResponseXML(ResponseGetUserProfile resp) throws IllegalArgumentException, IllegalAccessException {
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
		.append("</head><body>");
		
		msgResponse.append(XMLHelper.getXMLFromBean(resp));
		msgResponse.append("</body></crm>");
		
		
//		.append("<result_code>").append(resp.getResult_code()).append("</result_code>")
//		.append("<result_desc>").append(resp.getResult_desc()).append("</result_desc>")
//		.append("<cust_id>").append(resp.getCust_id()).append("</cust_id>")
//		.append("<cust_name>").append(resp.getCust_name()).append("</cust_name>")
//		.append("<phone>").append(resp.getPhone()).append("</phone>")
//		.append("<cert_type>").append(resp.getCert_type()).append("</cert_type>")
//		.append("<cert_code>").append(resp.getCert_code()).append("</cert_code>")
//		.append("<wx_id>").append(resp.getWx_id()).append("</wx_id>")
//		.append("</body></crm>");
		
		logger.info("CRM接口GET_USER_PROFILE响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 参数校验
	 * @param getProfileRequest
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestGetUserProfile getProfileRequest, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!getProfileRequest.getSecurity_code().equals(param.getSysName()) || !getProfileRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = getProfileRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		getProfileRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(getProfileRequest, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(getProfileRequest.getChannel_type())) {
			throw new InputParamException("渠道类型【channel_type】不能为空！", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		}
		
		if(StringUtils.isBlank(getProfileRequest.getChannel_id())) {
			throw new InputParamException("渠道ID【channel_id】不能为空！", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		}
		
		if(!getProfileRequest.getChannel_type().equals("0") && !getProfileRequest.getChannel_type().equals("1") && !getProfileRequest.getChannel_type().equals("2")) {
			throw new InputParamException("渠道类型【channel_type】参数错误！", getProfileRequest.getReq_serial_no(), getProfileRequest.getReq_date());
		} 
	}

	@Override
	public String generateGetUserProfileErrorXML(String code, String message) {
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
