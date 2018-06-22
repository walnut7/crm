package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.RCustAttachment;

@Local
public interface RCustAttachmentEaoLocal extends BaseEaoLocal<RCustAttachment, Integer> {
	public RCustAttachment findRcustAttachment(Integer custId);

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<RCustAttachment> findCustAttachmentByCustId(Integer custId);
}
