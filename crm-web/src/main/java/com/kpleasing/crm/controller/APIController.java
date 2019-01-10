package com.kpleasing.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.entity.TbSecurityKey;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.util.EncryptUtil;
import com.kpleasing.crm.ejb.util.XMLHelper;
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
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseGetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseRegister;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseReserverCar;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseStaffLogin;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncCustomerInfo;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncFaceVedioTime;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncTMCustomer;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.GetBankInfoService;
import com.kpleasing.crm.service.GetUserInfoService;
import com.kpleasing.crm.service.GetUserProfileService;
import com.kpleasing.crm.service.RegisterService;
import com.kpleasing.crm.service.SetBankInfoService;
import com.kpleasing.crm.service.SetUserProfileService;
import com.kpleasing.crm.service.StaffLoginService;
import com.kpleasing.crm.service.SyncCustomerInfoService;
import com.kpleasing.crm.service.SyncFaceVedioTimeService;
import com.kpleasing.crm.service.SyncReserverCustomerService;
import com.kpleasing.crm.service.SyncTMCustomerService;

@Controller 
@RequestMapping(value = "/api")
public class APIController {
	
	private static Logger logger = Logger.getLogger(APIController.class);
	
	@Autowired
	private EjbService ejbService;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private GetUserProfileService getUserProfileService;
	
	@Autowired
	private SetUserProfileService setUserProfileService;
	
	@Autowired
	private StaffLoginService staffLoginService;
	
	@Autowired
	private GetUserInfoService getUserInfoService;
	
	@Autowired
	private SyncCustomerInfoService syncCustomerInfoService;
	
	@Autowired
	private SyncReserverCustomerService syncReserverCarService;
	
	@Autowired
	private SyncTMCustomerService syncTMCustService;
	
	@Autowired
	private SyncFaceVedioTimeService syncFVTimeService;
	
	@Autowired
	private GetBankInfoService getBankInfoService;
	
	@Autowired
	private SetBankInfoService setBankInfoService;
	
	
	/**
	 * 系统用户登录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/staffLogin", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String getStaffLogin(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestStaffLogin staffLoginRequest = new RequestStaffLogin();
			XMLHelper.getBeanFromXML(requestXml, staffLoginRequest);
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(staffLoginRequest.getApi_code());
			
			logger.info("准备获取登录管理用户, 生成响应报文.......");
			ResponseStaffLogin staffLoginResponse = new ResponseStaffLogin();
			String responseXML = staffLoginService.generateStaffLoginResponseXML(staffLoginRequest, staffLoginResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = staffLoginService.generateStaffLoginErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = staffLoginService.generateStaffLoginErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String Register(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestRegister regiRequest = new RequestRegister();
			XMLHelper.getBeanFromXML(requestXml, regiRequest);
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(regiRequest.getApi_code());
			
			logger.info("准备创建注册用户, 生成响应报文.......");
			ResponseRegister regiResponse = new ResponseRegister();
			String responseXML = registerService.generateRegisterResponseXML(regiRequest, regiResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = registerService.generateRegisterErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = registerService.generateRegisterErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 更新用户简要信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/setUserProfile", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String setUserProfile(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestSetUserProfile setProfileRequest = new RequestSetUserProfile();
			XMLHelper.getBeanFromXML(requestXml, setProfileRequest);
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(setProfileRequest.getApi_code());
			
			logger.info("准备修改用户手机号, 生成响应报文.......");
			ResponseSetUserProfile getProfileResponse = new ResponseSetUserProfile();
			String responseXML = setUserProfileService.generateSetUserProfileResponseXML(setProfileRequest, getProfileResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = setUserProfileService.generateSetUserProfileErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = setUserProfileService.generateSetUserProfileErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 获取客户简要信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getUserProfile", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String getUserProfile(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestGetUserProfile getProfileRequest = new RequestGetUserProfile();
			XMLHelper.getBeanFromXML(requestXml, getProfileRequest);
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(getProfileRequest.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseGetUserProfile getProfileResponse = new ResponseGetUserProfile();
			String responseXML = getUserProfileService.generateGetUserProfileResponseXML(getProfileRequest, getProfileResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = getUserProfileService.generateGetUserProfileErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = getUserProfileService.generateGetUserProfileErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String getUserInfo(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestGetUserInfo getUserRequest = new RequestGetUserInfo();
			XMLHelper.getBeanFromXML(requestXml, getUserRequest);
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(getUserRequest.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseGetUserInfo getUserResponse = new ResponseGetUserInfo();
			String responseXML = getUserInfoService.generateGetUserInfoResponseXML(getUserRequest, getUserResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = getUserInfoService.generateGetUserInfoErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = getUserInfoService.generateGetUserInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 导入用户信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/setUserInfo", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SetUserInfo(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestSetUserInfo syncCustInfo = new RequestSetUserInfo();
			// XMLHelper.getBeanFromXML(requestXml, syncCustInfo);
			logger.info("客户信息......");
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(syncCustInfo, map);
			}
			
			logger.info("转换联系人信息......");
			List<Map<String,String>> mapList1 = XMLHelper.parseMultNodesXml(requestXml, "//contact");
			List<RequestSyncContactInfo> syncContracts = new ArrayList<RequestSyncContactInfo>();
			for(Map<String,String> map1 : mapList1) {
				RequestSyncContactInfo syncContract = new RequestSyncContactInfo();
				BeanUtils.populate(syncContract, map1);
				syncContracts.add(syncContract);
			}
			syncCustInfo.setContacts(syncContracts);
			
			logger.info("转换账户信息......");
			List<Map<String,String>> mapList2 = XMLHelper.parseMultNodesXml(requestXml, "//account");
			List<RequestSyncAccountInfo> syncAccounts = new ArrayList<RequestSyncAccountInfo>();
			for(Map<String,String> map2 : mapList2) {
				RequestSyncAccountInfo syncAccount = new RequestSyncAccountInfo();
				BeanUtils.populate(syncAccount, map2);
				syncAccounts.add(syncAccount);
			}
			syncCustInfo.setAccounts(syncAccounts);
			
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(syncCustInfo.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseSyncCustomerInfo syncCustInfoResponse = new ResponseSyncCustomerInfo();
			String responseXML = syncCustomerInfoService.generateSyncCustInfoResponseXML(syncCustInfo, syncCustInfoResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("处理失败：", e);
			String responseXML = syncCustomerInfoService.generateSyncCustInfoErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("处理失败："+e.getMessage(), e);
			String responseXML = syncCustomerInfoService.generateSyncCustInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 客户预约到店
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/syncReserverCustomer", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncRrserverCustomer(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求【SYNC_RESERVER_CUSTOMER】报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestReserverCar reserverCarRequset = new RequestReserverCar();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(reserverCarRequset, map);
			}
			
			logger.info("根据API_CODE=【"+reserverCarRequset.getApi_code()+"】匹配配置参数.......");
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(reserverCarRequset.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseReserverCar reserverCarResponse = new ResponseReserverCar();
			String responseXML = syncReserverCarService.generateSyncReserverCustomerResponseXML(reserverCarRequset, reserverCarResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_RESERVER_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("【SYNC_RESERVER_CUSTOMER】处理失败：", e);
			String responseXML = syncReserverCarService.generateSyncReserverCustomerErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_RESERVER_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("【SYNC_RESERVER_CUSTOMER】处理失败："+e.getMessage(), e);
			String responseXML = syncReserverCarService.generateSyncReserverCustomerErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_RESERVER_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 同步天猫订单客户数据
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/syncTMCustomer", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncTMCustomer(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求【SYNC_TM_CUSTOMER】报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestSyncTMCustomer syncTmCustRequset = new RequestSyncTMCustomer();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(syncTmCustRequset, map);
			}
			
			logger.info("根据API_CODE=【"+syncTmCustRequset.getApi_code()+"】匹配配置参数.......");
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(syncTmCustRequset.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseSyncTMCustomer syncTmCustResponse = new ResponseSyncTMCustomer();
			String responseXML = syncTMCustService.generateSyncTMCustomerResponseXML(syncTmCustRequset, syncTmCustResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_TM_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("【SYNC_TM_CUSTOMER】处理失败：", e);
			String responseXML = syncTMCustService.generateSyncTMCustomerErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_TM_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("【SYNC_TM_CUSTOMER】处理失败："+e.getMessage(), e);
			String responseXML = syncTMCustService.generateSyncTMCustomerErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_TM_CUSTOMER】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 同步视频面签预约时间
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/syncFaceVedioTime", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncFaceVedioTime(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求【SYNC_FACEVEDIO_TIME】报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestSyncFaceVedioTime syncFaceVedioTimeRequset = new RequestSyncFaceVedioTime();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(syncFaceVedioTimeRequset, map);
			}
			
			logger.info("根据API_CODE=【"+syncFaceVedioTimeRequset.getApi_code()+"】匹配配置参数.......");
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(syncFaceVedioTimeRequset.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseSyncFaceVedioTime syncFaceVedioTimeResponse = new ResponseSyncFaceVedioTime();
			String responseXML = syncFVTimeService.generateSyncFaceVedioTimeResponseXML(syncFaceVedioTimeRequset, syncFaceVedioTimeResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_FACEVEDIO_TIME】响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("【SYNC_FACEVEDIO_TIME】处理失败：", e);
			String responseXML = syncFVTimeService.generateSyncFaceVedioTimeErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_FACEVEDIO_TIME】响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("【SYNC_FACEVEDIO_TIME】处理失败："+e.getMessage(), e);
			String responseXML = syncFVTimeService.generateSyncFaceVedioTimeErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SYNC_FACEVEDIO_TIME】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	/**
	 * 查询银行账户信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/getBankAccountInfo", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String getBankAccountInfo(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求【GET_BANK_ACCOUNT_INFO】报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestGetBankInfo getBankInfoRequset = new RequestGetBankInfo();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(getBankInfoRequset, map);
			}
			
			logger.info("根据API_CODE=【"+getBankInfoRequset.getApi_code()+"】匹配配置参数.......");
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(getBankInfoRequset.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseGetBankInfo getBankInfoResponse = new ResponseGetBankInfo();
			String responseXML = getBankInfoService.generateBankInfoResponseXML(getBankInfoRequset, getBankInfoResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【GET_BANK_ACCOUNT_INFO】响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("【GET_BANK_ACCOUNT_INFO】处理失败：", e);
			String responseXML = getBankInfoService.generateBankInfoErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【GET_BANK_ACCOUNT_INFO】响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("【GET_BANK_ACCOUNT_INFO】处理失败："+e.getMessage(), e);
			String responseXML = getBankInfoService.generateBankInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【GET_BANK_ACCOUNT_INFO】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	
	/**
	 * 一键支付银行账户绑定设置
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws IOException
	 */
	@RequestMapping(value = "/setPayAccountBind", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SetFaceVedioTime(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = ejbService.getConfigServ().getConfig();
		
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("請求【SET_PAYMENT_ACCOUNT_BIND】报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			RequestSetBankInfo setBankInfoRequset = new RequestSetBankInfo();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/crm/head|/crm/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(setBankInfoRequset, map);
			}
			
			logger.info("根据API_CODE=【"+setBankInfoRequset.getApi_code()+"】匹配配置参数.......");
			TbSecurityKey param = ejbService.getConfigServ().getConfig().getSecurityKey(setBankInfoRequset.getApi_code());
			
			logger.info("准备获取用户信息, 生成响应报文.......");
			ResponseSetBankInfo setBankInfoResponse = new ResponseSetBankInfo();
			String responseXML = setBankInfoService.generateSetBankInfoResponseXML(setBankInfoRequset, setBankInfoResponse, param);
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SET_PAYMENT_ACCOUNT_BIND】响应密文：" + msgResponse);
			return msgResponse;
		} catch (CRMException e) {
			logger.error("【SET_PAYMENT_ACCOUNT_BIND】处理失败：", e);
			String responseXML = setBankInfoService.generateSetBankInfoErrorXML(e.getCode(), e.getDescription());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SET_PAYMENT_ACCOUNT_BIND】响应密文：" + msgResponse);
			return msgResponse;
		} catch (Exception e) {
			logger.error("【SET_PAYMENT_ACCOUNT_BIND】处理失败："+e.getMessage(), e);
			String responseXML = setBankInfoService.generateSetBankInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("【SET_PAYMENT_ACCOUNT_BIND】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
}
