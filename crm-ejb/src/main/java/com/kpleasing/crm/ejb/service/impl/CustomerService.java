package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.eao.local.RCustAttachmentEaoLocal;
import com.kpleasing.crm.ejb.eao.local.TbAttachmentInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbAuditingPlanEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalAccountInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalContactRelationEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerDetailInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbReserverCustomerEAOLocal;
import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;
import com.kpleasing.crm.ejb.entity.TbAuditingPlan;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbReserverCustomer;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.service.local.CustomerServiceLocal;
import com.kpleasing.crm.ejb.service.local.SystemServiceLocal;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.util.Security;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.ejb.util.XMLHelper;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncAccountInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncAttachmentInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncContactInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncCustomerInfo;


@Stateless
@LocalBean
public class CustomerService implements Serializable,CustomerServiceLocal  {
	
	private static final long serialVersionUID = 6796950583252330362L;
	
	private static Logger logger = Logger.getLogger(CustomerService.class);
	
	@EJB
	private TbPersonalCustomerInfoEAOLocal customerInfoEao;
	@EJB
	private TbPersonalCustomerDetailInfoEAOLocal customerDetailEao;
	@EJB
	private TbPersonalContactRelationEAOLocal contactRelationEao;
	@EJB
	private TbPersonalAccountInfoEAOLocal	accountInfoEao;
	@EJB
	private ConfigServiceLocal	configServic;
	@EJB
	private SystemServiceLocal systemServic;
	@EJB
	private TbAttachmentInfoEAOLocal attachmentInfoEao;
	@EJB
	private RCustAttachmentEaoLocal rCustAttachEao;
	@EJB
	private TbAuditingPlanEAOLocal auditingPlanEAO;
	@EJB
	private TbReserverCustomerEAOLocal reserverCustomerEao;
	
	@Override
	public Map<String,Object> getCustAndDetailInfoMap(TbPersonalCustomerInfo customerInfo){
		TbPersonalCustomerInfo custInfo = customerInfoEao.findById(customerInfo.getCustId());
		Map<String, Object> custMap = customerInfoEao.ConvertObjToMap(custInfo);
		List<TbPersonalCustomerDetailInfo> list = customerDetailEao.findByProperty("custId", customerInfo.getCustId());
		if(list!=null && list.size()==1){
			Map<String, Object> detailMap = null;
			for(TbPersonalCustomerDetailInfo tmp : list){
				detailMap = customerDetailEao.ConvertObjToMap(tmp);
				break;
			}
			custMap.putAll(detailMap);
		}
		Map<String, Object> attachMap = attachmentInfoEao.getCustAttachmentInfoMap(customerInfo.getCustId());
		if(attachMap != null){
			custMap.putAll(attachMap);
		}
		return custMap;
	}
	
	@Override
	public Map<String, Object> getAttachmentInfoMap(int custId){
		Map<String, Object> attachMap = attachmentInfoEao.getCustAttachmentInfoMap(custId);
		return attachMap;
	}
	
	@Override
	public List<TbPersonalContactRelation> getCustomerContactList(TbPersonalContactRelation contactInfo){
		return contactRelationEao.findByProperty("custId", contactInfo.getCustId(), contactInfo.getPage(), contactInfo.getRows());
	
	}
	
	@Override
	public int getCustomerContactCount(TbPersonalContactRelation contactInfo){
		String[] fields = {"custId"};
		Object[] values = {contactInfo.getCustId()};
		return contactRelationEao.countByPropertys(fields, values);
		
	}
	
	@Override
	public void saveContactRelation(TbPersonalContactRelation contactRelation){
		if(contactRelation.getId() == 0){
			contactRelationEao.save(contactRelation);
		}else{
			contactRelationEao.update(contactRelation);
		}
		
	}
	
	@Override
	public void deleteContactRelation(TbPersonalContactRelation contactRelation){
		contactRelationEao.delete(contactRelation.getId());
		
	}
	
	@Override
	public List<TbPersonalAccountInfo> getAccountInfoList(TbPersonalAccountInfo accountInfo){
		
		return accountInfoEao.findByProperty("custId", accountInfo.getCustId(), accountInfo.getPage(), accountInfo.getRows());
	}
	
	@Override
	public int getAccountInfoCount(TbPersonalAccountInfo accountInfo) {
		String[] fields = {"custId"};
		Object[] values = {accountInfo.getCustId()};
		return accountInfoEao.countByPropertys(fields, values);
	}
	
	@Override
	public void saveAccountInfo(TbPersonalAccountInfo accountInfo){
		if(accountInfo.getId() == 0){
			accountInfoEao.save(accountInfo);
		}else{
			accountInfoEao.update(accountInfo);
		}
	}
	
	@Override
	public void deleteAccountInfo(TbPersonalAccountInfo accountInfo){
		accountInfoEao.delete(accountInfo.getId());
		
	}
	
	@Override
	public List<TbPersonalCustomerInfo> getCustInfoListByPorperty(TbPersonalCustomerInfo customerInfo){
		
		return customerInfoEao.findCustomerListByProperty(customerInfo);
	}
	
	@Override
	public int getCustInfoListCount(TbPersonalCustomerInfo customerInfo) {
		
		return customerInfoEao.findCustomerListByCount(customerInfo);
	}
	
	@Override
	public void saveCustInfoAndDetail(TbPersonalCustomerInfo customerInfo,TbPersonalCustomerDetailInfo customerDetail) {
	
		customerInfo.setCreateAt(DateUtil.getDate());
		customerInfo.setUpdateAt(DateUtil.getDate());
		if(customerInfo.getCustStatus()==null ||"".equals(customerInfo.getCustStatus())){
			customerInfo.setCustStatus("Y");
		}
		customerInfoEao.save(customerInfo);
		
		customerDetail.setCustId(customerInfo.getCustId());
		customerDetailEao.save(customerDetail);
		
	}
	
	@Override
	public void updateCustInfoAndDetail(TbPersonalCustomerInfo customerInfo,TbPersonalCustomerDetailInfo customerDetail) {
		logger.info("开始保存客户信息及详细信息...");
		TbPersonalCustomerInfo custInfoOrg = customerInfoEao.findById(customerInfo.getCustId());
		custInfoOrg.setCustName(customerInfo.getCustName());
		custInfoOrg.setCustType(customerInfo.getCustType());
		custInfoOrg.setCertCode(customerInfo.getCertCode());
		custInfoOrg.setChannel(customerInfo.getChannel());
		custInfoOrg.setMemo(customerInfo.getMemo());
		if(customerInfo.getCustStatus()==null ||"".equals(customerInfo.getCustStatus())){
			custInfoOrg.setCustStatus("Y");
		}else{
			custInfoOrg.setCustStatus(customerInfo.getCustStatus());
		}
		custInfoOrg.setPhone(customerInfo.getPhone());
		custInfoOrg.setCustMemo(customerInfo.getCustMemo());
		custInfoOrg.setUpdateAt(DateUtil.getDate());
		customerInfoEao.update(custInfoOrg);
		
		/*customerInfo.setCreateAt(custInfoOrg.getCreateAt());
		customerInfo.setWxOpenId(custInfoOrg.getWxOpenId());
		customerInfo.setUpdateAt(DateUtil.getDate());
		if(customerInfo.getCustStatus()==null ||"".equals(customerInfo.getCustStatus())){
			customerInfo.setCustStatus("Y");
		}
		customerInfoEao.update(customerInfo);*/
		
		logger.info("根据客户ID" + customerInfo.getCustId() + "更新TbPersonalCustomerDetailInfo...");
		TbPersonalCustomerDetailInfo detailInfo = customerDetailEao.findCustDetailByCustId(customerInfo.getCustId());
		if(detailInfo == null){
			customerDetail.setCustId(customerInfo.getCustId());
			customerDetailEao.save(customerDetail);
		}else{
			detailInfo.setCustId(customerInfo.getCustId());
			detailInfo.setBirthday(customerDetail.getBirthday());
			detailInfo.setCustNameSpell(customerDetail.getCustNameSpell());
			detailInfo.setGender(customerDetail.getGender());
			detailInfo.setNation(customerDetail.getNation());
			detailInfo.setDriveModel(customerDetail.getDriveModel());
			detailInfo.setAnnualIncome(customerDetail.getAnnualIncome());
			detailInfo.setRelFlag(customerDetail.getRelFlag());
			detailInfo.setIncomeFrom(customerDetail.getIncomeFrom());
			detailInfo.setIncomeStatus(customerDetail.getIncomeStatus());
			detailInfo.setEntryYear(customerDetail.getEntryYear());
			detailInfo.setLiveStatus(customerDetail.getLiveStatus());
			detailInfo.setWorkUnit(customerDetail.getWorkUnit());
			detailInfo.setPosition(customerDetail.getPosition());
			detailInfo.setEduLevel(customerDetail.getEduLevel());
			detailInfo.setMarrStatus(customerDetail.getMarrStatus());
			detailInfo.setSpouseName(customerDetail.getSpouseName());
			detailInfo.setSpouseCertType(customerDetail.getSpouseCertType());
			detailInfo.setSpouseCertCode(customerDetail.getSpouseCertCode());
			detailInfo.setSpousePhone(customerDetail.getSpousePhone());
			detailInfo.setSpouseAnnualIncome(customerDetail.getAnnualIncome());
			detailInfo.setSpouseIncomeFrom(customerDetail.getSpouseIncomeFrom());
			detailInfo.setSpouseContactAddr(customerDetail.getSpouseContactAddr());
			detailInfo.setSpouseWorkUnit(customerDetail.getSpouseWorkUnit());
			detailInfo.setIndustry(customerDetail.getIndustry());
			detailInfo.setMaxQuota(customerDetail.getMaxQuota());
			detailInfo.setCompanyNature(customerDetail.getCompanyNature());
			detailInfo.setWorkYear(customerDetail.getWorkYear());
			detailInfo.setUnitTel(customerDetail.getUnitTel());
			detailInfo.setEmail(customerDetail.getEmail());
			detailInfo.setCertOrg(customerDetail.getCertOrg());
			detailInfo.setRegularDepositAmt(customerDetail.getRegularDepositAmt());
			detailInfo.setZipCode(customerDetail.getZipCode());
			detailInfo.setCertAddr(customerDetail.getCertAddr());
			detailInfo.setFamilyTel(customerDetail.getFamilyTel());
			detailInfo.setContactAddr(customerDetail.getContactAddr());
			detailInfo.setWorkAddr(customerDetail.getWorkAddr());
			customerDetailEao.update(detailInfo);
			
			/*customerDetail.setId(detailInfo.getId());
			customerDetailEao.update(customerDetail);*/
		}
		logger.info("保存成功！");
	}
	
	/**
	 * 封装请求实体Bean
	 * @param customerInfo
	 * @return
	 */
	public RequestSyncCustomerInfo getCustInfoRequestBean(int custId ,String wxFlag){
		RequestSyncCustomerInfo sui = new RequestSyncCustomerInfo();
		Configurate config = configServic.getConfig();
		TbPersonalCustomerInfo custInfo = customerInfoEao.findById(custId);
		TbPersonalCustomerDetailInfo detailInfo = customerDetailEao.findCustDetailByCustId(custId); 
		
		sui.setApi_code("LEASING002");
		sui.setReq_serial_no(StringUtil.getSerialNo32());
		sui.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		sui.setSecurity_code(config.ESB_SEC_CODE);
		sui.setSecurity_value(config.ESB_SEC_VALUE);
		sui.setCust_id(custInfo.getCustId()+"");
		sui.setCust_name(custInfo.getCustName()!=null?custInfo.getCustName().trim():"");
		sui.setCert_type(custInfo.getCertType());
		sui.setCert_code(custInfo.getCertCode()!=null?custInfo.getCertCode().trim():"");
		sui.setPhone(custInfo.getPhone()!=null?custInfo.getPhone().trim():"");
		sui.setWxFlag(wxFlag);
		sui.setCust_status(custInfo.getCustStatus());
		
		if(null != detailInfo) {
			sui.setCust_name_spell(detailInfo.getCustNameSpell());
			sui.setBirthday(DateUtil.date2Str(detailInfo.getBirthday(), DateUtil.yyyy_MM_dd));
			sui.setGender(detailInfo.getGender());
			sui.setNation(detailInfo.getNation()!=null?detailInfo.getNation().trim():"");
			sui.setDrive_model(detailInfo.getDriveModel());
			if(detailInfo.getAnnualIncome() != null){
				sui.setAnnual_income(detailInfo.getAnnualIncome().toString());
			}
			sui.setRel_flag(detailInfo.getRelFlag());
			sui.setIncome_from(detailInfo.getIncomeFrom()!=null?detailInfo.getIncomeFrom().trim():"");
			sui.setIncome_status(detailInfo.getIncomeStatus());
			sui.setEntry_year(detailInfo.getEntryYear()!=null?detailInfo.getEntryYear().trim():"");
			sui.setLive_status(detailInfo.getLiveStatus());
			sui.setWork_unit(detailInfo.getWorkUnit()!=null?detailInfo.getWorkUnit().trim():"");
			sui.setPosition(detailInfo.getPosition()!=null?detailInfo.getPosition().trim():"");
			sui.setEdu_level(detailInfo.getEduLevel());
			sui.setMarr_status(detailInfo.getMarrStatus());
			sui.setSpouse_name(detailInfo.getSpouseName()!=null?detailInfo.getSpouseName().trim():"");
			sui.setSpouse_cert_type(detailInfo.getSpouseCertType());
			sui.setSpouse_cert_code(detailInfo.getSpouseCertCode()!=null?detailInfo.getSpouseCertCode().trim():"");
			sui.setSpouse_phone(detailInfo.getSpousePhone()!=null?detailInfo.getSpousePhone().trim():"");
			sui.setSpouse_income_from(detailInfo.getSpouseIncomeFrom());
			if(detailInfo.getSpouseAnnualIncome() != null){
				sui.setSpouse_annual_income(detailInfo.getSpouseAnnualIncome().toString());
			}
			sui.setSpouse_work_unit(detailInfo.getSpouseWorkUnit()!=null?detailInfo.getSpouseWorkUnit().trim():"");
			sui.setSpouse_contact_addr(detailInfo.getSpouseContactAddr()!=null?detailInfo.getSpouseContactAddr().trim():"");
			sui.setIndustry(detailInfo.getIndustry());
			sui.setMax_quota(detailInfo.getMaxQuota());
			if(detailInfo.getWorkYear() != null){
				sui.setWork_year(detailInfo.getWorkYear().toString());
			}
			sui.setUnit_tel(detailInfo.getUnitTel()!=null?detailInfo.getUnitTel().trim():"");
			sui.setEmail(detailInfo.getEmail()!=null?detailInfo.getEmail().trim():"");
			sui.setCert_org(detailInfo.getCertOrg()!=null?detailInfo.getCertOrg().trim():"");
			sui.setRegular_deposit_amt(detailInfo.getRegularDepositAmt()!=null?detailInfo.getRegularDepositAmt().trim():"");
			sui.setZip_code(detailInfo.getZipCode()!=null?detailInfo.getZipCode().trim():"");
			sui.setCert_addr(detailInfo.getCertAddr()!=null?detailInfo.getCertAddr().trim():"");
			sui.setFamily_tel(detailInfo.getFamilyTel()!=null?detailInfo.getFamilyTel().trim():"");
			sui.setContact_address(detailInfo.getContactAddr()!=null?detailInfo.getContactAddr().trim():"");
			sui.setWork_addr(detailInfo.getWorkAddr()!=null?detailInfo.getWorkAddr().trim():"");
			sui.setCust_type(custInfo.getCustType());
			sui.setCust_memo(custInfo.getCustMemo()!=null?custInfo.getCustMemo().trim():"");
			sui.setCust_status(custInfo.getCustStatus());
			sui.setMemo(custInfo.getMemo()!=null?custInfo.getMemo().trim():"");
		}
		
		logger.info("根据客户ID"+custId+"查找联系人信息...");
		List<TbPersonalContactRelation> contactList = contactRelationEao.findByProperty("custId", custId);
		if(contactList != null && contactList.size() > 0){
			List<RequestSyncContactInfo> contacts = new ArrayList<RequestSyncContactInfo>();
			for(TbPersonalContactRelation cr:contactList){
				RequestSyncContactInfo sci = new RequestSyncContactInfo();
				sci.setCid(cr.getId()+"");
				sci.setContact_name(cr.getContactName()!=null?cr.getContactName().trim():"");
				sci.setIs_important_contact(cr.getIsImportantContact());
				sci.setContact_cert_type(cr.getContactCertType());
				sci.setContact_cert_code(cr.getContactCertCode()!=null?cr.getContactCertCode().trim():"");
				sci.setRelation(cr.getRelation());
				sci.setContact_work_unit(cr.getContactWorkUnit()!=null?cr.getContactWorkUnit().trim():"");
				sci.setContact_email(cr.getContactEmail()!=null?cr.getContactEmail().trim():"");
				sci.setContact_fax(cr.getContactFax()!=null?cr.getContactFax().trim():"");
				sci.setContact_phone(cr.getContactPhone()!=null?cr.getContactPhone().trim():"");
				sci.setContact_addr(cr.getContactAddr()!=null?cr.getContactAddr().trim():"");
				sci.setIs_send_sms(cr.getIsSendSms());
				contacts.add(sci);
			}
			sui.setContacts(contacts);
		}
		
		logger.info("根据客户ID"+custId+"查找其账户信息....");
		List<TbPersonalAccountInfo> accountList = accountInfoEao.findByProperty("custId", custId);
		if(accountList != null && accountList.size() > 0){
			List<RequestSyncAccountInfo> accounts = new ArrayList<RequestSyncAccountInfo>();
			for(TbPersonalAccountInfo ai:accountList){
				RequestSyncAccountInfo sai = new RequestSyncAccountInfo();
				sai.setAid(ai.getId()+"");
				sai.setAcc_name(ai.getAccName()!=null?ai.getAccName().trim():"");
				sai.setAcc_no(ai.getAccNo()!=null?ai.getAccNo().trim():"");
				sai.setBank_code(ai.getBankCode());
				sai.setBank_full_name(ai.getBankFullName()!=null?ai.getBankFullName().trim():"");
				sai.setBranch_bank_name(ai.getBranchBankName()!=null?ai.getBranchBankName().trim():"");
				sai.setBank_phone(ai.getBankPhone()!=null?ai.getBankPhone().trim():"");
				sai.setAbbreviation_card_no(ai.getAbbreviationCardNo()!=null?ai.getAbbreviationCardNo():"");
				sai.setIs_yjzf_bind(ai.getIsYjzfBind()+"");
				sai.setCurrency(ai.getCurrency());
				sai.setWithhold_unit(ai.getWithholdUnit());
				sai.setIs_withhold_acc(ai.getIsWithholdAcc());
				sai.setAcc_status(ai.getAccStatus());
				accounts.add(sai);
			}
			sui.setAccounts(accounts);
		}	
		
		logger.info("根据客户ID"+custId+"查找其附件信息....");
		List<TbAttachmentInfo> attachList = attachmentInfoEao.findAttachInfoByCustId(custId);
		if(attachList != null && attachList.size() > 0){
			List<RequestSyncAttachmentInfo> rsai_list = new ArrayList<RequestSyncAttachmentInfo>();
			for(TbAttachmentInfo tai : attachList){
				RequestSyncAttachmentInfo rsai = new RequestSyncAttachmentInfo();
				String attach_type = systemServic.getParamValueByCode(239, tai.getAttachmentType());
				rsai.setAttach_type(attach_type);
				rsai.setAttach_name(tai.getFileName());
				rsai.setFile_type_code(tai.getFileTypeCode());
				rsai.setAttach_url(tai.getRemotePath());
				rsai_list.add(rsai);
			}
			sui.setAttachs(rsai_list);
		}
		
		try {
			String sign = Security.getSign(sui, config.ESB_SIGN_KEY);
			sui.setSign(sign);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sui;
	}
	
	@Override
	public String structCustRequestParam(int custId,String wxFlag) throws IllegalArgumentException, IllegalAccessException{
		RequestSyncCustomerInfo suiReq = getCustInfoRequestBean(custId,wxFlag);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
		.append("<esb>")
		.append("<head>")
		.append("<api_code><![CDATA["+suiReq.getApi_code()+"]]></api_code>")
		.append("<req_serial_no><![CDATA["+ suiReq.getReq_serial_no() +"]]></req_serial_no>")
		.append("<req_date><![CDATA["+ suiReq.getReq_date() +"]]></req_date>")
		.append("<security_code><![CDATA["+ suiReq.getSecurity_code() +"]]></security_code>")
		.append("<security_value><![CDATA["+ suiReq.getSecurity_value() +"]]></security_value>")
		.append("<sign><![CDATA["+ suiReq.getSign() +"]]></sign>")
		.append("</head>")
		.append("<body>");
		sb.append(XMLHelper.getXMLFromBean(suiReq));
		
		List<RequestSyncContactInfo> contactList = suiReq.getContacts();
		if(contactList != null && contactList.size() > 0){
			sb.append("<contacts>");
			for(RequestSyncContactInfo cr :contactList){
				sb.append("<contact>");
				sb.append(XMLHelper.getXMLFromBean(cr));
				sb.append("</contact>");
			}
			sb.append("</contacts>");
		}
		
		List<RequestSyncAccountInfo> accountList = suiReq.getAccounts();
		if(accountList != null && accountList.size() > 0){
			sb.append("<accounts>");
			for(RequestSyncAccountInfo ai : accountList){
				sb.append("<account>");
				sb.append(XMLHelper.getXMLFromBean(ai));
				sb.append("</account>");
			}
			sb.append("</accounts>");
		}
		
		List<RequestSyncAttachmentInfo> attachList = suiReq.getAttachs();
		if(attachList != null && attachList.size()>0){
			sb.append("<attachs>");
			for(RequestSyncAttachmentInfo at : attachList){
				sb.append("<attach>");
				sb.append(XMLHelper.getXMLFromBean(at));
				sb.append("</attach>");
			}
			sb.append("</attachs>");
		}
		
		sb.append("</body>");
		sb.append("</esb>");
		
		System.out.println("请求报文："+sb.toString());
		return sb.toString();
	}

	@Override
	public int getCustCountByPhone(String phone) {
		int count = 0;
		List<TbPersonalCustomerInfo> list = customerInfoEao.findByProperty("phone", phone);
		if(list != null){
			count = list.size();
		}
		return count;
	}
	
	@Override
	public int getCustCountByCertCode(String certCode) {
		int count = 0;
		List<TbPersonalCustomerInfo> list = customerInfoEao.findByProperty("certCode", certCode);
		if(list != null){
			count = list.size();
		}
		return count;
	}

	@Override
	public void saveExcelImportCustInfo(List<String[]> list) throws CRMException {
		for(int i=0;i<list.size();i++){
			String[] str = (String[])list.get(i); 
			if(i == 0){		//跳过标题
				continue;
			}
			if("".equals(str[0]) && "".equals(str[2]) && "".equals(str[3])){
				continue;
			}
			
			//身份证校验
			if(!"".equals(str[2])){
				int count = getCustCountByCertCode(str[2]);
				if(count>0){
					throw new CRMException("FAILED", "客户（"+str[0]+"）身份证号在系统中已存在！");
				}
			}else{
				throw new CRMException("FAILED", "客户（"+str[0]+"）身份证号不能为空！");
			}
			
			//手机号校验
			if(!"".equals(str[3]) && str[3].length()==11){
				int count = getCustCountByPhone(str[3]);
				if(count>0){
					throw new CRMException("FAILED", "客户（"+str[0]+"）手机号在系统中已存在！");
				}
			}else{
				throw new CRMException("FAILED", "客户（"+str[0]+"）手机号必须为11位！");
			}
			
			logger.info("保存客户基本及详细信息....");
			TbPersonalCustomerInfo customerInfo = new TbPersonalCustomerInfo();
			TbPersonalCustomerDetailInfo custDetailInfo = new TbPersonalCustomerDetailInfo();
			customerInfo.setCustName(str[0]);	//			姓名	
			
			String gerder_code = "";
			if("男".equals(str[1])){
				gerder_code = "MALE";
			}else if("女".equals(str[1])){
				gerder_code = "FEMALE";
			}else {
				gerder_code = "";
			}
			custDetailInfo.setGender(gerder_code);	//			性别	
			
			customerInfo.setCertType("ID_CARD");
			customerInfo.setCertCode(str[2]);	//			身份证号
			customerInfo.setPhone(str[3]);		//			手机号码	
			custDetailInfo.setNation(str[4]);	//			民族	
			custDetailInfo.setCertOrg(str[5]);	//			发证机关
			//			str[6]证件有效期从	
			//			str[7]证件有效期到	
			custDetailInfo.setCertAddr(str[8]);		//			身份证地址	
			custDetailInfo.setContactAddr(str[9]);	//			常住地址	
			
			String marr_code = "";					
			if("未婚".equals(str[10])){
				marr_code = "UNMARRIED";
			} else if("已婚".equals(str[10])){
				marr_code = "MARRIED";
			} else if("离异".equals(str[10])){
				marr_code = "DIVORCED";
			} else if("丧偶".equals(str[10])){
				marr_code = "WIDOWED";
			}else {
				marr_code = "";
			}
			custDetailInfo.setMarrStatus(marr_code);	//			婚姻状况	
			custDetailInfo.setSpouseName(str[11]);	//			配偶姓名	
			custDetailInfo.setSpouseCertType("ID_CARD");
			custDetailInfo.setSpouseCertCode(str[12]);	//			配偶身份证号	
			custDetailInfo.setSpousePhone(str[13]);		//			配偶手机号	
			custDetailInfo.setWorkUnit(str[14]);		//			工作单位	
			custDetailInfo.setWorkAddr(str[15]);		//			工作单位地址	
			custDetailInfo.setUnitTel(str[16]);		//			单位联系电话	
			if(!"".equals(str[17])){
				double d1 = Double.parseDouble(str[17]);
				custDetailInfo.setAnnualIncome(new Double(d1*12));	//		str[17]月薪资收入	
			}
			custDetailInfo.setDriveModel(str[18]);	//			准驾车型	
			custDetailInfo.setEmail(str[19]);		//			邮箱	
			
			customerInfo.setChannel("2");
			customerInfo.setCustStatus("Y");
			customerInfo.setCreateAt(DateUtil.getDate());
			customerInfo.setUpdateAt(DateUtil.getDate());
			customerInfoEao.save(customerInfo);
			custDetailInfo.setCustId(customerInfo.getCustId());
			customerDetailEao.save(custDetailInfo);
			
			logger.info("保存紧急联系人信息....");
			TbPersonalContactRelation contactInfo = new TbPersonalContactRelation();
			contactInfo.setContactName(str[20]);	//			紧急联系人
			
			String relation_code = "";
			if("父母".equals(str[21])){
				relation_code = "1";
			}else if("配偶".equals(str[21])){
				relation_code = "2";
			}else if("子女".equals(str[21])){
				relation_code = "3";
			}else{
				relation_code = "";
			}
			contactInfo.setRelation(relation_code);//			关系（父母/配偶/子女）
			
			contactInfo.setContactPhone(str[22]);//			联系人手机
			contactInfo.setIsImportantContact("Y");
			contactInfo.setCustId(customerInfo.getCustId());
			contactRelationEao.save(contactInfo);
			
			logger.info("客户（"+str[0]+"）信息保存成功！");
		}
	}

	@Override
	public void saveOrUpdateCustAttachment(TbAttachmentInfo tbAttachmentInfo, RCustAttachment rCustAttachment) {
		logger.info("保存附件信息....");
		tbAttachmentInfo.setCreateAt(DateUtil.getDate());
		tbAttachmentInfo.setUpdateAt(DateUtil.getDate());
		attachmentInfoEao.save(tbAttachmentInfo);
		
		logger.info("根据id保存或更新附件关联表");
		rCustAttachment.setAttachmentId(tbAttachmentInfo.getAttachmentId());
		rCustAttachment.setCustFlag(0);
		rCustAttachment.setFlag(0);
		if(rCustAttachment.getId() == 0){
			rCustAttachEao.save(rCustAttachment);
		}else{
			rCustAttachEao.update(rCustAttachment);
		}
		logger.info("附件信息保存成功！");
	}

	@Override
	public void deleteRCustAttachmentById(int id) {
		RCustAttachment rCustAttachment = rCustAttachEao.findById(id);
		rCustAttachment.setFlag(1);
		rCustAttachEao.update(rCustAttachment);
	}

	@Override
	public TbAttachmentInfo findAttachEntityById(int id) {
		RCustAttachment rCustAttachment = rCustAttachEao.findById(id);
		TbAttachmentInfo tbAttachmentInfo = null;
		if(rCustAttachment !=null){
		  tbAttachmentInfo = attachmentInfoEao.findById(rCustAttachment.getAttachmentId());
		}
		return tbAttachmentInfo;
	}

	@Override
	public List<TbAuditingPlan> getAuditingPlanListByPorperty(TbAuditingPlan tbAuditingPlan) {
		
		return auditingPlanEAO.findAuditingPlanList(tbAuditingPlan);
	}

	@Override
	public int getAuditingPlanListCount(TbAuditingPlan tbAuditingPlan) {
		return auditingPlanEAO.findAuditingPlanListByCount(tbAuditingPlan);
	}

	@Override
	public TbPersonalCustomerInfo getCustInfoByID(int custId) {
		return customerInfoEao.findById(custId);
	}

	@Override
	public void saveVideoAuthInfo(TbAuditingPlan tbAuditingPlan) {
		logger.info("更新视频面签信息...");
		if(tbAuditingPlan !=null){
			TbAuditingPlan auditingPlan = auditingPlanEAO.findById(tbAuditingPlan.getId());
			auditingPlan.setAudiFlag(tbAuditingPlan.getAudiFlag());
			auditingPlan.setRemark(tbAuditingPlan.getRemark());
			auditingPlan.setUpdateAt(DateUtil.getDate());
			auditingPlanEAO.update(auditingPlan);
		}
	}

	@Override
	public List<TbReserverCustomer> getReserverListByPorperty(TbReserverCustomer tbReserverCustomer) {
		return reserverCustomerEao.findReserverList(tbReserverCustomer);
	}

	@Override
	public int getReserverListCount(TbReserverCustomer tbReserverCustomer) {
		return reserverCustomerEao.findReserverListByCount(tbReserverCustomer);
	}

	
}
