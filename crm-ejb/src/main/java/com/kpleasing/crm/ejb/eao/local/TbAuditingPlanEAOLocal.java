package com.kpleasing.crm.ejb.eao.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbAuditingPlan;

@Local
public interface TbAuditingPlanEAOLocal extends BaseEaoLocal<TbAuditingPlan, Integer> {

	public TbAuditingPlan findFaceVedioPlanByCustId(Integer custId);

	public List<TbAuditingPlan> findAuditingPlanList(TbAuditingPlan tbAuditingPlan);

	public int findAuditingPlanListByCount(TbAuditingPlan tbAuditingPlan);

}
