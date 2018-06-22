package com.kpleasing.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.exception.InputParamException;
import com.kpleasing.crm.ejb.pojo.Account;
import com.kpleasing.crm.ejb.pojo.Contact;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.util.XMLHelper;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserInfo;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.GetUserInfoService;

@Service("GetUserInfoService")
public class GetUserInfoServiceImpl implements GetUserInfoService {

	private static Logger logger = Logger.getLogger(GetUserInfoServiceImpl.class);
	
	@Autowired
	private EjbService ejbService;

	@Override
	public String generateGetUserInfoResponseXML(RequestGetUserInfo getUserRequest, ResponseGetUserInfo getUserResponse,
			TbSecurityKey param) throws CRMException {
		try {
			getUserResponse.setReturn_code("SUCCESS");
			getUserResponse.setReturn_desc("成功！");
			getUserResponse.setReq_serial_no(getUserRequest.getReq_serial_no());
			getUserResponse.setReq_date(getUserRequest.getReq_date());
			getUserResponse.setRes_serial_no(StringUtil.getSerialNo32());
			getUserResponse.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(getUserRequest, param);
			
			logger.info("开始查找响应内容.......");
			TbPersonalCustomerInfo tbCustInfo = ejbService.getApiServ().getUserInfo(getUserRequest);
			generateResponseBean(getUserResponse, tbCustInfo);
			getUserResponse.setSign(Security.getSign(getUserResponse, param.getSignKey()));
			
			return generateSuccessfulResponseXML(getUserResponse);
		} catch (CRMException e) {
			getUserResponse.setResult_code(e.getCode());
			getUserResponse.setResult_desc(e.getDescription());
			try {
				Security.getSign(getUserResponse, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new CRMException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(getUserResponse);
		} catch (IllegalAccessException e) {
			throw new CRMException("FAILED", "签名失败");
		}
	}
	
	
	/**
	 * 封装响应Bean
	 * @param getUserResponse
	 * @param tbCustInfo
	 */
	private void generateResponseBean(ResponseGetUserInfo getUserResponse, TbPersonalCustomerInfo tbCustInfo) {
		getUserResponse.setResult_code("SUCCESS");
		getUserResponse.setResult_desc("操作成功！");
		
		// 基本信息
		getUserResponse.setCust_name(tbCustInfo.getCustName());
		getUserResponse.setCert_type(tbCustInfo.getCertType());
		getUserResponse.setCert_code(tbCustInfo.getCertCode());
		getUserResponse.setPhone(tbCustInfo.getPhone());
		getUserResponse.setCust_type(tbCustInfo.getCustType());
		getUserResponse.setChannel_source(tbCustInfo.getChannel());
		getUserResponse.setRegister_time(DateUtil.date2Str(tbCustInfo.getCreateAt(), DateUtil.yyyyMMddHHmmss));
		
		// 客户详情
		TbPersonalCustomerDetailInfo tbCustDetailInfo = tbCustInfo.getTbPersonalCustomerDetailInfo();
		if(null != tbCustDetailInfo) {
			getUserResponse.setCust_name_spell(tbCustDetailInfo.getCustNameSpell());
			getUserResponse.setBirthday(DateUtil.date2Str(tbCustDetailInfo.getBirthday(), DateUtil.yyyyMMddHHmmss));
			getUserResponse.setGender(tbCustDetailInfo.getGender());
			getUserResponse.setNation(tbCustDetailInfo.getNation());
			getUserResponse.setDrive_model(tbCustDetailInfo.getDriveModel());
			
			if(tbCustDetailInfo.getAnnualIncome() != null) {
				getUserResponse.setAnnual_income(String.valueOf(tbCustDetailInfo.getAnnualIncome()));
			}
			getUserResponse.setRel_flag(tbCustDetailInfo.getRelFlag());
			getUserResponse.setIncome_from(tbCustDetailInfo.getIncomeFrom());
			getUserResponse.setIncome_status(tbCustDetailInfo.getIncomeStatus());
			getUserResponse.setEntry_year(tbCustDetailInfo.getEntryYear());
			getUserResponse.setLive_status(tbCustDetailInfo.getLiveStatus());
			getUserResponse.setWork_unit(tbCustDetailInfo.getWorkUnit());
			getUserResponse.setPosition(tbCustDetailInfo.getPosition());
			getUserResponse.setEdu_level(tbCustDetailInfo.getEduLevel());
			getUserResponse.setMarr_status(tbCustDetailInfo.getMarrStatus());
			getUserResponse.setSpouse_name(tbCustDetailInfo.getSpouseName());
			getUserResponse.setSpouse_cert_type(tbCustDetailInfo.getSpouseCertType());
			getUserResponse.setSpouse_cert_code(tbCustDetailInfo.getSpouseCertCode());
			getUserResponse.setSpouse_phone(tbCustDetailInfo.getSpousePhone());
			getUserResponse.setSpouse_income_from(tbCustDetailInfo.getSpouseIncomeFrom());
			
			if(tbCustDetailInfo.getSpouseAnnualIncome() != null) {
				getUserResponse.setSpouse_annual_income(String.valueOf(tbCustDetailInfo.getSpouseAnnualIncome()));
			}
			getUserResponse.setSpouse_work_unit(tbCustDetailInfo.getSpouseWorkUnit());
			getUserResponse.setSpouse_contact_addr(tbCustDetailInfo.getSpouseContactAddr());
			getUserResponse.setIndustry(tbCustDetailInfo.getIndustry());
			getUserResponse.setMax_quota(tbCustDetailInfo.getMaxQuota());
			getUserResponse.setCompany_nature(tbCustDetailInfo.getCompanyNature());
			
			if(tbCustDetailInfo.getWorkYear() != null) {
				getUserResponse.setWork_year(String.valueOf(tbCustDetailInfo.getWorkYear()));
			}
			getUserResponse.setUnit_tel(tbCustDetailInfo.getUnitTel());
			getUserResponse.setEmail(tbCustDetailInfo.getEmail());
			getUserResponse.setCert_org(tbCustDetailInfo.getCertOrg());
			getUserResponse.setRegular_deposit_amt(tbCustDetailInfo.getRegularDepositAmt());
			getUserResponse.setZip_code(tbCustDetailInfo.getZipCode());
			getUserResponse.setCert_addr(tbCustDetailInfo.getCertAddr());
			getUserResponse.setFamily_tel(tbCustDetailInfo.getFamilyTel());
			getUserResponse.setContact_addr(tbCustDetailInfo.getContactAddr());
		}
		
		// 联系人
	    List<TbPersonalContactRelation> tbContactRelations = tbCustInfo.getTbPersonalContactRelationList();
	    if(tbContactRelations != null && !tbContactRelations.isEmpty()) {
		    List<Contact> contacts = new ArrayList<Contact>();
			for(TbPersonalContactRelation tbContactRelation : tbContactRelations) {
				Contact contact = new Contact();
				contact.setContact_name(tbContactRelation.getContactName());
				contact.setIs_important_contact(tbContactRelation.getIsImportantContact());
				contact.setContact_cert_type(tbContactRelation.getContactCertType());
				contact.setContact_cert_code(tbContactRelation.getContactCertCode());
				contact.setRelation(tbContactRelation.getRelation());
				contact.setContact_work_unit(tbContactRelation.getContactWorkUnit());
				contact.setContact_email(tbContactRelation.getContactEmail());
				contact.setContact_fax(tbContactRelation.getContactFax());
				contact.setContact_phone(tbContactRelation.getContactPhone());
				contact.setContact_addr(tbContactRelation.getContactAddr());
				contact.setIs_send_sms(tbContactRelation.getIsSendSms());
				
				contacts.add(contact);
			}
			getUserResponse.setContacts(contacts);
	    }
		
		// 账户
		List<TbPersonalAccountInfo> tbAccounts = tbCustInfo.getTbPersonalAccountInfoList();
		if(tbAccounts!=null && !tbAccounts.isEmpty()) {
			List<Account> accounts = new ArrayList<Account>();
			for(TbPersonalAccountInfo tbAccount : tbAccounts) {
				Account account = new Account();
				account.setAcc_name(tbAccount.getAccName());
				account.setAcc_no(tbAccount.getAccNo());
				account.setBank_code(tbAccount.getBankCode());
				account.setBank_full_name(tbAccount.getBankFullName());
				account.setBranch_bank_name(tbAccount.getBranchBankName());
				account.setCurrency(tbAccount.getCurrency());
				account.setWithhold_unit(tbAccount.getWithholdUnit());
				account.setIs_withhold_acc(tbAccount.getIsWithholdAcc());
				account.setAcc_status(tbAccount.getAccStatus());
				
				accounts.add(account);
			}
			getUserResponse.setAccounts(accounts);
		}
	}


	/**
	 * 生成成功响应报文
	 * @param getUserResponse
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private String generateSuccessfulResponseXML(ResponseGetUserInfo resp) throws IllegalArgumentException, IllegalAccessException {
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
		
		List<Contact> contacts = resp.getContacts();
		if(null == contacts || contacts.isEmpty()) {
			msgResponse.append("<contacts/>");
		} else {
			msgResponse.append("<contacts>");
			for(Contact contact : contacts) {
				msgResponse.append("<contact>").append(XMLHelper.getXMLFromBean(contact)).append("</contact>");
			}
			msgResponse.append("</contacts>");
		}
		
		List<Account> accounts = resp.getAccounts();
		if(null == accounts || accounts.isEmpty()) {
			msgResponse.append("<accounts/>");
		} else {
			msgResponse.append("<accounts>");
			for(Account account : accounts) {
				msgResponse.append("<account>").append(XMLHelper.getXMLFromBean(account)).append("</account>");
			}
			msgResponse.append("</accounts>");
		}
		msgResponse.append("</body></crm>");
		
		logger.info("CRM接口GET_USER_INFO响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	
	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(ResponseGetUserInfo resp) {
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
		
		logger.info("CRM接口GET_USER_INFO响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}


	/**
	 * 参数校验
	 * @param getUserRequest
	 * @param param
	 * @throws InputParamException
	 */
	private void verification(RequestGetUserInfo getUserRequest, TbSecurityKey param) throws InputParamException {
		// 访问权限校验
		if(!getUserRequest.getSecurity_code().equals(param.getSysName()) || !getUserRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", getUserRequest.getReq_serial_no(), getUserRequest.getReq_date());
		}
		
		// 签名校验
		String signFromAPIResponse = getUserRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", getUserRequest.getReq_serial_no(), getUserRequest.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		getUserRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(getUserRequest, param.getSignKey());
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", getUserRequest.getReq_serial_no(), getUserRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", getUserRequest.getReq_serial_no(), getUserRequest.getReq_date());
		}
		
		// 参数校验
		if(StringUtils.isBlank(getUserRequest.getCust_id())) {
			throw new InputParamException("客户ID【cust_id】不能为空！", getUserRequest.getReq_serial_no(), getUserRequest.getReq_date());
		}
	}


	@Override
	public String generateGetUserInfoErrorXML(String code, String message) {
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
	
		logger.info("CRM接口GET_USER_INFO响应出錯报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
}
