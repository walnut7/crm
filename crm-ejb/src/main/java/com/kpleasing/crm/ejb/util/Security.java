package com.kpleasing.crm.ejb.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

/**
 * User: rizenguo Date: 2014/10/29 Time: 15:23
 */
public class Security {
	
	private static Logger logger = Logger.getLogger(Security.class);
	
	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 *
	 * @param responseString
	 *            API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws DocumentException 
	 * @throws IllegalAccessException 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean checkIsSignValidFromResponseString(String responseString, String key) throws DocumentException, IllegalAccessException {

		Map<String, String> map = XMLHelper.getMapFromXML(responseString);

		String signFromAPIResponse = map.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		String signForAPIResponse = Security.getSign(map, key);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
			return false;
		}
		logger.info("恭喜，API返回的数据签名验证通过!!!");
		return true;
	}
	
	public static String getSign(Map<String, Object> map, String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		logger.info("Sign Before MD5:" + result);
		result = MD5Encode(result).toUpperCase();
		logger.info("Sign Result:" + result);

		return result;
	}
	
	
	/**
	 * 签名算法
	 *
	 * @param o
	 *            要参与签名的数据对象
	 * @return 签名
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException
	 */
	public static String getSign(Object o, String key) throws IllegalArgumentException, IllegalAccessException {
		String result = getSignContent(o);
		result += "key=" + key;
		logger.info("Sign Before MD5:" + result);
		result = MD5Encode(result).toUpperCase();
		logger.info("Sign Result:" + result);
		return result;
	}
	
	
	/**
	 * 签名内容
	 * @param o
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static String getSignContent(Object o) throws IllegalArgumentException, IllegalAccessException {
		List<String> list = new ArrayList<String>();
		Class<? extends Object> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			
			Class<?> fieldClazz = field.getType(); 
			
			if(fieldClazz.isAssignableFrom(java.util.List.class)) {
				List<?> objs = (List<?>)field.get(o);
				
				if(objs != null) {
					for(Object obj : objs) {
						String content = getSignContent(obj);
						list.add(field.getName()+"={"+content.substring(0, content.length()-1)+"}&");
		            }
				}
			} else if(fieldClazz.isAssignableFrom(Logger.class) || fieldClazz.isPrimitive()) {
				continue;
			} else {
				if (field.get(o) != null && !"".equals(field.get(o))) {
					list.add(field.getName() + "=" + field.get(o) + "&");
				}
			}
		}
		addFatherClassProperty(o, list);
		
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		return sb.toString();
	}
	
	
	/**
	 * 添加父类属性值
	 * @param o
	 * @param list
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void addFatherClassProperty(Object o, List<String> list) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> cls = o.getClass().getSuperclass();
		Field[] Fields = cls.getDeclaredFields();
		for (Field f : Fields) {
			f.setAccessible(true);
			if (f.get(o) != null && f.get(o) != "") {
				if(!(f.get(o) instanceof Logger || f.getName().equals("serialVersionUID"))) {
					list.add(f.getName() + "=" + f.get(o) + "&");
				}
			}
		}
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 *
	 * @param responseString
	 *            API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws DocumentException 
	 * @throws IllegalAccessException 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String getSignFromResponseString(String responseString, String key) throws DocumentException, IllegalAccessException {
		Map<String, String> map = XMLHelper.getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return Security.getSign(map, key);
	}
	
	// MD5 
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};
	
	
	public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }
	
	
	private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
	
	
	public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
