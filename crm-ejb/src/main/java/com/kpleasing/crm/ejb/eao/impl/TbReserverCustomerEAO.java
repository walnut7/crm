package com.kpleasing.crm.ejb.eao.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.eao.local.TbReserverCustomerEAOLocal;
import com.kpleasing.crm.ejb.entity.TbReserverCustomer;

@Stateless
@LocalBean
public class TbReserverCustomerEAO extends BaseEao<TbReserverCustomer, Integer> implements TbReserverCustomerEAOLocal {

	private final static String CUST_ID = "custId";
	
	private final static String CUST_NAME = "custName";
	
	private final static String PHONE = "phone";
	
	/**
     * @see BaseEao#BaseEao()
     */
    public TbReserverCustomerEAO() {
    	super(TbReserverCustomer.class);
    }

	@Override
	public List<TbReserverCustomer> findReserverList(TbReserverCustomer tbReserverCustomer) {
		String fieldname = "";
		String fieldvalue = "";
		if(tbReserverCustomer != null){
			if(tbReserverCustomer.getCustName() != null && !"".equals(tbReserverCustomer.getCustName())){
				fieldname += CUST_NAME + ":";
				fieldvalue += tbReserverCustomer.getCustName() + ":";
			}
			if(tbReserverCustomer.getPhone() != null && !"".equals(tbReserverCustomer.getPhone())){
				fieldname += PHONE ;
				fieldvalue += tbReserverCustomer.getPhone();
			}
			
		}
		
		if((!"".equals(fieldname)) && (!"".equals(fieldvalue))) {
			String[] fields = fieldname.split(":");
			String[] values = fieldvalue.split(":");
			return this.findByPropertysLike(fields, values, tbReserverCustomer.getPage(), tbReserverCustomer.getRows(), " order by id desc ");
		} else {
			return this.findAll(tbReserverCustomer.getPage(), tbReserverCustomer.getRows(), " order by id desc ");
		}
	}

	@Override
	public int findReserverListByCount(TbReserverCustomer tbReserverCustomer) {
		String fieldname = "";
		String fieldvalue = "";
		if(tbReserverCustomer != null){
			if(tbReserverCustomer.getCustName() != null && !"".equals(tbReserverCustomer.getCustName())){
				fieldname += CUST_NAME + ":";
				fieldvalue += tbReserverCustomer.getCustName() + ":";
			}
			if(tbReserverCustomer.getPhone() != null && !"".equals(tbReserverCustomer.getPhone())){
				fieldname += PHONE ;
				fieldvalue += tbReserverCustomer.getPhone();
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
