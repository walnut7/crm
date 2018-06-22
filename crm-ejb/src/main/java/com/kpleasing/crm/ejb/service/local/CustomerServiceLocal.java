package com.kpleasing.crm.ejb.service.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;
import com.kpleasing.crm.ejb.entity.TbAuditingPlan;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbReserverCustomer;
import com.kpleasing.crm.ejb.exception.CRMException;


@Local
public interface CustomerServiceLocal {
	
	public void saveCustInfoAndDetail(TbPersonalCustomerInfo customerInfo,TbPersonalCustomerDetailInfo customerDetail);

	public List<TbPersonalCustomerInfo> getCustInfoListByPorperty(TbPersonalCustomerInfo customerInfo);

	public void updateCustInfoAndDetail(TbPersonalCustomerInfo customerInfo, TbPersonalCustomerDetailInfo customerDetail);

	public List<TbPersonalContactRelation> getCustomerContactList(TbPersonalContactRelation contactInfo);

	public void saveContactRelation(TbPersonalContactRelation contactRelation);

	public List<TbPersonalAccountInfo> getAccountInfoList(TbPersonalAccountInfo accountInfo);

	public void saveAccountInfo(TbPersonalAccountInfo accountInfo);

	public void deleteContactRelation(TbPersonalContactRelation contactRelation);

	public void deleteAccountInfo(TbPersonalAccountInfo accountInfo);

	public Map<String, Object> getCustAndDetailInfoMap(TbPersonalCustomerInfo customerInfo);

	public String structCustRequestParam(int custId,String wxFlag) throws IllegalArgumentException, IllegalAccessException;

	public int getCustInfoListCount(TbPersonalCustomerInfo customerInfo);

	public int getCustomerContactCount(TbPersonalContactRelation contactInfo);

	public int getAccountInfoCount(TbPersonalAccountInfo accountInfo);

	public int getCustCountByPhone(String phone);
	
	public int getCustCountByCertCode(String certCode);

	public void saveExcelImportCustInfo(List<String[]> list) throws CRMException;

	public void saveOrUpdateCustAttachment(TbAttachmentInfo tbAttachmentInfo, RCustAttachment rCustAttachment);

	public void deleteRCustAttachmentById(int id);

	public Map<String, Object> getAttachmentInfoMap(int custId);

	public TbAttachmentInfo findAttachEntityById(int id);

	public List<TbAuditingPlan> getAuditingPlanListByPorperty(TbAuditingPlan tbAuditingPlan);

	public int getAuditingPlanListCount(TbAuditingPlan tbAuditingPlan);

	public TbPersonalCustomerInfo getCustInfoByID(int custId);

	public void saveVideoAuthInfo(TbAuditingPlan tbAuditingPlan);

	public List<TbReserverCustomer> getReserverListByPorperty(TbReserverCustomer tbReserverCustomer);

	public int getReserverListCount(TbReserverCustomer tbReserverCustomer);

}
