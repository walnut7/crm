package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.eao.local.RCustAttachmentEaoLocal;
import com.kpleasing.crm.ejb.eao.local.TbAttachmentInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbAuditingPlanEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalAccountInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalContactRelationEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerDetailInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbReserverCustomerEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbStaffInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;
import com.kpleasing.crm.ejb.entity.TbAuditingPlan;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbReserverCustomer;
import com.kpleasing.crm.ejb.entity.TbStaffInfo;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.service.local.APIServiceLocal;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.util.DateUtil;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestRegister;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestReserverCar;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestStaffLogin;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncAccountInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncContactInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncFaceVedioTime;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncTMCustomer;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncTMCustomer;

@Stateless
@LocalBean
public class APIService implements Serializable, APIServiceLocal {

	/** * */
	private static final long serialVersionUID = -2007688198815612595L;
	private static Logger logger = Logger.getLogger(APIService.class);

	@EJB
	private TbPersonalCustomerInfoEAOLocal customerEao;
	
	@EJB
	private TbPersonalCustomerDetailInfoEAOLocal custDetailEao;
	
	@EJB
	private TbPersonalContactRelationEAOLocal contactEao;
	
	@EJB
	private TbPersonalAccountInfoEAOLocal accountEao;
	
	@EJB
	private TbReserverCustomerEAOLocal reserverCustEao;
	
	@EJB
	private TbStaffInfoEAOLocal staffEao;
	
	@EJB
	private RCustAttachmentEaoLocal attachEao;
	
	@EJB
	private TbAttachmentInfoEAOLocal tbAttachEao;
	
	@EJB
	private ConfigServiceLocal configServ;
	
	@EJB
	private TbAuditingPlanEAOLocal tbAuditPlanEao;
	
	@EJB
	private TbPersonalAccountInfoEAOLocal bankAccountEao;

	@Override
	public int Register(RequestRegister req) throws CRMException {
		String phone = req.getPhone();
		String channelType = req.getChannel_type();
		
		logger.info("用户注册中.........");
		TbPersonalCustomerInfo tbPerson = null;
		if ("0".equals(channelType)) {
			tbPerson = customerEao.findCustomerByPhone(phone);
			if (null == tbPerson) {
				tbPerson = new TbPersonalCustomerInfo();
				tbPerson.setPhone(req.getPhone());
				tbPerson.setCustStatus("Y");
				tbPerson.setCreateAt(DateUtil.getDate());
				tbPerson.setUpdateAt(DateUtil.getDate());
			} else {
				throw new CRMException("FAILED", "用户已经存在！");
			}
		} else if ("1".equals(channelType)) {
			tbPerson = customerEao.findCustomerByPhone(phone);
			if (null == tbPerson) {
				tbPerson = new TbPersonalCustomerInfo();
				tbPerson.setPhone(req.getPhone());
				tbPerson.setWxOpenId(req.getChannel_id());
				tbPerson.setChannel("1");
				tbPerson.setCustStatus("Y");
				tbPerson.setCreateAt(DateUtil.getDate());
				tbPerson.setUpdateAt(DateUtil.getDate());
			} else if(StringUtils.isBlank(tbPerson.getWxOpenId())) {
				tbPerson.setWxOpenId(req.getChannel_id());
				tbPerson.setUpdateAt(DateUtil.getDate());
				customerEao.update(tbPerson);
				
				return tbPerson.getCustId();
			} else {
				throw new CRMException("FAILED", "用户已绑定！");
			}
		} else {
			throw new CRMException("FAILED", "渠道类型不存在！");
		}
		customerEao.save(tbPerson);
		
		return tbPerson.getCustId();
	}

	
	@Override
	public TbPersonalCustomerInfo getUserProfile(RequestGetUserProfile req) throws CRMException {
		String channelId = req.getChannel_id();
		String channelType = req.getChannel_type();
		
		TbPersonalCustomerInfo tbPerson = null;
		if ("0".equals(channelType)) {
			tbPerson = customerEao.findCustomerByPhone(channelId);
		} else if("1".equals(channelType)) {
			tbPerson = customerEao.findCustomerByOpenId(channelId);
		} else if("2".equals(channelType)) {
			tbPerson = customerEao.findCustomerByCustId(Integer.valueOf(channelId));
		} else {
			throw new CRMException("FAILED", "渠道类型参数不正确！");
		}
		
		if(null == tbPerson) {
			throw new CRMException("FAILED", "查无资料！");
		}
		
		return tbPerson;
	}

	
	@Override
	public void setUserProfile(RequestSetUserProfile req) {
		TbPersonalCustomerInfo tbPersonal = customerEao.findById(Integer.valueOf(req.getCust_id()));
		tbPersonal.setPhone(req.getPhone());
		tbPersonal.setUpdateAt(DateUtil.getDate());
		
		if(StringUtils.isNotBlank(req.getCust_name())) {
			tbPersonal.setCustName(req.getCust_name());
		}
		
		if(StringUtils.isNotBlank(req.getCert_type())) {
			tbPersonal.setCertType(req.getCert_type());
		}
		
		if(StringUtils.isNotBlank(req.getCert_code())) {
			tbPersonal.setCertCode(req.getCert_code());
		}
		tbPersonal.setCustStatus("Y");
		customerEao.save(tbPersonal);
	}


	@Override
	public TbStaffInfo getStaffLoginInfo(RequestStaffLogin req) throws CRMException {
		logger.info("登录账户：login_id="+req.getLogin_id()+"  password="+req.getPassword());
		TbStaffInfo tbStaff = staffEao.getStaffInfoByNameAndPWD(req.getLogin_id(), req.getPassword());
		if(null == tbStaff) {
			throw new CRMException("FAILED", "用户名或密码不正确！");
		}
		return tbStaff;
		
	}


	@Override
	public TbPersonalCustomerInfo getUserInfo(RequestGetUserInfo getUserRequest) throws CRMException {
		try {
			int custId = Integer.valueOf(getUserRequest.getCust_id());
			return customerEao.getUserInfo(custId);
		} catch(Exception e) {
			throw new CRMException("FAILED", "查找用户数据异常.");
		}
	}


	@Override
	public void saveCustInfo(RequestSetUserInfo custReq) throws CRMException {
		try {
			int custId = Integer.valueOf(custReq.getCust_id());
			TbPersonalCustomerInfo tbCustInfo = customerEao.getUserInfo(custId);
			if(null == tbCustInfo) {
				throw new CRMException("FAILED", "客户ID不存在！");
			} else {
				logger.info("保存基本信息......");
				tbCustInfo.setCertType(custReq.getCert_type());
				tbCustInfo.setCertCode(custReq.getCert_code());
				//tbCustInfo.setChannel("");
				tbCustInfo.setCreateAt(DateUtil.getDate());
				// tbCustInfo.setCustMemo("");
				tbCustInfo.setCustName(custReq.getCust_name());
				// tbCustInfo.setCustStatus("");
				//tbCustInfo.setCustType("");
				//tbCustInfo.setMemo("");
				tbCustInfo.setPhone(custReq.getPhone());
				customerEao.save(tbCustInfo);
				
				logger.info("保存详细信息......");
				TbPersonalCustomerDetailInfo tbDetailCustInfo = tbCustInfo.getTbPersonalCustomerDetailInfo();
				if(null != tbDetailCustInfo) {
					newTbCustInfoDetail(tbDetailCustInfo, custReq);
					custDetailEao.update(tbDetailCustInfo);
				} else {
					tbDetailCustInfo = new TbPersonalCustomerDetailInfo();
					newTbCustInfoDetail(tbDetailCustInfo, custReq);
					tbDetailCustInfo.setCustId(custId);
					custDetailEao.save(tbDetailCustInfo);
				}
				
				
				logger.info("更新联系人信息......");
				List<TbPersonalContactRelation> tbContacts = tbCustInfo.getTbPersonalContactRelationList();
				List<RequestSyncContactInfo> syncContacts = custReq.getContacts();
				if(tbContacts == null) {
					for(RequestSyncContactInfo syncContact : syncContacts) {
						logger.info("新增联系人信息【"+syncContact.getContact_cert_code()+"】");
						TbPersonalContactRelation tbContact = new TbPersonalContactRelation();
						newTbCustContact(tbContact, syncContact);
						tbContact.setCustId(custId);
						contactEao.save(tbContact);
					}
				} else {
					for(RequestSyncContactInfo syncContact : syncContacts) {
						boolean isExist = false;
						for(TbPersonalContactRelation tbContact : tbContacts) {
							if(null!=tbContact.getContactCertCode() && null!=syncContact.getContact_cert_code() 
									&& tbContact.getContactCertCode().equals(syncContact.getContact_cert_code())) {
								logger.info("更新联系人信息【"+syncContact.getContact_cert_code()+"】");
								isExist = true;
								newTbCustContact(tbContact, syncContact);
								contactEao.update(tbContact);
								break;
							}
						}
						if(!isExist) {
							logger.info("新增联系人信息【"+syncContact.getContact_cert_code()+"】");
							TbPersonalContactRelation tbContact = new TbPersonalContactRelation();
							newTbCustContact(tbContact, syncContact);
							tbContact.setCustId(custId);
							contactEao.save(tbContact);
						}
					}
				}
				
				
				logger.info("更新账户信息......");
				List<TbPersonalAccountInfo> tbAccounts = tbCustInfo.getTbPersonalAccountInfoList();
				List<RequestSyncAccountInfo> syncAccounts = custReq.getAccounts();
				if(tbAccounts==null) {
					for(RequestSyncAccountInfo syncAccount : syncAccounts) {
						logger.info("新增账户信息【"+syncAccount.getAcc_no()+"】");
						TbPersonalAccountInfo tbAccount = new TbPersonalAccountInfo();
						newTbCustAccount(tbAccount, syncAccount);
						tbAccount.setCustId(custId);
						accountEao.save(tbAccount);
					}
				} else {
					for(RequestSyncAccountInfo syncAccount : syncAccounts) {
						boolean isExist = false;
						for(TbPersonalAccountInfo tbAccount : tbAccounts) {
							tbAccount.setIsWithholdAcc("N");
							if(null!=tbAccount.getAccNo() && null!=syncAccount.getAcc_no() 
									&& tbAccount.getAccNo().equals(syncAccount.getAcc_no())) {
								logger.info("更新账户信息【"+syncAccount.getAcc_no()+"】");
								isExist = true;
								newTbCustAccount(tbAccount, syncAccount);
								accountEao.update(tbAccount);
							}
						}
						if(!isExist) {
							logger.info("新增账户信息【"+syncAccount.getAcc_no()+"】");
							TbPersonalAccountInfo tbAccount = new TbPersonalAccountInfo();
							newTbCustAccount(tbAccount, syncAccount);
							tbAccount.setCustId(custId);
							accountEao.save(tbAccount);
						}
					}
				}
				
				logger.info("更新证件信息......");
				String[][] reqAttacs = new String[][] {
					{custReq.getIdcard_front_img(),	custReq.getIdcard_back_img(), custReq.getDrivelicense_img(), custReq.getDrivelicense_back_img(), custReq.getBankcard_front_img(), custReq.getBankcard_back_img()},
					{"idCardP", "idCardB", "drivLicP", "drivLicB", "bankCardP", "bankCardB"},
					{"身份证正面照片", "身份证反面照片", "驾驶证正本照片", "驾驶证副本照片", "银行卡正面照片", "银行卡反面照片"},
					{custReq.getIf_file_type(),custReq.getIb_file_type(),custReq.getDf_file_type(),custReq.getDb_file_type(),custReq.getBf_file_type(),custReq.getBb_file_type()}};
				List<RCustAttachment> rAttachs = tbCustInfo.getRCustAttachmentList();
				if(rAttachs == null) {
					for (int i = 0; i < reqAttacs[0].length; i++) {
						if(StringUtils.isNotBlank(reqAttacs[0][i])) {
							logger.info("新增证件信息【"+reqAttacs[0][i]+"】");
							TbAttachmentInfo attach = new TbAttachmentInfo();
							attach.setRemotePath(reqAttacs[0][i]);
							attach.setAttachmentType(reqAttacs[1][i]);
							attach.setFileName(reqAttacs[2][i]);
							attach.setFileTypeCode(reqAttacs[3][i]);
							attach.setCreateAt(DateUtil.getDate());
							attach.setUpdateAt(DateUtil.getDate());
							tbAttachEao.save(attach);
							
							RCustAttachment rAttach = new RCustAttachment();
							rAttach.setCustFlag(0);
							rAttach.setCustId(custId);
							rAttach.setAttachmentId(attach.getAttachmentId());
							attachEao.save(rAttach);
						}
					}
				} else {
					for (int i = 0; i < reqAttacs[0].length; i++) {
						boolean isExist = false;
						for(RCustAttachment rAttach : rAttachs) {
							if(reqAttacs[1][i].equals(rAttach.getTbAttachmentInfo().getAttachmentType())) {
								logger.info("更新证件信息【"+reqAttacs[0][i]+"】");
								TbAttachmentInfo attach = new TbAttachmentInfo();
								attach.setRemotePath(reqAttacs[0][i]);
								attach.setAttachmentType(reqAttacs[1][i]);
								attach.setFileName(reqAttacs[2][i]);
								attach.setFileTypeCode(reqAttacs[3][i]);
								attach.setCreateAt(DateUtil.getDate());
								attach.setUpdateAt(DateUtil.getDate());
								tbAttachEao.save(attach);
								
								rAttach.setAttachmentId(attach.getAttachmentId());
								rAttach.setFlag(0);
								attachEao.update(rAttach);
								isExist = true;
							}
						}
						if(!isExist) {
							logger.info("新增证件信息【"+reqAttacs[0][i]+"】");
							TbAttachmentInfo attach = new TbAttachmentInfo();
							attach.setRemotePath(reqAttacs[0][i]);
							attach.setAttachmentType(reqAttacs[1][i]);
							attach.setFileName(reqAttacs[2][i]);
							attach.setFileTypeCode(reqAttacs[3][i]);
							attach.setCreateAt(DateUtil.getDate());
							attach.setUpdateAt(DateUtil.getDate());
							tbAttachEao.save(attach);
							
							RCustAttachment rAttach = new RCustAttachment();
							rAttach.setCustFlag(0);
							rAttach.setCustId(custId);
							rAttach.setAttachmentId(attach.getAttachmentId());
							attachEao.save(rAttach);
						}
					}
				}
			}
		} catch(Exception e) {
			throw new CRMException("FAILED", "数据处理异常.");
		}
	}
	
	
	/**
	 * 客户详细信息设置
	 * @param tbDetailCustInfo
	 * @param custReq
	 * @throws CRMException 
	 */
	private void newTbCustInfoDetail(TbPersonalCustomerDetailInfo tbDetailCustInfo, RequestSetUserInfo custReq) throws CRMException {
		try {
			if(!StringUtils.isBlank(custReq.getAnnual_income())) {
				tbDetailCustInfo.setAnnualIncome(Double.valueOf(custReq.getAnnual_income()));
			}
			if(!StringUtils.isBlank(custReq.getBirthday())) {
				tbDetailCustInfo.setBirthday(DateUtil.str2Date(custReq.getBirthday(), DateUtil.yyyyMMdd));
			}
			tbDetailCustInfo.setCertAddr(custReq.getCert_addr());
			tbDetailCustInfo.setCertOrg(custReq.getCert_org());
			// tbDetailCustInfo.setCompanyNature("");    //公司性质
			tbDetailCustInfo.setContactAddr(custReq.getContact_address());
			tbDetailCustInfo.setCustNameSpell(custReq.getCust_name_spell());
			tbDetailCustInfo.setDriveModel(custReq.getDrive_model());
			tbDetailCustInfo.setEduLevel(custReq.getEdu_level());
			tbDetailCustInfo.setEmail(custReq.getEmail());
			tbDetailCustInfo.setEntryYear(custReq.getEntry_year());
			tbDetailCustInfo.setFamilyTel(custReq.getFamily_tel());
			tbDetailCustInfo.setGender(custReq.getGender());
			tbDetailCustInfo.setIncomeFrom(custReq.getIncome_from());
			tbDetailCustInfo.setIncomeStatus(custReq.getIncome_status());
			tbDetailCustInfo.setIndustry(custReq.getIndustry());
			tbDetailCustInfo.setLiveStatus(custReq.getLive_status());
			tbDetailCustInfo.setMarrStatus(custReq.getMarr_status());
			tbDetailCustInfo.setMaxQuota(custReq.getMax_quota());
			tbDetailCustInfo.setNation(custReq.getNation());
			tbDetailCustInfo.setPosition(custReq.getPosition());
			tbDetailCustInfo.setRegularDepositAmt(custReq.getRegular_deposit_amt());
			tbDetailCustInfo.setRelFlag(custReq.getRel_flag());
			
			if(!StringUtils.isBlank(custReq.getSpouse_annual_income())) {
				tbDetailCustInfo.setSpouseAnnualIncome(Double.valueOf(custReq.getSpouse_annual_income()));
			}
			tbDetailCustInfo.setSpouseCertCode(custReq.getSpouse_cert_code());
			tbDetailCustInfo.setSpouseCertType(custReq.getSpouse_cert_type());
			tbDetailCustInfo.setSpouseContactAddr(custReq.getSpouse_contact_addr());
			tbDetailCustInfo.setSpouseIncomeFrom(custReq.getSpouse_income_from());
			tbDetailCustInfo.setSpouseName(custReq.getSpouse_name());
			tbDetailCustInfo.setSpousePhone(custReq.getSpouse_phone());
			tbDetailCustInfo.setSpouseWorkUnit(custReq.getSpouse_work_unit());
			tbDetailCustInfo.setUnitTel(custReq.getUnit_tel());
			tbDetailCustInfo.setWorkUnit(custReq.getWork_unit());
			if(!StringUtils.isBlank(custReq.getWork_year())) {
				tbDetailCustInfo.setWorkYear(Integer.valueOf(custReq.getWork_year()));
			}
			tbDetailCustInfo.setZipCode(custReq.getZip_code());
			tbDetailCustInfo.setWorkAddr(custReq.getWork_addr());
			
		} catch (Exception e) {
			throw new CRMException("FAILED", "客户详细信息处理失败！.");
		}
	}

	
	/**
	 * 客户账户信息设置
	 * @param tbAccount
	 * @param syncAccount
	 */
	private void newTbCustAccount(TbPersonalAccountInfo tbAccount, RequestSyncAccountInfo syncAccount) {
		tbAccount.setAccName(syncAccount.getAcc_name());
		tbAccount.setAccNo(syncAccount.getAcc_no());
		tbAccount.setAccStatus(syncAccount.getAcc_status());
		tbAccount.setBankCode(syncAccount.getBank_code());
		tbAccount.setBankFullName(syncAccount.getBank_full_name());
		tbAccount.setBranchBankName(syncAccount.getBranch_bank_name());
		tbAccount.setBankPhone(syncAccount.getBank_phone());
		tbAccount.setCurrency(syncAccount.getCurrency());
		tbAccount.setIsWithholdAcc(syncAccount.getIs_withhold_acc());
		tbAccount.setWithholdUnit(syncAccount.getWithhold_unit());
		tbAccount.setAccStatus("Y");
	}
	
	
	/**
	 * 联系人信息设置
	 * @param tbContact
	 * @param syncContact
	 */
	private void newTbCustContact(TbPersonalContactRelation tbContact, RequestSyncContactInfo syncContact) {
		tbContact.setContactAddr(syncContact.getContact_addr());
		tbContact.setContactCertCode(syncContact.getContact_cert_code());
		tbContact.setContactCertType(syncContact.getContact_cert_type());
		tbContact.setContactEmail(syncContact.getContact_email());
		tbContact.setContactFax(syncContact.getContact_fax());
		tbContact.setContactName(syncContact.getContact_name());
		tbContact.setContactPhone(syncContact.getContact_phone());
		tbContact.setContactWorkUnit(syncContact.getContact_work_unit());
		tbContact.setIsImportantContact(syncContact.getIs_important_contact());
		tbContact.setIsSendSms(syncContact.getIs_send_sms());
		tbContact.setRelation(syncContact.getRelation());
	}
	
	
	@Override
	public void appendReserverCustomerInfo(RequestReserverCar reserverCarRequset) throws CRMException {
		try {
			TbReserverCustomer tbReserverCust = new TbReserverCustomer();
			tbReserverCust.setCreateAt(DateUtil.getDate());
			tbReserverCust.setCustId(Integer.valueOf(reserverCarRequset.getCust_id()));
			tbReserverCust.setCustName(reserverCarRequset.getCust_name());
			tbReserverCust.setGender(reserverCarRequset.getGender());
			tbReserverCust.setPhone(reserverCarRequset.getPhone());
			tbReserverCust.setMemo(reserverCarRequset.getMemo());
			tbReserverCust.setProductTitle(reserverCarRequset.getProduct_title());
			tbReserverCust.setProductDesc(reserverCarRequset.getProduct_desc());
			tbReserverCust.setReserverStore(reserverCarRequset.getReserve_store());
			tbReserverCust.setReserverTime(DateUtil.str2Date(reserverCarRequset.getReserve_time(), DateUtil.yyyy_MM_dd));

			reserverCustEao.save(tbReserverCust);
		} catch(Exception e) {
			throw new CRMException("FAILED", "预约客户信息保存失败！.");
		}
	}

	
	
	@Override
	public void addCustomerFromTM(RequestSyncTMCustomer syncTmCustRequset, ResponseSyncTMCustomer syncTmCustResponse)
			throws CRMException {
		TbPersonalCustomerInfo tbCust = customerEao.findCustomerByPhone(syncTmCustRequset.getPhone());
		if(null == tbCust) {
			tbCust = customerEao.findCustomerByCertInfo(syncTmCustRequset.getCert_type(), syncTmCustRequset.getCert_code());
			if(null == tbCust) {
				logger.info("开始保存天猫客户数据");
				tbCust = new TbPersonalCustomerInfo();
				syncTbCustInfo(tbCust, syncTmCustRequset, syncTmCustResponse);
				
				syncTmCustResponse.setCust_id(String.valueOf(tbCust.getCustId()));
//				return tbCust.getCustId();
			} else {
				throw new CRMException("FAILED", "该客户证件号已在系统中存在！.");
			}
		} else if(StringUtils.isBlank(tbCust.getCertCode())){
			logger.info("用户手机号已存在，身份证号码为空，更新天猫客户数据");
			syncTbCustInfo(tbCust, syncTmCustRequset, syncTmCustResponse);
			syncTmCustResponse.setCust_id(String.valueOf(tbCust.getCustId()));
//			return tbCust.getCustId();
		} else {
			throw new CRMException("FAILED", "该客户手机号已在系统中存在！.");
		}
	}
	

	/**
	 * 同步天猫车秒贷客户信息
	 * @param tbCust
	 * @param syncTmCustRequset
	 * @param syncTmCustResponse 
	 */
	private void syncTbCustInfo(TbPersonalCustomerInfo tbCust, RequestSyncTMCustomer syncTmCustRequset, ResponseSyncTMCustomer syncTmCustResponse) {
		tbCust.setCustName(syncTmCustRequset.getCust_name());
		tbCust.setPhone(syncTmCustRequset.getPhone());
		tbCust.setCertType(syncTmCustRequset.getCert_type());
		tbCust.setCertCode(syncTmCustRequset.getCert_code());
		tbCust.setCustStatus("Y");
		tbCust.setChannel("4");
		tbCust.setCreateAt(DateUtil.getDate());
		tbCust.setUpdateAt(DateUtil.getDate());
		if(0 == tbCust.getCustId()) {
			logger.info("保存天猫用户【"+tbCust.getCustName()+"】");
			customerEao.save(tbCust);
		} else {
			logger.info("更新天猫用户【"+tbCust.getCustName()+"】");
			customerEao.update(tbCust);
		}
		
		if(StringUtils.isNotBlank(syncTmCustRequset.getAlipay_no())) {
			try {
				logger.info("保存天猫用户【"+tbCust.getCustName()+"】支付宝信息");
				TbPersonalAccountInfo tbAccount = new TbPersonalAccountInfo();
				tbAccount.setCustId(tbCust.getCustId());
				tbAccount.setAccName(syncTmCustRequset.getCust_name());
				tbAccount.setAccNo(syncTmCustRequset.getAlipay_no());
				tbAccount.setBankFullName("支付宝");
				tbAccount.setBranchBankName("支付宝");
				tbAccount.setAccStatus("Y");
				
				accountEao.save(tbAccount);
				syncTmCustResponse.setAccount_id(String.valueOf(tbAccount.getId()));
			} catch(Exception e) {
				logger.error("支付宝账户保存失败！", e);
			}
		}
	}


	@Override
	public void addFaceVedioTime(RequestSyncFaceVedioTime syncFaceVedioTimeRequset) throws CRMException {
		TbAuditingPlan tbAuditPlan = tbAuditPlanEao.findFaceVedioPlanByCustId(Integer.valueOf(syncFaceVedioTimeRequset.getCust_id()));
		if(null == tbAuditPlan) {
			tbAuditPlan = new TbAuditingPlan();
			tbAuditPlan.setCustId(Integer.valueOf(syncFaceVedioTimeRequset.getCust_id()));
			tbAuditPlan.setCustName(syncFaceVedioTimeRequset.getCust_name());
			tbAuditPlan.setAudiFlag((byte)0);
			tbAuditPlan.setFirstDate(syncFaceVedioTimeRequset.getFirst_date());
			tbAuditPlan.setSecondDate(syncFaceVedioTimeRequset.getSecond_date());
			tbAuditPlan.setCreateAt(DateUtil.getDate());
			tbAuditPlan.setUpdateAt(DateUtil.getDate());
			
			tbAuditPlanEao.save(tbAuditPlan);
		} else {
			if(tbAuditPlan.getAudiFlag()==1) {
				throw new CRMException("FAILED", "该客户已经完成视频面签审核！");
			}
			tbAuditPlan.setCustName(syncFaceVedioTimeRequset.getCust_name());
			tbAuditPlan.setFirstDate(syncFaceVedioTimeRequset.getFirst_date());
			tbAuditPlan.setSecondDate(syncFaceVedioTimeRequset.getSecond_date());
			tbAuditPlan.setUpdateAt(DateUtil.getDate());
			
			tbAuditPlanEao.update(tbAuditPlan);
		}
	}


	@Override
	public List<TbPersonalAccountInfo> getBanksInfo(RequestGetBankInfo bankInfoRequset) {
		if(StringUtils.isBlank(bankInfoRequset.getAccount())) {
			return bankAccountEao.findDefAccountByCustId(Integer.valueOf(bankInfoRequset.getCust_id()));
		} else {
			return bankAccountEao.findAccountByAccountNo(Integer.valueOf(bankInfoRequset.getCust_id()), Integer.valueOf(bankInfoRequset.getAccount()));
		}
	}


	@Override
	public void setBankInfo(RequestSetBankInfo bankInfoRequset) throws CRMException {
	    bankAccountEao.updateDefaultWithholderFlag(Integer.valueOf(bankInfoRequset.getCust_id()));
		TbPersonalAccountInfo tbAccount = (TbPersonalAccountInfo) bankAccountEao.findByCustIdAndAccount(Integer.valueOf(bankInfoRequset.getCust_id()), bankInfoRequset.getAccount_name(), bankInfoRequset.getAccount_no());
		if(null != tbAccount) {
			tbAccount.setBankPhone(bankInfoRequset.getBank_phone());
			tbAccount.setAbbreviationCardNo(bankInfoRequset.getShort_account_no());
			tbAccount.setIsWithholdAcc("Y");
			tbAccount.setIsYjzfBind(Integer.valueOf(bankInfoRequset.getIs_yjzf_bind()));
			bankAccountEao.update(tbAccount);
		} else {
			throw new CRMException("FAILED", "账户不存在或账户名，手机号，账户号信息不匹配！.");
		}
	}
}
