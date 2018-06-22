package com.kpleasing.crm.ejb.util;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtil {
	
	
	/**
	 * 32 位UUID全球唯一码
	 * @return
	 */
	public static String getSerialNo32() {
		return UUID.randomUUID().toString().replace("-","");
	}
	
	
	/**
	 * 年月日時分秒6位随机数
	 * yyyyMMddHHmmssxxxxxx
	 * @return
	 */
	public static String getDateSerialNo6() {
		return DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss) + RandomStringUtils.randomNumeric(6);
	}

}
