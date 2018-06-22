package com.kpleasing.crm.ejb.eao.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;

@Local
public interface TbAttachmentInfoEAOLocal extends BaseEaoLocal<TbAttachmentInfo, Integer> {

	public List<TbAttachmentInfo> findAttachInfoByCustId(int custId);

	
	/**
	 * 
	 * @param attachId 
	 * @return
	 */
	public TbAttachmentInfo findAttachmentByAttchId(Integer attachId);


	public Map<String, Object> getCustAttachmentInfoMap(int custId);

}
