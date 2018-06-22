package com.kpleasing.crm.ejb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyMMddHHmmss");
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HHmmss");
	
	
	/**
	 * 当前日历，这里用中国时间表示
	 *
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}
	
	/**
	 * 当前日期
	 *
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return getCalendar().getTime();
	}
	
	
	/**
	 * 日期转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getCurrentDate(SimpleDateFormat date_sdf) {
		Date date = getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	
	/**
	 * 字符串转换成日期.
	 * 
	 * @param str
	 *            yyMMddHHmmss
	 * @param
	 * @return
	 */
	public static Date str2Date(String str) {
		if (null == str || "".equals(str) || "null".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = yyyyMMddHHmmss.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str) || "null".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	
	
	/**
	 * 比较两个时间的大小
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDateDiff(Date date1, Date date2) {
		long d1 = date1.getTime();
		long d2 = date2.getTime();
		return d1 > d2 ? 1 : ((d1 == d2) ? 0 : -1);
	}
	
	/**
	 * 获得指定日期以后n天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAfterDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
}
