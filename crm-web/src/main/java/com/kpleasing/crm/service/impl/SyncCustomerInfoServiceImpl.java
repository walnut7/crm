package com.kpleasing.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.exception.InputParamException;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.EncryptUtil;
import com.kpleasing.crm.ejb.util.HttpHelper;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.util.XMLHelper;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncAccountInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncContactInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncCustomerInfo;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SyncCustomerInfoService;


@Service("SyncCustomerInfoService")
public class SyncCustomerInfoServiceImpl implements SyncCustomerInfoService {
	
	private static Logger logger = Logger.getLogger(SyncCustomerInfoServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;


	@Override
	public String generateSyncCustInfoResponseXML(RequestSetUserInfo syncCustInfoRequest,
			ResponseSyncCustomerInfo syncCustInfoResponse, TbSecurityKey param) throws CRMException, Exception {
		try {
			syncCustInfoResponse.setReturn_code("SUCCESS");
			syncCustInfoResponse.setReturn_desc("成功！");
			syncCustInfoResponse.setReq_serial_no(syncCustInfoRequest.getReq_serial_no());
			syncCustInfoResponse.setReq_date(syncCustInfoRequest.getReq_date());
			syncCustInfoResponse.setRes_serial_no(StringUtil.getSerialNo32());
			syncCustInfoResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(syncCustInfoRequest, param);

			logger.info("保存客户信息至本地.......");
			ejbService.getApiServ().saveCustInfo(syncCustInfoRequest);
			
			logger.info("同步客户信息至其他系统.......");
			String result = syncCustomerInfo(Integer.valueOf(syncCustInfoRequest.getCust_id()),"Y");
			
			if("SUCCESS".equals(result)) {
				syncCustInfoResponse.setResult_code("SUCCESS");
				syncCustInfoResponse.setResult_desc("提交成功！");
			} else {
				syncCustInfoResponse.setResult_code("FAILED");
				syncCustInfoResponse.setResult_desc(result);
			}
			
			syncCustInfoResponse.setSign(Security.getSign(syncCustInfoResponse, param.getSignKey()));

		} catch (CRMException e) {
			syncCustInfoResponse.setResult_code(e.getCode());
			syncCustInfoResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(syncCustInfoResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncCustInfoResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(syncCustInfoResponse);
	}


	/**
	 * 生成失败响应报文
	 * @param syncCustInfoResponse
	 * @return
	 */
	private String generateFailedResponseXML(ResponseSyncCustomerInfo syncCustInfoResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncCustInfoResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncCustInfoResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncCustInfoResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncCustInfoResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncCustInfoResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncCustInfoResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncCustInfoResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(syncCustInfoResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(syncCustInfoResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SET_USER_INFO响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	/**
	 *  生成成功响应报文
	 * @param syncCustInfoResponse
	 * @return
	 */
	private String generateSuccessfulResponseXML(ResponseSyncCustomerInfo syncCustInfoResponse) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\"?>")
		.append("<crm><head>")
		.append("<return_code>").append(syncCustInfoResponse.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(syncCustInfoResponse.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(syncCustInfoResponse.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(syncCustInfoResponse.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(syncCustInfoResponse.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(syncCustInfoResponse.getRes_date()).append("</res_date>")
		.append("<sign>").append(syncCustInfoResponse.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(syncCustInfoResponse.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(syncCustInfoResponse.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("CRM接口SET_USER_INFO响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	
	/**
	 * 报文校验
	 * @param syncCustInfoRequest
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestSetUserInfo syncCustInfoRequest, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!syncCustInfoRequest.getSecurity_code().equals(param.getSysName()) || !syncCustInfoRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = syncCustInfoRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncCustInfoRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncCustInfoRequest, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(syncCustInfoRequest.getCert_code())) {
			throw new InputParamException("证件号码不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getCust_name())) {
			throw new InputParamException("客户姓名不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getPhone())) {
			throw new InputParamException("手机号不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getCust_name_spell())) {
			throw new InputParamException("姓名拼音不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getNation())) {
			throw new InputParamException("民族不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getCert_org())) {
			throw new InputParamException("发证机关不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getLive_status())) {
			throw new InputParamException("住房情况不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getMarr_status())) {
			throw new InputParamException("婚姻状况不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getEdu_level())) {
			throw new InputParamException("学历不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getDrive_model())) {
			throw new InputParamException("准驾车型不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getFamily_tel())) {
			throw new InputParamException("家庭电话不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getContact_address())) {
			throw new InputParamException("居住地址不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getEmail())) {
			throw new InputParamException("邮箱不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getZip_code())) {
			throw new InputParamException("邮编不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getWork_unit())) {
			throw new InputParamException("工作单位不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getEntry_year())) {
			throw new InputParamException("入职年限不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getPosition())) {
			throw new InputParamException("职务不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getWork_addr())) {
			throw new InputParamException("办公地址不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getUnit_tel())) {
			throw new InputParamException("工作电话不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getAnnual_income())) {
			throw new InputParamException("年收入不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getIncome_status())) {
			throw new InputParamException("收入状态不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		if(StringUtils.isBlank(syncCustInfoRequest.getIncome_from())) {
			throw new InputParamException("收入来源不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		if(null != syncCustInfoRequest.getContacts() && syncCustInfoRequest.getContacts().size() > 0){
			List<RequestSyncContactInfo> list = syncCustInfoRequest.getContacts();
			
			for(RequestSyncContactInfo ci: list){
				if(StringUtils.isBlank(ci.getContact_name())){
					throw new InputParamException("联系人姓名不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ci.getRelation())){
					throw new InputParamException("联系人关系不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ci.getContact_cert_type())){
					throw new InputParamException("联系人证件类型不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ci.getContact_cert_code())){
					throw new InputParamException("联系人证件号码不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ci.getContact_phone())){
					throw new InputParamException("联系人手机号不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}else{
					String tmp_str = ci.getContact_phone().replaceAll(" ", "");
					if(ci.getContact_phone().length() !=11  ){
						throw new InputParamException("联系人手机号必须为11位！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
					}
					if("1".equals(tmp_str.substring(0, 1)) && tmp_str.length() != 11){
						throw new InputParamException("联系人手机号格式不正确！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
					}
				}
				
			}
		}else{
			throw new InputParamException("联系人信息不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		if(null != syncCustInfoRequest.getAccounts()){
			List<RequestSyncAccountInfo> list = syncCustInfoRequest.getAccounts();
			for(RequestSyncAccountInfo ai: list){
				if(StringUtils.isBlank(ai.getAcc_no())){
					throw new InputParamException("借记卡号不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ai.getBank_code())){
					throw new InputParamException("借记卡开户行简称不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
				if(StringUtils.isBlank(ai.getBank_full_name())){
					throw new InputParamException("借记卡开户行全称不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
				}
			}
			
		}else{
			throw new InputParamException("银行账号不能为空！", syncCustInfoRequest.getReq_serial_no(), syncCustInfoRequest.getReq_date());
		}
		
		
	}


	@Override
	public String generateSyncCustInfoErrorXML(String code, String message) {
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
	
		logger.info("CRM接口SET_USER_INFO应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}




	@Override
	public String syncCustomerInfo(int custId,String wxFlag) throws Exception {
		Configurate config = ejbService.getConfigServ().getConfig();
		logger.info("客户ID为----->" + custId);
		String subxml =  ejbService.getCustServ().structCustRequestParam(custId,wxFlag);

		String xmlString = EncryptUtil.encrypt(config.ESB_DES_KEY, config.ESB_DES_IV, subxml);

		String result = HttpHelper.doHttpPost(config.ESB_SERVER_URL, xmlString);
		logger.info("返回报文信息：" + result);
		String xmlResult = EncryptUtil.decrypt(config.ESB_DES_KEY, config.ESB_DES_IV, result);
		ResponseSyncCustomerInfo responseSci = new ResponseSyncCustomerInfo();
		XMLHelper.getBeanFromXML(xmlResult, responseSci);

		if ("SUCCESS".equals(responseSci.getReturn_code()) && "SUCCESS".equals(responseSci.getResult_code())) {
			return "SUCCESS";
		} else {
			if("FAILED".equals(responseSci.getReturn_code())){
				return responseSci.getReturn_desc();
			}else{
				return responseSci.getResult_desc();
			}
		}
			
	}
}
