package com.kpleasing.crm.ejb.service.local;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.pojo.UserProfile;

@Local
public interface LoginServiceLocal {

	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public UserProfile getLoginUserProfile(UserProfile user);

}
