package com.kpleasing.crm.ejb.eao.local;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbStaffInfo;

@Local
public interface TbStaffInfoEAOLocal extends BaseEaoLocal<TbStaffInfo, Integer> {

	/**
	 * 根据用户名，密码查找员工信息
	 * @param login
	 * @param pwd
	 * @return
	 */
	public TbStaffInfo getStaffInfoByNameAndPWD(String login_id, String password);


}
