package com.kpleasing.crm.ejb.eao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.eao.local.RCustAttachmentEaoLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalAccountInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalContactRelationEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerDetailInfoEAOLocal;
import com.kpleasing.crm.ejb.eao.local.TbPersonalCustomerInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;


@Stateless
@LocalBean
public class TbPersonalCustomerInfoEAO extends BaseEao<TbPersonalCustomerInfo, Integer> implements TbPersonalCustomerInfoEAOLocal {

	private static Logger logger = Logger.getLogger(TbPersonalCustomerInfoEAO.class);
	
	private final static String CUST_ID = "custId";
	
	private final static String FIELD_PHONE = "phone";
	
	private final static String FIELD_OPEN_ID = "wxOpenId";
	
	private final static String FIELD_CERT_TYPE = "certType";
	
	private final static String FIELD_CERT_CODE = "certCode";
	
	private final static String FIELD_CUST_NAME = "custName";
	
	private final static String FIELD_CUST_TYPE = "custType";
	
	private final static String FIELD_CUST_STATUS = "custStatus";
	
	private final static String FIELD_CHANNEL = "channel";
	
	@EJB
	private TbPersonalCustomerDetailInfoEAOLocal custDetailEao;
	
	@EJB
	private TbPersonalContactRelationEAOLocal contactRelationEao;
	
	@EJB
	private TbPersonalAccountInfoEAOLocal accountEao;
	
	@EJB
	private RCustAttachmentEaoLocal attachEao;
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbPersonalCustomerInfoEAO() {
    	super(TbPersonalCustomerInfo.class);
    }

    
	@Override
	public TbPersonalCustomerInfo findCustomerByPhone(String phone) {
		List<TbPersonalCustomerInfo> custList = this.findByProperty(FIELD_PHONE, phone);
		return (custList!=null && custList.size()>0)?custList.get(0):null;
	}
	
	
	@Override
	public TbPersonalCustomerInfo findCustomerByOpenId(String openId) {
		List<TbPersonalCustomerInfo> custList = this.findByProperty(FIELD_OPEN_ID, openId);
		return (custList!=null && custList.size()>0)?custList.get(0):null;
	}
	
	

	@Override
	public TbPersonalCustomerInfo findCustomerByPhoneOrOpenId(String phone, String openId) {
		TbPersonalCustomerInfo cust = findCustomerByPhone(phone);
		if(null == cust) {
			List<TbPersonalCustomerInfo> custList = this.findByProperty(FIELD_OPEN_ID, openId);
			return (custList!=null && custList.size()>0)?custList.get(0):null;
		}
		return cust;
	}


	@Override
	public TbPersonalCustomerInfo getUserInfo(int id) {
		TbPersonalCustomerInfo custInfo = this.findById(id);
		if(null != custInfo) {
			logger.info("关联用户详细信息.......");
			TbPersonalCustomerDetailInfo custDatil = custDetailEao.findCustDetailByCustId(id);
			if(null != custDatil) {
				custInfo.setTbPersonalCustomerDetailInfo(custDatil);
			}
			
			logger.info("关联联系人信息.......");
			List<TbPersonalContactRelation> contactList = contactRelationEao.findCustContactRelationByCustId(id);
			if(null != contactList && !contactList.isEmpty()) {
				custInfo.setTbPersonalContactRelationList(contactList);
			}
			
			logger.info("关联账户信息.......");
			List<TbPersonalAccountInfo> accountList = accountEao.findByCustId(id);
			if(null != accountList && !accountList.isEmpty()) {
				custInfo.setTbPersonalAccountInfoList(accountList);
			}
			
			logger.info("关联附件证件信息.......");
			List<RCustAttachment> rCustAttachmentList = attachEao.findCustAttachmentByCustId(id);
			if(null != rCustAttachmentList && !rCustAttachmentList.isEmpty()) {
				custInfo.setRCustAttachmentList(rCustAttachmentList);
			}
		}
		return custInfo; 
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPersonalCustomerInfo> findByPropertyMapOrder(Map<String, Object> map,int page, int pageSize){
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + this.entityClass.getSimpleName());
		if(map.size()>0){
			strBuffer.append(" as model where ");
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object value = map.get(key);
	
				strBuffer.append(" model." + key + "= '" + value + "'");
				strBuffer.append(" and");
			}
			strBuffer.delete(strBuffer.length() - 4, strBuffer.length());
		}
		strBuffer.append(" order by custId desc ");
		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);
		int firstResult = (page - 1) * pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	
	@Override
	public List<TbPersonalCustomerInfo> findCustomerListByProperty(TbPersonalCustomerInfo customerInfo){
		String fieldname = "";
		String fieldvalue = "";
		if(null != customerInfo) {
			if(!"".equals(customerInfo.getCustName()) && customerInfo.getCustName()!=null){
				fieldname += FIELD_CUST_NAME + ":";
				fieldvalue += customerInfo.getCustName() + ":";
			}
			if(!"".equals(customerInfo.getCertCode()) && customerInfo.getCertCode()!=null){
				fieldname += FIELD_CERT_CODE + ":";
				fieldvalue += customerInfo.getCertCode() + ":";
			}
			if(!"".equals(customerInfo.getPhone()) && customerInfo.getPhone()!=null){
				fieldname += FIELD_PHONE + ":";
				fieldvalue += customerInfo.getPhone() + ":";
			}
			if(!"".equals(customerInfo.getCustType()) && customerInfo.getCustType()!=null){
				fieldname += FIELD_CUST_TYPE + ":";
				fieldvalue += customerInfo.getCustType() + ":";
			}
			if(!"".equals(customerInfo.getCustStatus()) && customerInfo.getCustStatus()!=null){
				fieldname += FIELD_CUST_STATUS + ":";
				fieldvalue += customerInfo.getCustStatus() + ":";
			}
			if(!"".equals(customerInfo.getChannel()) && customerInfo.getChannel()!=null){
				fieldname += FIELD_CHANNEL;
				fieldvalue += customerInfo.getChannel();
			}
		}
		
		if((!"".equals(fieldname)) && (!"".equals(fieldvalue))) {
			String[] fields = fieldname.split(":");
			String[] values = fieldvalue.split(":");
			return this.findByPropertysLike(fields, values, customerInfo.getPage(), customerInfo.getRows(), " order by custId desc ");
		} else {
			return this.findAll(customerInfo.getPage(), customerInfo.getRows(), " order by custId desc ");
		}
	}


	@Override
	public int findCustomerListByCount(TbPersonalCustomerInfo customerInfo) {
		String fieldname = "";
		String fieldvalue = "";
		if(null != customerInfo) {
			if(!"".equals(customerInfo.getCustName()) && customerInfo.getCustName()!=null) {
				fieldname += FIELD_CUST_NAME + ":";
				fieldvalue += customerInfo.getCustName() + ":";
			}
			if(!"".equals(customerInfo.getCertCode()) && customerInfo.getCertCode()!=null) {
				fieldname += FIELD_CERT_CODE + ":";
				fieldvalue += customerInfo.getCertCode() + ":";
			}
			if(!"".equals(customerInfo.getPhone()) && customerInfo.getPhone()!=null){
				fieldname += FIELD_PHONE + ":";
				fieldvalue += customerInfo.getPhone() + ":";
			}
			if(!"".equals(customerInfo.getCustType()) && customerInfo.getCustType()!=null) {
				fieldname += FIELD_CUST_TYPE + ":";
				fieldvalue += customerInfo.getCustType() + ":";
			}
			if(!"".equals(customerInfo.getCustStatus()) && customerInfo.getCustStatus()!=null) {
				fieldname += FIELD_CUST_STATUS + ":";
				fieldvalue += customerInfo.getCustStatus() + ":";
			}
			if(!"".equals(customerInfo.getChannel()) && customerInfo.getChannel()!=null) {
				fieldname += FIELD_CHANNEL;
				fieldvalue += customerInfo.getChannel();
			}
		}
		
		if((!"".equals(fieldname)) && (!"".equals(fieldvalue))) {
			String[] fields = fieldname.split(":");
			String[] values = fieldvalue.split(":");
			return this.countByPropertysLike(fields, values);
		} else {
			return this.countAll();
		}
	}
	


	@Override
	public TbPersonalCustomerInfo findCustomerByCertInfo(String cert_type, String cert_code) {
		List<TbPersonalCustomerInfo> custList = this.findByProperty(new String[]{FIELD_CERT_TYPE, FIELD_CERT_CODE}, cert_type,  cert_code);
		return (custList!=null && custList.size()>0)?custList.get(0):null;
	}


	@Override
	public TbPersonalCustomerInfo findCustomerByCustId(Integer custId) {
		List<TbPersonalCustomerInfo> custList = this.findByProperty(new String[]{CUST_ID}, custId);
		return (custList!=null && custList.size()>0)?custList.get(0):null;
	}

	
}
