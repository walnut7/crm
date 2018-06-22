package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbAuditingPlanEAOLocal;
import com.kpleasing.crm.ejb.entity.TbAuditingPlan;

@Stateless
@LocalBean
public class TbAuditingPlanEAO  extends BaseEao<TbAuditingPlan, Integer> implements TbAuditingPlanEAOLocal {

	private final static String CUST_ID = "custId";
	
	private final static String CUST_NAME = "custName";
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbAuditingPlanEAO() {
    	super(TbAuditingPlan.class);
    }

    
	@Override
	public TbAuditingPlan findFaceVedioPlanByCustId(Integer custId) {
		List<TbAuditingPlan> faceVedioList = this.findByProperty(CUST_ID, custId);
		return (faceVedioList!=null && faceVedioList.size()>0)?faceVedioList.get(0):null;
	}
	
	@Override
	public List<TbAuditingPlan> findAuditingPlanList(TbAuditingPlan tbAuditingPlan) {
		String fieldname = "";
		String fieldvalue = "";
		if(tbAuditingPlan != null){
			if(tbAuditingPlan.getCustName() != null && !"".equals(tbAuditingPlan.getCustName())){
				fieldname += CUST_NAME ;
				fieldvalue += tbAuditingPlan.getCustName() ;
			}
			
		}
		
		if((!"".equals(fieldname)) && (!"".equals(fieldvalue))) {
			String[] fields = fieldname.split(":");
			String[] values = fieldvalue.split(":");
			return this.findByPropertysLike(fields, values, tbAuditingPlan.getPage(), tbAuditingPlan.getRows(), " order by custId desc ");
		} else {
			return this.findAll(tbAuditingPlan.getPage(), tbAuditingPlan.getRows(), " order by custId desc ");
		}
		
	}


	@Override
	public int findAuditingPlanListByCount(TbAuditingPlan tbAuditingPlan) {
		String fieldname = "";
		String fieldvalue = "";
		if(tbAuditingPlan != null){
			if(tbAuditingPlan.getCustName() != null && "".equals(tbAuditingPlan.getCustName())){
				fieldname += CUST_NAME ;
				fieldvalue += tbAuditingPlan.getCustName() ;
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
}
