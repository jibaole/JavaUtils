package com.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 描        述：时间格式化
 * 创建时间：2016-7-26
 * @author Jibaole
 */
public class DateUtil
{

	public static String getCurrentTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = sdf.format(new Date());
		return dt;
	}
	
	public static String getCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf.format(new Date());
		return dt;
	}
	
	public static String getCurrentDate(String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dt = sdf.format(new Date());
		return dt;
	}
	
	public static String strForDate(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static String getDateString()
	{
		Date dToday = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(dToday);
	}


	public static Date parse(String param)
	{
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try
		{
			date = sdf.parse(param);
		} catch (ParseException localParseException)
		{
		}
		return date;
	}
	
	public static Date parse(String param,String pattern)
	{
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			date = sdf.parse(param);
		} catch (ParseException localParseException)
		{
		}
		return date;
	}

	public static String getCurrentYear()
	{
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	public static String getCurrentMonth()
	{
		Calendar cal = Calendar.getInstance();
		int nMonth = cal.get(Calendar.MONTH) + 1;
		if (nMonth < 10)
		{
			return "0" + String.valueOf(nMonth);
		}
		return String.valueOf(nMonth);
	}

	public static String getCurrentDay()
	{
		Calendar cal = Calendar.getInstance();
		int nMonth = cal.get(Calendar.DATE);
		if (nMonth < 10)
		{
			return "0" + String.valueOf(nMonth);
		}
		return String.valueOf(nMonth);
	}

	public static String getPreviousMonth()
	{
		Calendar cal = Calendar.getInstance();
		int nMonth = cal.get(2);
		if (nMonth < 10)
		{
			if (nMonth == 0)
			{
				return "12";
			}
			return "0" + String.valueOf(nMonth);
		}
		return String.valueOf(nMonth);
	}

	public static String getNextMonth()
	{
		Calendar cal = Calendar.getInstance();
		int nMonth = cal.get(2) + 2;
		if (nMonth < 10)
		{
			return "0" + String.valueOf(nMonth);
		}
		return String.valueOf(nMonth);
	}

	public static String getNextYear()
	{
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(1) + 1);
	}

	public static String getLastYear()
	{
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(1) - 1);
	}

	/**
	 * 得到时间字符串
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateToString(Date date,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		String dateString = formatter.format(date);
		return dateString;
	}
	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param min
	 * @return
	 */
	public static Date getMinBefore(Date d, int min) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - min);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	/**
	 * 得到n年后的时间
	 * 
	 * @param d
	 * @param year
	 * @return
	 */
	public static Date getYearAfter(Date d, int year) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
		return now.getTime();
	}
	
	public static void main(String[] args)
	{
//		 System.out.println(DateUtil.getCurrentTime());
//		 System.out.println(DateUtil.getCurrentDay());
//		 System.out.println(DateUtil.getCurrentYear());
//		 System.out.println(DateUtil.getCurrentMonth());
		 System.out.println(DateUtil.getDateToString(DateUtil.getMinBefore(new Date(), 60),"yyyy-MM-dd HH:mm:ss"));
		 System.out.println(getNextYear());
		 
		 Date dateBefore30=DateUtil.getMinBefore(new Date(), -1);
		 Date now = new Date();
			
		System.out.println(dateBefore30.before(now));
			
		
	}
}