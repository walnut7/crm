package com.kpleasing.crm.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonUtil {

	private static ObjectMapper mapper;
	
	private static JsonConfig jsonConfig = null;
	
	public static synchronized JsonConfig getJsonConfig() {
		if(null == jsonConfig) {
			jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
			return jsonConfig;
		}
		return jsonConfig;
	}
	
	/**
	 * 获取ObjectMapper实例
	 * @param createNew 方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {   
        if (createNew) {   
            return new ObjectMapper();   
        } else if (mapper == null) {   
            mapper = new ObjectMapper();   
        }   
        return mapper;   
    }
	
	
	
	/**
	 * 将java对象转换成json字符串
	 * @param obj 准备转换的对象
	 * @return json字符串
	 * @throws Exception 
	 */
	public static String beanToJson(Object obj) throws Exception {
		try {
			ObjectMapper objectMapper = getMapperInstance(false);
			String json =objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	/**
	 * 将java对象转换成json字符串
	 * @param obj 准备转换的对象
	 * @param createNew ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return json字符串
	 * @throws Exception
	 */
	public static String beanToJson(Object obj,Boolean createNew) throws Exception {
		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			String json =objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}
	
	/**
	 * 将json字符串转换成java对象
	 * @param json 准备转换的json字符串
	 * @param cls  准备转换的类
	 * @return 
	 * @throws Exception 
	 */
	public static Object jsonToBean(String json, Class<?> cls) throws Exception {
		try {
		ObjectMapper objectMapper = getMapperInstance(false);
		Object vo = objectMapper.readValue(json, cls);
		return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}	
	}
	
	/**
	 * 将json字符串转换成java对象
	 * @param json 准备转换的json字符串
	 * @param cls  准备转换的类
	 * @param createNew ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBean(String json, Class<?> cls,Boolean createNew) throws Exception {
		try {
		ObjectMapper objectMapper = getMapperInstance(createNew);
		Object vo = objectMapper.readValue(json, cls);
		return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}	
	}
	
	
	public static String JSON_Bean2String(Object ob, JsonConfig jsonConfig) {
        JSONObject jsonObj = JSONObject.fromObject(ob, jsonConfig);
        return jsonObj.toString();
    }
	
	public static String JSON_Bean2String(Object ob) {
		return JSON_Bean2String(ob, getJsonConfig());
    }
	
	public static String JSON_List2String(Object ob, JsonConfig jsonConfig) throws JSONException {
        JSONArray jsonArray = JSONArray.fromObject(ob, jsonConfig);
        return jsonArray.toString();
    }

    public static String JSON_List2String(Object ob) throws JSONException {
        return JSON_List2String(ob, getJsonConfig());
    }

    public static Object JSON_String2Bean(String s, Class bean) throws JSONException, IllegalAccessException, InvocationTargetException {
        JSONObject jsonObj = JSONObject.fromObject(s);
        return JSONObject.toBean(jsonObj, bean);
    }

    public static List JSON_String2List(String s, Class bean) {
        JSONArray jsonArray = JSONArray.fromObject(s);
        List list = (List) JSONArray.toCollection(jsonArray, bean);
        return list;
    }

    
    /**
     * 
     * @author howard.huang
     *
     */
    static class JsonDateValueProcessor implements JsonValueProcessor {
    	private String format ="yyyy-MM-dd";
    	
    	public JsonDateValueProcessor() {
    		super();
    	}
    	
    	public JsonDateValueProcessor(String patter) {
    		this.format = patter;
    	}
    	
    	@Override
    	public Object processArrayValue(Object value, JsonConfig config) {
    		return process(value);
    	}

    	@Override
    	public Object processObjectValue(String key, Object value, JsonConfig config) {
    		return process(value);
    	}
    	
    	
    	private Object process(Object value) {
            if(value instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.UK);  
                return sdf.format(value);
            }
            return value == null ? "" : value.toString();  
        } 
    }
}
