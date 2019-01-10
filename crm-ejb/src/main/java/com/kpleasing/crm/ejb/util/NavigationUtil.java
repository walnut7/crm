package com.kpleasing.crm.ejb.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.kpleasing.crm.ejb.pojo.Item;
import com.kpleasing.crm.ejb.pojo.Menu;

/**
 * 
 * @author howard.huang
 *
 */
public class NavigationUtil {
	private static Logger logger = Logger.getLogger(NavigationUtil.class);
	private static NavigationUtil instance;
	
	
	public synchronized static NavigationUtil getInstance() {
		if (instance == null) {
			instance = new NavigationUtil();
		}
		return instance;
	}
	
	private  NavigationUtil() { }
	
	public Document getDocument(String naviFile) {
		try {
			SAXReader reader = new SAXReader();
			return reader.read(new File(naviFile));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	/**
	 * 一级菜单项
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getMenus(Document document) {
		Element root = document.getRootElement();
		List<Element> eMenus = root.element("menus").elements();
		List<Node> subMenus = root.selectNodes("//sub-menu");
		
		List<Menu> menuList = new ArrayList<Menu>();
		for(Element eMenu : eMenus) {
			if(!isSubMenu(eMenu, subMenus)) {
				Menu menu = new Menu();
				menu.setName(eMenu.attributeValue("name"));
				menu.setLabel(eMenu.attributeValue("label"));
				menu.setRef(eMenu.attributeValue("ref"));
				
				if(StringUtils.isBlank(menu.getRef())) {
					menu.setSub_menus(getSubMenus(document, eMenu));
					menu.setItems(getItems(document, eMenu));
				} else {
					menu.setItems(getItems(document, menu.getRef()));
				}
				menuList.add(menu);
			}
		}
		return menuList;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Item> getItems(Document doc, String ref) {
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("//item[@name='"+ref+"']");
		List<Item> itemList = new ArrayList<Item>();
		if(null!=node) {
			Item item = new Item();
			item.setName(((Element)node).attributeValue("name"));
			item.setLabel(((Element)node).attributeValue("label"));
			item.setUrl(((Element)node).elementTextTrim("url"));
			
			List<com.kpleasing.crm.ejb.pojo.Security> securityList = new ArrayList<com.kpleasing.crm.ejb.pojo.Security>();
			List<Element> eSecuritys = ((Element)node).element("security").elements();
			for(Element eSecurity : eSecuritys) {
				com.kpleasing.crm.ejb.pojo.Security security = new com.kpleasing.crm.ejb.pojo.Security();
				security.setRuleCode(eSecurity.elementTextTrim("rule-code"));
				securityList.add(security);
			}
			item.setSecuritys(securityList);
			itemList.add(item);
		}
		return itemList;
	}


	/**
	 * 是否是子菜单
	 * @param eMenu
	 * @param subMenus
	 * @return
	 */
	private boolean isSubMenu(Element eMenu, List<Node> subMenus) {
		for(Node subMenu : subMenus) {
			if(eMenu.attributeValue("name").equals(subMenu.getText())) return true;
		}
		return false;
	}
	

	/**
	 * 当前菜单子菜单
	 * @param eMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Menu> getSubMenus(Document doc, Element eMenu) {
		Element root = doc.getRootElement();
		
		List<Menu> subMenuList = new ArrayList<Menu>();
		List<Element> eSubMenus = eMenu.elements("sub-menu");
		for(Element eSubMenu : eSubMenus) {
			Node node = root.selectSingleNode("//menu[@name='"+eSubMenu.getTextTrim()+"']");
			if(null!=node) {
				Menu menu = new Menu();
				menu.setName(((Element)node).attributeValue("name"));
				menu.setLabel(((Element)node).attributeValue("label"));
				menu.setRef(((Element)node).attributeValue("ref"));
				if(StringUtils.isBlank(menu.getRef())) {
					menu.setSub_menus(getSubMenus(doc, (Element)node));
					menu.setItems(getItems(doc, (Element)node));
				} else {
					menu.setItems(getItems(doc, menu.getRef()));
				}
				subMenuList.add(menu);
			}
		}
		return subMenuList;
	}


	/**
	 * 菜单条目-叶子节点
	 * @param eMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Item> getItems(Document doc, Element eMenu) {
		Element root = doc.getRootElement();
		
		List<Item> itemList = new ArrayList<Item>();
		List<Element> eItems = eMenu.elements("item");
		for(Element eItem : eItems) {
			Node node = root.selectSingleNode("//item[@name='"+eItem.getTextTrim()+"']");
			if(null!=node) {
				Item item = new Item();
				item.setName(((Element)node).attributeValue("name"));
				item.setLabel(((Element)node).attributeValue("label"));
				item.setUrl(((Element)node).elementTextTrim("url"));
				
				List<com.kpleasing.crm.ejb.pojo.Security> securityList = new ArrayList<com.kpleasing.crm.ejb.pojo.Security>();
				List<Element> eSecuritys = ((Element)node).element("security").elements();
				for(Element eSecurity : eSecuritys) {
					com.kpleasing.crm.ejb.pojo.Security security = new com.kpleasing.crm.ejb.pojo.Security();
					security.setRuleCode(eSecurity.elementTextTrim("rule-code"));
					securityList.add(security);
				}
				item.setSecuritys(securityList);
				itemList.add(item);
			}
		}
		return itemList;
	}


	/**
	 * 树形一级菜单
	 * @param menus
	 * @return
	 */
	public String getJsonMenuTree(List<Menu> menus) {
		if(menus.isEmpty()) { return ""; }
		
		int index = 0;
		StringBuilder rtnJson = new StringBuilder();
		for(Menu menu : menus) {
			if(index != 0) rtnJson.append(","); 
			if(StringUtils.isBlank(menu.getRef())) {
				rtnJson.append("{\"id\":\"").append(menu.getName()).append("\",\"name\":\"").append(menu.getLabel()).append("\"");
				
				List<Menu> subMenuList = menu.getSub_menus();
				int i = 0;
				if((null!=subMenuList && subMenuList.size()>0) 
						|| (menu.getItems()!=null && menu.getItems().size()>0)) {
					rtnJson.append(",\"children\":["); 
					for(Menu subMenu : subMenuList) {
						rtnJson.append(getSubMenu(subMenu)); i++;
					}
					for(Item item : menu.getItems()) {
						if(i!=0) rtnJson.append(",");
						rtnJson.append("{\"id\":\"").append(item.getName()).append("\",\"name\":\"").append(item.getLabel()).append("\",")
								.append("\"url\":\"").append(item.getUrl()).append("\"}");
						i++;
					}
					rtnJson.append("]");
				}
				rtnJson.append("}");
			} else {
				List<Item> items = menu.getItems();
				rtnJson.append("{\"id\":\"").append(menu.getName()).append("\",\"name\":\"").append(menu.getLabel()).append("\",")
						.append("\"url\":\"").append(items.get(0).getUrl()).append("\"}");
			
			}
			index++;
		}
		return rtnJson.toString();
	}
	
	
	/**
	 * 子菜单
	 * @param menu
	 * @return
	 */
	private String getSubMenu(Menu menu) {
		StringBuilder subJsonMenu = new StringBuilder();
		subJsonMenu.append("{\"id\":\"").append(menu.getName()).append("\",\"name\":\"").append(menu.getLabel()).append("\"");
		if(menu.getSub_menus()!=null && menu.getSub_menus().size()>0 
				|| menu.getItems()!=null && menu.getItems().size()>0) {
			subJsonMenu.append(",\"children\":[");
			int i = 0;
			for(Menu sub : menu.getSub_menus()) {
				subJsonMenu.append(getSubMenu(sub)); i++;
			}
			
			for(Item item : menu.getItems()) {
				if(i!=0) subJsonMenu.append(",");
				subJsonMenu.append("{\"id\":\"").append(item.getName()).append("\",\"name\":\"").append(item.getLabel()).append("\",")
					.append("\"url\":\"").append(item.getUrl()).append("\"}");
				i++;
			}
			subJsonMenu.append("]");
		}
		subJsonMenu.append("}");
		return subJsonMenu.toString();
	}
}
