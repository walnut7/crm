package com.kpleasing.crm.ejb.eao.impl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.RStaffInfoRoleEAOLocal;
import com.kpleasing.crm.ejb.entity.RStaffInfoRole;

@Stateless
@LocalBean
public class RStaffInfoRoleEAO extends BaseEao<RStaffInfoRole, Integer> implements RStaffInfoRoleEAOLocal {

	/**
     * @see BaseEao#BaseEao()
     */
    public RStaffInfoRoleEAO() {
    	super(RStaffInfoRole.class);
    }
}
