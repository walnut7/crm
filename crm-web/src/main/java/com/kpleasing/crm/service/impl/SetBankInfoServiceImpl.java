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
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetBankInfo;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SetBankInfoService;
import com.kpleasing.crm.service.SyncCustomerInfoService;

@Service("SetBankInfoService")
public class SetBankInfoServiceImpl implements SetBankInfoService {
	
	private static Logger logger = Logger.getLogger(SetBankInfoServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;
	
	@Autowired
	private SyncCustomerInfoService syncCustomerInfoService;

	@Override
	public String generateSetBankInfoResponseXML(RequestSetBankInfo bankInfoRequset,
			ResponseSetBankInfo bankInfoResponse, TbSecurityKey param) throws CRMException {
		try {
			bankInfoResponse.setReturn_code("SUCCESS");
			bankInfoResponse.setReturn_desc("成功！");
			bankInfoResponse.setReq_serial_no(bankInfoRequset.getReq_serial_no());
			bankInfoResponse.setReq_date(bankInfoRequset.getReq_date());
			bankInfoResponse.setRes_serial_no(StringUtil.getSerialNo32());
			bankInfoResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(bankInfoRequset, param);
			
			ejbService.getApiServ().setBankInfo(bankInfoRequset);
			
			bankInfoResponse.setResult_code("SUCCESS");
			bankInfoResponse.setResult_desc("成功！");
			bankInfoResponse.setSign(Security.getSign(bankInfoResponse, param.getSignKey()));

			final String __custId = bankInfoRequset.getCust_id();
			new Thread() {
				public void run() {
					try {
						syncCustomerInfoService.syncCustomerInfo(Integer.valueOf(__custId), "N");
					} catch (NumberFormatException e) {
						logger.error(e.getMessage(), e);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}.start();
			
			return generateSuccessfulResponseXML(bankInfoResponse);
			
		} catch (CRMException e) {
			bankInfoResponse.setResult_code(e.getCode());
			bankInfoResponse.setResult_desc(e.getDescription());
			try {
				bankInfoResponse.setSign(Security.getSign(bankInfoResponse, param.getSignKey()));
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getDescription(), e);
			return generateFailedResponseXML(bankInfoResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
	}

	private String generateFailedResponseXML(ResponseSetBankInfo bankInfoResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(bankInfoResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(bankInfoResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(bankInfoResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(bankInfoResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(bankInfoResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(bankInfoResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(bankInfoResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(bankInfoResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(bankInfoResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SET_PAYMENT_ACCOUNT_BIND响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	private String generateSuccessfulResponseXML(ResponseSetBankInfo bankInfoResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(bankInfoResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(bankInfoResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(bankInfoResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(bankInfoResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(bankInfoResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(bankInfoResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(bankInfoResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(bankInfoResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(bankInfoResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SET_PAYMENT_ACCOUNT_BIND响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	private void verification(RequestSetBankInfo bankInfoRequset, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!bankInfoRequset.getSecurity_code().equals(param.getSysName()) || !bankInfoRequset.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = bankInfoRequset.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		bankInfoRequset.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(bankInfoRequset, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(bankInfoRequset.getCust_id())) {
			throw new InputParamException("客户ID【cust_id】不能为空！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(bankInfoRequset.getAccount_name())) {
			throw new InputParamException("账户用户名【account_name】不能为空！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(bankInfoRequset.getAccount_no())) {
			throw new InputParamException("账户【account_no】不能为空！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(bankInfoRequset.getBank_phone())) {
			throw new InputParamException("银行预留手机号【bank_phone】不能为空！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(bankInfoRequset.getIs_yjzf_bind())) {
			throw new InputParamException("一键支付标志【is_yizf_bind】不能为空！", bankInfoRequset.getReq_serial_no(), bankInfoRequset.getReq_date());
		}
	}

	@Override
	public String generateSetBankInfoErrorXML(String code, String message) {
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
	
		logger.info("CRM接口SET_PAYMENT_ACCOUNT_BIND响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
