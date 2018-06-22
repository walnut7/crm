package com.kpleasing.crm.ejb.eao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.RCustAttachmentEaoLocal;
import com.kpleasing.crm.ejb.eao.local.TbAttachmentInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;

@Stateless
@LocalBean
public class TbAttachmentInfoEAO extends BaseEao<TbAttachmentInfo, Integer> implements TbAttachmentInfoEAOLocal {

	private final static String ATTACH_ID = "attachmentId";
	
	@EJB
	private RCustAttachmentEaoLocal rCustAttachmentEao;
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbAttachmentInfoEAO() {
    	super(TbAttachmentInfo.class);
    }

	@Override
	public List<TbAttachmentInfo> findAttachInfoByCustId(int custId) {
		String[] fields = {"custId","flag"};
		Object[] values = {custId,0};
		List<RCustAttachment> rca_list = rCustAttachmentEao.findByProperty(fields, values);
		List<TbAttachmentInfo> tai_list = null;
		if(rca_list != null && rca_list.size()>0){
			tai_list = new ArrayList<TbAttachmentInfo>();
			for(RCustAttachment rca:rca_list ){
				TbAttachmentInfo tai = this.findById(rca.getAttachmentId());
				tai_list.add(tai);
			}
		}
		return tai_list;
	}

	
	@Override
	public TbAttachmentInfo findAttachmentByAttchId(Integer attachId) {
		List<TbAttachmentInfo> list = this.findByProperty(ATTACH_ID, attachId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public Map<String, Object> getCustAttachmentInfoMap(int custId) {
		String[] fields = {"custId","flag"};
		Object[] values = {custId,0};
		List<RCustAttachment> rca_list = rCustAttachmentEao.findByProperty(fields, values);
		Map<String,Object> map = null;
		if(rca_list!=null && rca_list.size()>0){
			map = new HashMap<String,Object>();
			for(RCustAttachment rca : rca_list){
				TbAttachmentInfo tbAttach = this.findById(rca.getAttachmentId());
				String attachType = tbAttach.getAttachmentType();
				map.put(attachType+"_id", rca.getId());
				map.put(attachType+"_n", tbAttach.getFileName());
				//map.put(attachType+"_p", tbAttach.getRemotePath());
			}
		}
		return map;
	}

}
