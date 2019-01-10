package com.kpleasing.crm.ejb.service.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.pojo.Menu;

@Local
public interface NavigationServiceLocal {

	/**
	 * 
	 * @return
	 */
	public List<Menu> getMenus();

	
	/**
	 * 
	 * @param menus
	 * @return
	 */
	public String getJsonMenuTree(List<Menu> menus);

}
