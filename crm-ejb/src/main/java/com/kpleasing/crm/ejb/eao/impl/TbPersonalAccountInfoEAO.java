package com.kpleasing.crm.ejb.eao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.kpleasing.crm.ejb.eao.local.TbPersonalAccountInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;


@Stateless
@LocalBean
public class TbPersonalAccountInfoEAO extends BaseEao<TbPersonalAccountInfo, Integer> implements TbPersonalAccountInfoEAOLocal {

	private final static String CUST_ID = "custId";
	private final static String IS_WITHHOLDER_ACC= "isWithholdAcc";
	private final static String ACCOUNT = "accNo";
	private final static String STATUS = "accStatus";
	private final static String ACCOUNT_NAME = "accName";
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbPersonalAccountInfoEAO() {
    	super(TbPersonalAccountInfo.class);
    }

	public List<TbPersonalAccountInfo> findByCustId(Integer custId) {
		return this.findByProperty(new String[] {CUST_ID, STATUS}, new Object[] {custId, "Y"});
	}
	
	@Override
	public List<TbPersonalAccountInfo> findDefAccountByCustId(Integer custId) {
		return this.findByProperty(new String[] {CUST_ID, IS_WITHHOLDER_ACC, STATUS}, new Object[] {custId, "Y", "Y"});
	}

	@Override
	public List<TbPersonalAccountInfo> findAccountByAccountNo(Integer custId, Integer accountNo) {
		return this.findByProperty(new String[] {CUST_ID, ACCOUNT, IS_WITHHOLDER_ACC, STATUS}, new Object[] {custId, accountNo, "Y", "Y"});
	}

	@Override
	public void updateDefaultWithholderFlag(Integer custId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<TbPersonalAccountInfo> op = cb.createCriteriaUpdate(TbPersonalAccountInfo.class);
		Root<TbPersonalAccountInfo> root = op.from(TbPersonalAccountInfo.class);
		
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		predicatesList.add(cb.equal(root.get(CUST_ID), custId));
		predicatesList.add(cb.equal(root.get(STATUS), "Y"));
		op.set(IS_WITHHOLDER_ACC, "N").where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		
		//op.set(IS_WITHHOLDER_ACC, "N").where(cb.equal(root.get(CUST_ID), custId));
		entityManager.createQuery(op).executeUpdate();
	}

	@Override
	public TbPersonalAccountInfo findByCustIdAndAccount(Integer custId, String accountName, String accountNo) {
		List<TbPersonalAccountInfo> tbAccount =  this.findByProperty(new String[] {CUST_ID, ACCOUNT_NAME, ACCOUNT, STATUS}, new Object[] {custId, accountName, accountNo, "Y"});
		return (tbAccount!=null && tbAccount.size()>0) ? tbAccount.get(0):null;
	}
}
