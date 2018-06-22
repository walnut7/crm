package com.kpleasing.crm.service.impl;

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
import com.kpleasing.crm.ejb.xmlpojo.request.RequestReserverCar;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseReserverCar;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SyncReserverCustomerService;


@Service("ReserverCarService")
public class SyncReserverCustomerServiceImpl implements SyncReserverCustomerService {
	
	private static Logger logger = Logger.getLogger(SyncReserverCustomerServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateSyncReserverCustomerResponseXML(RequestReserverCar reserverCarRequset,
			ResponseReserverCar reserverCarResponse, TbSecurityKey param) throws CRMException  {
		try {
			reserverCarResponse.setReturn_code("SUCCESS");
			reserverCarResponse.setReturn_desc("成功！");
			reserverCarResponse.setReq_serial_no(reserverCarRequset.getReq_serial_no());
			reserverCarResponse.setReq_date(reserverCarRequset.getReq_date());
			reserverCarResponse.setRes_serial_no(StringUtil.getSerialNo32());
			reserverCarResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			
			logger.info("开始校验内容.......");
			verification(reserverCarRequset, param);
			
			ejbService.getApiServ().appendReserverCustomerInfo(reserverCarRequset);
			reserverCarResponse.setResult_code("SUCCESS");
			reserverCarResponse.setResult_desc("处理成功！");
			reserverCarResponse.setSign(Security.getSign(reserverCarResponse, param.getSignKey()));
			
			return generateSuccessfulResponseXML(reserverCarResponse);
			
		} catch(InputParamException e) {
			reserverCarResponse.setResult_code(e.getCode());
			reserverCarResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(reserverCarResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(reserverCarResponse);
		} catch (CRMException e) {
			reserverCarResponse.setResult_code(e.getCode());
			reserverCarResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(reserverCarResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(reserverCarResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
	}

	
	/**
	 * 生成成功响应报文
	 * @param reserverCarResponse
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private String generateSuccessfulResponseXML(ResponseReserverCar reserverCarResponse) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(reserverCarResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(reserverCarResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(reserverCarResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(reserverCarResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(reserverCarResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(reserverCarResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(reserverCarResponse.getSign()).append("</sign>")
		.append("</head><body>"); 
		msgResponse.append(XMLHelper.getXMLFromBean(reserverCarResponse));
		msgResponse.append("</body></crm>");
		
		logger.info("CRM接口RESERVER_CAR响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 生成失败响应报文
	 * @param reserverCarResponse
	 * @return
	 */
	private String generateFailedResponseXML(ResponseReserverCar reserverCarResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(reserverCarResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(reserverCarResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(reserverCarResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(reserverCarResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(reserverCarResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(reserverCarResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(reserverCarResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(reserverCarResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(reserverCarResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SYNC_RESERVER_CUSTOMER响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	

	/**
	 * 参数规格校验
	 * @param reserverCarRequset
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestReserverCar reserverCarRequset, TbSecurityKey param) throws InputParamException  {
		// 访问权限校验
		if(!reserverCarRequset.getSecurity_code().equals(param.getSysName()) || !reserverCarRequset.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", reserverCarRequset.getReq_serial_no(), reserverCarRequset.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = reserverCarRequset.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", reserverCarRequset.getReq_serial_no(), reserverCarRequset.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		reserverCarRequset.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(reserverCarRequset, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", reserverCarRequset.getReq_serial_no(), reserverCarRequset.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", reserverCarRequset.getReq_serial_no(), reserverCarRequset.getReq_date());
		}
	}


	@Override
	public String generateSyncReserverCustomerErrorXML(String code, String meessge) {
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
	
		logger.info("CRM接口SYNC_RESERVER_CUSTOMER响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
}
