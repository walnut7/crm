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
import com.kpleasing.crm.ejb.util.XMLHelper;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncTMCustomer;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncTMCustomer;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SyncTMCustomerService;


@Service("SyncTMCustomerService")
public class SyncTMCustomerServiceImpl  implements SyncTMCustomerService  {
	
	private static Logger logger = Logger.getLogger(SyncTMCustomerServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateSyncTMCustomerResponseXML(RequestSyncTMCustomer syncTmCustRequset,
			ResponseSyncTMCustomer syncTmCustResponse, TbSecurityKey param) throws CRMException {
		try {
			syncTmCustResponse.setReturn_code("SUCCESS");
			syncTmCustResponse.setReturn_desc("成功！");
			syncTmCustResponse.setReq_serial_no(syncTmCustRequset.getReq_serial_no());
			syncTmCustResponse.setReq_date(syncTmCustRequset.getReq_date());
			syncTmCustResponse.setRes_serial_no(StringUtil.getSerialNo32());
			syncTmCustResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			
			logger.info("开始校验内容.......");
			verification(syncTmCustRequset, param);
			
			ejbService.getApiServ().addCustomerFromTM(syncTmCustRequset, syncTmCustResponse);
			syncTmCustResponse.setResult_code("SUCCESS");
			syncTmCustResponse.setResult_desc("处理成功！");
		//	syncTmCustResponse.setCust_id(String.valueOf(custId));
			syncTmCustResponse.setSign(Security.getSign(syncTmCustResponse, param.getSignKey()));
			
			return generateSuccessfulResponseXML(syncTmCustResponse);
			
		} catch(InputParamException e) {
			syncTmCustResponse.setResult_code(e.getCode());
			syncTmCustResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(syncTmCustResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncTmCustResponse);
		} catch (CRMException e) {
			syncTmCustResponse.setResult_code(e.getCode());
			syncTmCustResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(syncTmCustResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncTmCustResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
	}
	
	
	/**
	 *  生成成功响应报文
	 * @param syncTmCustResponse
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String generateSuccessfulResponseXML(ResponseSyncTMCustomer syncTmCustResponse) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncTmCustResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncTmCustResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncTmCustResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncTmCustResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncTmCustResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncTmCustResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncTmCustResponse.getSign()).append("</sign>")
		.append("</head><body>"); 
		msgResponse.append(XMLHelper.getXMLFromBean(syncTmCustResponse));
		msgResponse.append("</body></crm>");
		
		logger.info("CRM接口SYNC_TM_CUSTOMER响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 生成失败响应报文
	 * @param syncTmCustResponse
	 * @return
	 */
	private String generateFailedResponseXML(ResponseSyncTMCustomer syncTmCustResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncTmCustResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncTmCustResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncTmCustResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncTmCustResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncTmCustResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncTmCustResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncTmCustResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(syncTmCustResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(syncTmCustResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SYNC_TM_CUSTOMER响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 参数规格校验
	 * @param syncTmCustRequset
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestSyncTMCustomer syncTmCustRequset, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!syncTmCustRequset.getSecurity_code().equals(param.getSysName()) || !syncTmCustRequset.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = syncTmCustRequset.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncTmCustRequset.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncTmCustRequset, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(syncTmCustRequset.getCust_name())) {
			throw new InputParamException("客户姓名【cust_name】不能为空！", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(syncTmCustRequset.getPhone())) {
			throw new InputParamException("手机号码【phone】不能为空！", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(syncTmCustRequset.getCert_type())) {
			throw new InputParamException("客户证件类型【cert_type】不能为空！", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(syncTmCustRequset.getCert_code())) {
			throw new InputParamException("客户证件号码【cert_code】不能为空！", syncTmCustRequset.getReq_serial_no(), syncTmCustRequset.getReq_date());
		}
	}

	
	@Override
	public String generateSyncTMCustomerErrorXML(String code, String meessge) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append("FAILED").append("</return_code>")
		.append("<return_desc>").append("处理出错").append("</return_desc>")
		.append("<res_serial_no>").append(StringUtil.getSerialNo32()).append("</res_serial_no>")
		.append("<res_date>").append(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)).append("</res_date>")
		.append("</head><body>")
		.append("<result_code>").append(code).append("</result_code>")
		.append("<result_desc>").append(meessge).append("</result_desc>")
		.append("</body></crm>");
	
		logger.info("CRM接口SYNC_TM_CUSTOMER响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
