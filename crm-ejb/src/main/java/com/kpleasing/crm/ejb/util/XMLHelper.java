package com.kpleasing.crm.ejb.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

public class XMLHelper {

	/**
	 *  Map转换成 Bean
	 * @param xml
	 * @param o
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DocumentException
	 */
	public static void getBeanFromXML(String xml, Object o) throws IllegalAccessException, InvocationTargetException, DocumentException  {
		BeanUtils.populate(o, getMapFromXML(xml));
	}
	
	
	/**
	 * XML文档转换成Map键值对
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String, String> getMapFromXML(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
        Map<String, String> map = new HashMap<String, String>();
        getIterationElement(doc.getRootElement(), map);
        return map;
	}
	
	
	
	/**
	 * XML多值转换成List<Map<String,String>>
	 * @param strXml
	 * @param nsMap
	 * @param xpathExpre
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> parseMultNodesXml(String strXml, Map<String, String> nsMap, String xpathExpre) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXml);
        XPath xpExpre = doc.createXPath(xpathExpre);
        xpExpre.setNamespaceURIs(nsMap);
        
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        List<Node> nodes = xpExpre.selectNodes(doc);
        
        for(Node node : nodes) {
        	Map<String, String> map = new HashMap<String, String>();
        	getIterationElement((Element)node, map);
        	list.add(map);
        }
		return list;
	}
	
	
	/**
	 * XML多值转换成List<Map<String,String>>
	 * @param strXml
	 * @param xpathExpre
	 * @return
	 * @throws DocumentException
	 */
	public static List<Map<String, String>> parseMultNodesXml(String strXml, String xpathExpre) throws DocumentException {
		return parseMultNodesXml(strXml, xpathExpre, true);
	}
	
	
	/**
	 * 
	 * @param strXml
	 * @param xpathExpre
	 * @param loop  true：返回所有子节点, false:返回当前节点
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> parseMultNodesXml(String strXml, String xpathExpre, boolean loop) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXml);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Node> nodes = doc.selectNodes(xpathExpre);
		for(Node node : nodes) {
        	Map<String, String> map = new HashMap<String, String>();
        	if(loop) {
        		getIterationElement((Element)node, map);
        	} else {
        		getCurrentElement((Element)node, map);
        	}
        	list.add(map);
        }
		return list;
	}
	
	
	/**
	 * XML单值转换成对象 
	 * @param <T>
	 * @param strXml
	 * @param nsMap
	 * @param xpathExpre
	 * @param o
	 * @throws DocumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> void parseSingleNodeXml(String strXml, Map<String, String> nsMap, String xpathExpre, T o) throws DocumentException, IllegalAccessException, InvocationTargetException {
		Map<String,String> map = parseSingleNodeXml(strXml, nsMap, xpathExpre);
		if(map != null) {
			BeanUtils.populate(o, map);
		}
	}
	
	
	/**
	 * XML单值文档转换成Map<String,String>
	 * @param strXml
	 * @param nsMap
	 * @param xpathExpre
	 * @return
	 * @throws DocumentException
	 */
	public static Map<String,String> parseSingleNodeXml(String strXml, Map<String, String> nsMap, String xpathExpre) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXml);
        XPath xpExpre = doc.createXPath(xpathExpre);
        xpExpre.setNamespaceURIs(nsMap);
        
        Node node = xpExpre.selectSingleNode(doc);
        if (node !=null) {
	        Map<String, String> map = new HashMap<String, String>();
	        getIterationElement((Element)node, map);
			return map;
        } else {
        	return null;
        }
	}
	
	
	
	/**
	 * 迭代遍历XML节点
	 * @param e
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	private static void getIterationElement(Element e, Map<String, String> m) {
		List<Element> elements = e.elements();
		for(Element element : elements) {
			if(StringUtils.isNotBlank(element.getTextTrim())) {
				m.put(element.getName(), element.getTextTrim());
			}
			getIterationElement(element, m);
		}
	}
	
	/**
	 * 获取当前XML节点
	 * @param e
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	private static void getCurrentElement(Element e, Map<String, String> m) {
		List<Element> elements = e.elements();
		for(Element element : elements) {
			if(StringUtils.isNotBlank(element.getTextTrim())) {
				m.put(element.getName(), element.getTextTrim());
			}
		}
	}
	
	
	/**
	 * 根据节点名取第一个节点值
	 * @param xml
	 * @param tagName
	 * @return
	 * @throws DocumentException
	 */
	public static String getNodeTextByTagName(String strXml, Map<String, String> nsMap, String xpathExpre) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXml);
        XPath xpExpre = doc.createXPath(xpathExpre);
        xpExpre.setNamespaceURIs(nsMap);
        return xpExpre.selectSingleNode(doc).getText().trim();
	}
	
	
	/**
	 * 根据节点名取第一个节点值
	 * @param xml
	 * @param tagName
	 * @return
	 * @throws DocumentException
	 */
	public static String getNodeTextByTagName(String xml, String tagName) throws DocumentException {
		return DocumentHelper.parseText(xml).selectSingleNode("//"+tagName).getText().trim();
	}
	
	
	/**
	 * 实体转换成XML字符串
	 * @param o
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static String getXMLFromBean(Object o) throws IllegalArgumentException, IllegalAccessException {
		return getXMLFromBean(o, "");
	}
	
	
	/**
	 * 带命名空间的实体转换成XML字符串
	 * @param o
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static String getXMLFromBean(Object o, String nsPrefix) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder strXmlBody = new StringBuilder();
		Class<? extends Object> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			
			Class<?> fieldClazz = field.getType();
			if(fieldClazz.isAssignableFrom(java.util.List.class)) {
				continue;
			} else if(fieldClazz.isAssignableFrom(Logger.class) || fieldClazz.isPrimitive()) {
				continue;
			} else {
				if (field.get(o) != null && !"".equals(field.get(o)) && !"null".equals(field.get(o))) {
					strXmlBody.append("<").append(nsPrefix).append(field.getName()).append("><![CDATA[").append(field.get(o)).append("]]></").append(nsPrefix).append(field.getName()).append(">");
				}
			}
		}
		return strXmlBody.toString();
	}
		
}
