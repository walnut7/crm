package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.kpleasing.crm.ejb.eao.local.TbStaffInfoEAOLocal;
import com.kpleasing.crm.ejb.entity.TbStaffInfo;
import com.kpleasing.crm.ejb.pojo.UserProfile;
import com.kpleasing.crm.ejb.service.local.LoginServiceLocal;

@Stateless
@LocalBean
public class LoginService implements Serializable, LoginServiceLocal {

	/** *  */
	private static final long serialVersionUID = -1380257726999276622L;
	
	private static Logger logger = Logger.getLogger(LoginService.class);
	
	@EJB
	private TbStaffInfoEAOLocal staffEao;

	
	@Override
	public UserProfile getLoginUserProfile(UserProfile user) {
		TbStaffInfo staff =  staffEao.getStaffInfoByNameAndPWD(user.getUsername(), user.getPassword().toLowerCase());
		logger.info("username:"+user.getUsername()+"   password:"+user.getPassword());
		if (staff != null) {
			logger.info(staff.getEmail());
			logger.info(staff.getStaffName());
			user.setLoginInfo("登录成功！");
			user.setLoginStatus("SUCCESS");
		} else {
			user.setLoginInfo("用户名或密码不正确");
			user.setLoginStatus("FAILED");
		}
		return user;
	}

}
