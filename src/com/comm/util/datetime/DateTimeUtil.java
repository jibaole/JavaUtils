package com.comm.util.datetime;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;



public class DateTimeUtil {
  /**
   * Return current datetime string.
   * @return current datetime, pattern: "yyyy-MM-dd HH:mm:ss".
   */
  public static String getDateTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dt = sdf.format(new Date());
    return dt;
  }
  
  
  
  /**
   * Return current datetime string.
   * @param pattern format pattern
   * @return current datetime
   */
  public static String getDateTime(String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String dt = sdf.format(new Date());
    return dt;
  }

  /**
   * Return short format datetime string.
   * @param date java.util.Date
   * @return short format datetime
   */
  public static String strForDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    return sdf.format(date);
  }
  
  /**
   * 生日日期格式化
   * @param date
   * @return MMdd
   */
  public static String birthdayFmt(Date date){
	  SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
	    return sdf.format(date);
  }
  
  /**
   * 获取日期格式如：2010年1月1日
   * @return
   */
  public static String getDateToString(){
	  StringBuffer str = new StringBuffer();
	  str.append(getCurrentYear()+"年"+getCurrentMonth()+"月"+getCurrentDay(getShortCurrentDate()+"日"));
	  return str.toString();
  }
  
  /**
   * Return short format datetime string.
   * @param date java.util.Date
   * @return short format datetime
   */
  public static String shortFmt(Date date) {
  	if(date==null){
  	 return "";
  	}
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  public static String shortMonthFmt(Date date) {
	  	if(date==null){
	  	 return "";
	  	}
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    return sdf.format(date);
  }
  
  public static String LongFmt(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return sdf.format(date);
  }


  public static String getTime(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date);
  }

  
  public static String getOrderTimeForBill99(Date date) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	    return sdf.format(date);
	  }
  
  
  /**
   * Parse a datetime string.
   * @param param datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
   * @return java.util.Date
   */
  public static Date parse(String param) {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    try {
      date = sdf.parse(param);
    }
    catch (ParseException ex) {
    }
    return date;
  }

  
  /**
   * Parse a datetime string.
   * @param param datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
   * @return java.util.Date
   */
  public static Date parseLong(Long param) {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    try {
      date = sdf.parse(sdf.format(new Date(param)));
    }
    catch (ParseException ex) {
    }
    return date;
  }
  
  
  /**
   * Parse a datetime string.
   * @param param datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
   * @return java.util.Date
   */
  public static Date getShortDate(String param) {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      date = sdf.parse(param);
    }
    catch (ParseException ex) {
    }
    return date;
  }
  /**
    * Parse a datetime string.
    * @param param datetime string, pattern: "yyyy-MM-dd HH:mm:ss".
    * @return java.util.Date
    */
   public static Date getDateFomate(String param) {
     Date date = null;
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     try {
       date = sdf.parse(param);
     }
     catch (ParseException ex) {
     }
     return date;
  }

  /**
   * 返回当前年份
   * @return String
   */
  public static String getCurrentYear() {
    Calendar cal = Calendar.getInstance();
    return String.valueOf(cal.get(Calendar.YEAR));
  }

  /**
   * 返回当前月份
   * @return String
   */
  public static String getCurrentMonth() {
    Calendar cal = Calendar.getInstance();
    int nMonth = cal.get(Calendar.MONTH) + 1;
    if (nMonth < 10) {
      return "0" + String.valueOf(nMonth);
    }
    return String.valueOf(nMonth);
  }
  /**
   * 返回上一月
   * @return String
   */
  public static String getPreviousMonth() {
    Calendar cal = Calendar.getInstance();
    int nMonth = cal.get(Calendar.MONTH);
    if (nMonth < 10) {
      if(nMonth==0){                 //如果是1月，则返回上年12月
        return "12";
      }
      return "0" + String.valueOf(nMonth);
    }
    return String.valueOf(nMonth);
  }


  /**
   * 返回下一月
   * @return String
   */
  public static String getNextMonth() {
    Calendar cal = Calendar.getInstance();
    int nMonth = cal.get(Calendar.MONTH) + 2;
    if (nMonth < 10) {
      return "0" + String.valueOf(nMonth);
    }
    return String.valueOf(nMonth);
  }

  /**
   * 返回下一年份
   * @return String
   */
  public static String getNextYear() {
    Calendar cal = Calendar.getInstance();
    return String.valueOf(cal.get(Calendar.YEAR) + 1);
  }

  /**返回上一年份 */
  public static String getLastYear() {
    Calendar cal = Calendar.getInstance();
    return String.valueOf(cal.get(Calendar.YEAR) - 1);
  }

  /**
   * 获得日期字符串
   * @return
   */
  public static String getDateString() {
    java.util.Date dToday = new java.util.Date();
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
        "yyyyMMddHHmmss");
    return formatter.format(dToday);
  }

  /**
   * 解析日期类型,获取当前月份天数
   * @param strDate String:2005-05-05
   * @return int
   * @author:gjw
   * @date:2005-07-11
   */
  public static int getDayCount(String strDate) {
    int nYear = 0;
    int nMonth = 0;
    int[] nDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //默认非润年
    if (strDate.length() > 7) {
      nYear = Integer.parseInt(strDate.substring(0, 4));
      nMonth = Integer.parseInt(strDate.substring(5, 7));
    }
    if ( (nYear % 4 == 0 && nYear % 100 != 0) || (nYear % 400 == 0)) { //判断是否是闰年
      nDays[1] = 29;
    }
    for (int i = 0; i < nDays.length; i++) {
      if (nMonth == i + 1) {
        return nDays[i];
      }
    }
    return 0;
  }

  /**
   * 获取当前天数
   * @param strDate String:2005-05-05
   * @return int
   * @author:gjw
   * @date:2005-07-11
   */
  public static int getCurrentDay(String strDate) {
    int nDay = 0;
    if (strDate.length() >=10) {
      nDay = Integer.parseInt(strDate.substring(8, 10));
    }
    return nDay;
  }
 
  /**
   * 返回当前日期
   * @return  String
   * @author  hechangcheng
   */
  public static String getShortCurrentDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dt = sdf.format(new Date());
    return dt;
  }

  /**返回比当前日期早七天的日期 */
  public static String getDay() {
      Date ndate = new Date();
      /**七天共 604800000 毫秒*/
      long currentdate = ndate.getTime() - 604800000 ;
      Date dd = new Date(currentdate);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String dt = sdf.format(dd);
      return dt ;
  }
 public static String getbeforeDay(int n){
    Date ndate =new Date();
    //n天共n*24*60*60*1000毫秒
    long currentdate=ndate.getTime()-n*24*60*60*1000;
    Date dd = new Date(currentdate);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dt = sdf.format(dd);
    return dt ;
 }
 
 //dateStr的格式:yyyymmdd
 public static String getbeforeDay(String dateStr ,int n){
	    Date ndate =getDateSFomate(dateStr);
	    //n天共n*24*60*60*1000毫秒
	    long currentdate=ndate.getTime()-n*24*60*60*1000;
	    Date dd = new Date(currentdate);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String dt = sdf.format(dd);
	    return dt ;
	 }
  /**
   * 返回比当前日早一天的日期
   * @return String
   */
  public static String getPreviouseDate(){
      Date ndate = new Date();
      /**一天共86400 毫秒*/
      long currentdate = ndate.getTime() - 86400 ;
      Date dd = new Date(currentdate);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String dt = sdf.format(dd);
      return dt ;

  }

public static Date getDateSFomate(String param) {
   Date date = null;
   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
   try {
    date = sdf.parse(param);
   }
   catch (ParseException ex) {
   }
    return date;
 }


public static Date getDateSFomateShortMonth(String param) {
	   Date date = null;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	   try {
	    date = sdf.parse(param);
	   }
	   catch (ParseException ex) {
	   }
	    return date;
}



public static String getDateSFomateMonth(String param) {
	   Date date = null;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	   try {
	    date = sdf.parse(param);
	   }
	   catch (ParseException ex) {
	   }
	    return shortFmt(getFirstDay(shortFmt(date)));
	 }

	 /**
	  * 当年
	  * @return
	  */
	public static int currentYear(){
			return Calendar.getInstance().get(Calendar.YEAR);
		}
	
	/**
	 * 当月
	 * @return
	 */
	public static int currentMonth(){
			return Calendar.getInstance().get(Calendar.MONTH) + 1;
		}

/**
 * 返回当前月最后一天的日期
 * @return date
 * parameter yyyy-mm-dd
 * wlb
 */
public static Date getLastDay(String dateDay)
{
	Calendar   c   =   Calendar.getInstance();
	Date date = null;
	dateDay=dateDay+" 00:00:00";
	c.setTime(getDateFomate(dateDay));
	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(c.DAY_OF_MONTH));
	date=c.getTime();
	return date;
}
/**
 * 返回当前月第一天的日期
 * @return date
 * parameter yyyy-mm-dd
 * wlb
 */
public static Date getFirstDay(String dateDay)
{
	Calendar   c   =   Calendar.getInstance();
	Date date = null;
	dateDay=dateDay+" 00:00:00";
	c.setTime(getDateFomate(dateDay));
	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(c.DAY_OF_MONTH));
	date=c.getTime();
	return date;
}
public static String getCurrentFirstDay()
{
	String dateDay=getDateTime();
	Calendar   c   =   Calendar.getInstance();
	Date date = null;
	c.setTime(getDateFomate(dateDay));
	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(c.DAY_OF_MONTH));
	date=c.getTime();
	return shortFmt(date);
}

public static String getCurrentLastDay()
{
	String dateDay=getDateTime();
	Calendar   c   =   Calendar.getInstance();
	Date date = null;
	c.setTime(getDateFomate(dateDay));
	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(c.DAY_OF_MONTH));
	date=c.getTime();
	return shortFmt(date);
}
public static Date getFirstDay(Date dateDay)
{
	return getFirstDay(shortFmt(dateDay));
}
/**
 * 根据年月获得，指定年月的最后一天
 * @param yy_mm 例2007-11
 * @return 返回指定参数＋最后一天＝20071130
 * @author LiangYanPeng
 */
public static String getLastDayByYYMM(String yy_mm) {
	return DateTimeUtil.strForDate(DateTimeUtil.getLastDay(yy_mm + "-01"));
}

public static String getCurrentDay() {
	Calendar cal = Calendar.getInstance();
	int nMonth = cal.get(Calendar.DATE) ;
	if (nMonth < 10) {
		return "0" + String.valueOf(nMonth);
	}
	return String.valueOf(nMonth);
}

/**
 * 用于获得指定格式的当前日期 
 * @param format 字符串时间格式  eg:yyyy-MM-dd hh:mm:ss
 * @return String 字符串时间
 */
public static String getCurrentDate(String format) {
	String currentDate = "";
	try {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat;
		Date date = calendar.getTime();
		simpleDateFormat = new SimpleDateFormat(format);
		currentDate = simpleDateFormat.format(date);
	} catch (Exception e) {
		currentDate = "";
	}
	return currentDate;

}
/**
 * 将字符串日期转换成长整类型日期
 * @param strDate 字符串型时间
 * @return long 长整型时间
 */
public static long formatDateStringToInt(String strDate) {
	SimpleDateFormat format;
	Date time;
	if (strDate.trim().equals(""))
		return -1;
	String strAry[] = strDate.split(":");
	if (strAry.length > 1)
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale
				.getDefault());
	else
		format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	try {
		time = format.parse(strDate + ":00");
		return time.getTime() / 1000;
	} catch (Exception e) {
		return -1;
	}
}
/**
 * 对年进行加减运算
 * @param args
 * num 传入正负数进行加减
 */
public static String operationDate(String format,int num){
	SimpleDateFormat sdf=new SimpleDateFormat(format);
    String str= getCurrentDate(format);
    Date dt;
    String reStr = "";
	try {
		dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,num);
        Date dt1=rightNow.getTime();
        reStr = sdf.format(dt1);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return reStr;
}
/**
 * 对天进行加减运算
 * @param format
 * @param num
 * @return
 */
public static String operationDateDay(String format,int num){
	SimpleDateFormat sdf=new SimpleDateFormat(format);
    String str= getCurrentDate(format);
    Date dt;
    String reStr = "";
	try {
		dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_WEEK,num);
        Date dt1=rightNow.getTime();
        reStr = sdf.format(dt1);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return reStr;
}

	/**
	 * 整数秒 转换为HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String intToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
						+ unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
	//测试主方法main()
	public static void main(String[] args) {

		System.out.println(DateTimeUtil.getDateTime());
		//System.out.println(DateTimeUtil.getDayCount("2016-02-07"));
		
		//System.out.println(getShortCurrentDate());

	}

}
