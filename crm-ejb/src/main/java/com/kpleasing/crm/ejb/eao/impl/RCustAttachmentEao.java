package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.RCustAttachmentEaoLocal;
import com.kpleasing.crm.ejb.eao.local.TbAttachmentInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.RCustAttachment;

@Stateless
@LocalBean
public class RCustAttachmentEao extends BaseEao<RCustAttachment, Integer> implements RCustAttachmentEaoLocal {

	private final static String CUST_ID = "custId";
	
	@EJB
	private TbAttachmentInfoEAOLocal tbAttachEao;
	
	/**
     * @see BaseEao#BaseEao()
     */
	public RCustAttachmentEao(){
		super(RCustAttachment.class);
	}
	
	@Override
	public RCustAttachment findRcustAttachment(Integer custId) {
		List<RCustAttachment> list = this.findByProperty("custId", custId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	
	@Override
	public List<RCustAttachment> findCustAttachmentByCustId(Integer custId) {
		List<RCustAttachment> rAttachmentList = this.findByProperty(CUST_ID, custId);
		if(rAttachmentList != null && rAttachmentList.size()>0) {
			for(RCustAttachment rAttachment : rAttachmentList) {
				rAttachment.setTbAttachmentInfo(tbAttachEao.findAttachmentByAttchId(rAttachment.getAttachmentId()));
			} 
			return rAttachmentList;
		}
		return null;
	}
}
