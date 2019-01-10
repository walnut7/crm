package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.dom4j.Document;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.pojo.Menu;
import com.kpleasing.crm.ejb.service.local.ConfigServiceLocal;
import com.kpleasing.crm.ejb.service.local.NavigationServiceLocal;
import com.kpleasing.crm.ejb.util.NavigationUtil;


@Stateless
@LocalBean
public class NavigationService implements Serializable, NavigationServiceLocal {

	/**	 * 	 */
	private static final long serialVersionUID = -7545682286609453403L;
	
	@EJB
	private ConfigServiceLocal configServ;

	@Override
	public List<Menu> getMenus() { 
		Configurate config = configServ.getConfig();
		NavigationUtil navigation = NavigationUtil.getInstance();
		Document doc = navigation.getDocument(config.NAVIGATION_CONF_PATH);
		List<Menu> menus = navigation.getMenus(doc);
		return menus;
	}

	
	@Override
	public String getJsonMenuTree(List<Menu> menus) {
		NavigationUtil navigation = NavigationUtil.getInstance();
		return navigation.getJsonMenuTree(menus);
	}

}
