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
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncFaceVedioTime;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncFaceVedioTime;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SyncFaceVedioTimeService;


@Service("SyncFaceVedioTimeService")
public class SyncFaceVedioTimeServiceImpl implements SyncFaceVedioTimeService {
	
	private static Logger logger = Logger.getLogger(SyncFaceVedioTimeServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateSyncFaceVedioTimeResponseXML(RequestSyncFaceVedioTime syncFaceVedioTimeRequset,
			ResponseSyncFaceVedioTime syncFaceVedioTimeResponse, TbSecurityKey param) throws CRMException {
		try {
			syncFaceVedioTimeResponse.setReturn_code("SUCCESS");
			syncFaceVedioTimeResponse.setReturn_desc("成功！");
			syncFaceVedioTimeResponse.setReq_serial_no(syncFaceVedioTimeRequset.getReq_serial_no());
			syncFaceVedioTimeResponse.setReq_date(syncFaceVedioTimeRequset.getReq_date());
			syncFaceVedioTimeResponse.setRes_serial_no(StringUtil.getSerialNo32());
			syncFaceVedioTimeResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			
			logger.info("开始校验内容.......");
			verification(syncFaceVedioTimeRequset, param);
			
			ejbService.getApiServ().addFaceVedioTime(syncFaceVedioTimeRequset);
			syncFaceVedioTimeResponse.setResult_code("SUCCESS");
			syncFaceVedioTimeResponse.setResult_desc("处理成功！");
			syncFaceVedioTimeResponse.setSign(Security.getSign(syncFaceVedioTimeResponse, param.getSignKey()));
			
			return generateSuccessfulResponseXML(syncFaceVedioTimeResponse);
			
		} catch(InputParamException e) {
			syncFaceVedioTimeResponse.setResult_code(e.getCode());
			syncFaceVedioTimeResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(syncFaceVedioTimeResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncFaceVedioTimeResponse);
		} catch (CRMException e) {
			syncFaceVedioTimeResponse.setResult_code(e.getCode());
			syncFaceVedioTimeResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(syncFaceVedioTimeResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncFaceVedioTimeResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
	}
	
	
	/**
	 * 验证参数防篡改
	 * @param syncFaceVedioTimeRequset
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestSyncFaceVedioTime syncFaceVedioTimeRequset, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!syncFaceVedioTimeRequset.getSecurity_code().equals(param.getSysName()) || !syncFaceVedioTimeRequset.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = syncFaceVedioTimeRequset.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncFaceVedioTimeRequset.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncFaceVedioTimeRequset, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(syncFaceVedioTimeRequset.getCust_name())) {
			throw new InputParamException("客户姓名【cust_name】不能为空！", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(syncFaceVedioTimeRequset.getCust_id())) {
			throw new InputParamException("客户ID【cust_id】不能为空！", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
		
		if(StringUtils.isBlank(syncFaceVedioTimeRequset.getFirst_date())) {
			throw new InputParamException("面签首选时间【first_facetime】不能为空！", syncFaceVedioTimeRequset.getReq_serial_no(), syncFaceVedioTimeRequset.getReq_date());
		}
	}
	
	
	/**
	 * 成功响应报文
	 * @param syncFaceVedioTimeResponse
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String generateSuccessfulResponseXML(ResponseSyncFaceVedioTime syncFaceVedioTimeResponse) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncFaceVedioTimeResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncFaceVedioTimeResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncFaceVedioTimeResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncFaceVedioTimeResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncFaceVedioTimeResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncFaceVedioTimeResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncFaceVedioTimeResponse.getSign()).append("</sign>")
		.append("</head><body>"); 
		msgResponse.append(XMLHelper.getXMLFromBean(syncFaceVedioTimeResponse));
		msgResponse.append("</body></crm>");
		
		logger.info("CRM接口SYNC_FACEVEDIO_TIME响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 失败响应报文
	 * @param syncFaceVedioTimeResponse
	 * @return
	 */
	private String generateFailedResponseXML(ResponseSyncFaceVedioTime syncFaceVedioTimeResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncFaceVedioTimeResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncFaceVedioTimeResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncFaceVedioTimeResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncFaceVedioTimeResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncFaceVedioTimeResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncFaceVedioTimeResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncFaceVedioTimeResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(syncFaceVedioTimeResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(syncFaceVedioTimeResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SYNC_FACEVEDIO_TIME响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	

	@Override
	public String generateSyncFaceVedioTimeErrorXML(String code, String meessge) {
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
	
		logger.info("CRM接口SYNC_FACEVEDIO_TIME响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
}
