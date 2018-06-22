package com.kpleasing.crm.ejb.service.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbStaffInfo;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestGetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestRegister;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestReserverCar;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetBankInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserInfo;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSetUserProfile;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestStaffLogin;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncFaceVedioTime;
import com.kpleasing.crm.ejb.xmlpojo.request.RequestSyncTMCustomer;
import com.kpleasing.crm.ejb.xmlpojo.response.ResponseSyncTMCustomer;



@Local
public interface APIServiceLocal {

	/**
	 * 用户注册
	 * @param regiRequest
	 * @return
	 * @throws CRMException 
	 * @throws Exception
	 */
	public int Register(RequestRegister regiRequest) throws CRMException ;

	
	/**
	 * 获取用户简要信息
	 * @param getProfileRequest
	 * @throws CRMException 
	 */
	public TbPersonalCustomerInfo getUserProfile(RequestGetUserProfile getProfileRequest) throws CRMException;


	/**
	 * 修改用户简要信息
	 * @param setProfileRequest
	 * @return
	 */
	public void setUserProfile(RequestSetUserProfile setProfileRequest);

	
	/**
	 * 获取登录用户信息
	 * @param staffLoginRequest
	 * @return 
	 * @throws CRMException 
	 */
	public TbStaffInfo getStaffLoginInfo(RequestStaffLogin staffLoginRequest) throws CRMException;


	
	/**
	 * 获取用户信息
	 * @param getUserRequest
	 * @return
	 * @throws CRMException 
	 */
	public TbPersonalCustomerInfo getUserInfo(RequestGetUserInfo getUserRequest) throws CRMException;


	
	/**
	 * 保存客户详情信息
	 * @param syncCustInfoRequest
	 * @return
	 * @throws CRMException 
	 */
	public void saveCustInfo(RequestSetUserInfo syncCustInfoRequest) throws CRMException;


	
	/**
	 * 添加客户预约信息
	 * @param reserverCarRequset
	 * @throws CRMException
	 */
	public void appendReserverCustomerInfo(RequestReserverCar reserverCarRequset) throws CRMException;


	/**
	 * 创建从天猫同步过来的订单
	 * @param syncTmCustRequset
	 * @param syncTmCustResponse 
	 * @return 
	 * @throws CRMException 
	 */
	public void addCustomerFromTM(RequestSyncTMCustomer syncTmCustRequset, ResponseSyncTMCustomer syncTmCustResponse) throws CRMException;


	
	/**
	 * 创建视频面签信息
	 * @param syncFaceVedioTimeRequset
	 * @throws CRMException 
	 */
	public void addFaceVedioTime(RequestSyncFaceVedioTime syncFaceVedioTimeRequset) throws CRMException;


	
	/**
	 * 
	 * @param bankInfoRequset
	 * @return
	 */
	public List<TbPersonalAccountInfo> getBanksInfo(RequestGetBankInfo bankInfoRequset);


	/**
	 * 
	 * @param bankInfoRequset
	 * @throws CRMException 
	 */
	public void setBankInfo(RequestSetBankInfo bankInfoRequset) throws CRMException;



}
