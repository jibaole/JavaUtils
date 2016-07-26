package com.comm.util.string;

import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StringTool {

	public static String replace(String mobiles, String srcString,
			String replaceString) {

		return mobiles.replace(srcString, replaceString);
	}

	public static boolean isMobile(String mobiles) {

		Pattern p = Pattern.compile("^((1))\\d{10}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches() + "---");

		return m.matches();
	}

	public static boolean isEmail(String email) {

		Pattern pattern = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");

		/*
		 * Pattern pattern = Pattern
		 * .compile("\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*");
		 */

		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static String getMapString(Map paramMap, String mapStr) {

		String returnString = (paramMap!=null && paramMap.get(mapStr) != null) ? paramMap.get(
				mapStr).toString() : "";

		return returnString;
	}

	/**
	 * 获取分割后的字符串
	 * 
	 * @param arrString
	 * @param split
	 * @param index
	 *            0 或1
	 * @return
	 */
	public static String arrValue(String arrString, String split, int index) {
		String v = arrString;
		int i = arrString.indexOf(split);
		if (i > -1) {
			int len = arrString.length();
			if (index == 0) {
				v = arrString.substring(0, i);
			} else if (index == 1) {
				v = arrString.substring(i + 1, len);
			}
		}
		return v;
	}

	public static Long convertObjectToLong(Object obj) {
		return Long.valueOf(String.valueOf(obj));
	}

	public static String convertObjectToString(Object obj) {
		return String.valueOf(obj);
	}

	public static boolean isLongEmpty(Long l) {

		if (null != null && l > 0l) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isStringEmpty(String str) {

		return StringUtils.isEmpty(str);
	}
/*
 * DES:切除字符串，后缀内容
 */
	public static StringBuffer splitTail(StringBuffer str) {

		int length = str.length();
		System.out.println("length=" + length);
		System.out.println("ad=" + str.lastIndexOf(","));

		if (length>0 && str.lastIndexOf(",") + 1 == length) {
			return str.delete(length - 1, length);
		} else {
			return str;
		}

	}

	public static void main(String[] args) {

		StringBuffer str = new StringBuffer("");
		str.append("1313,213,1312");
//
//		System.out.println(StringTool.splitTail(str));
//
//		System.out.println("email" + StringTool.isEmail("zybyjy@sohu.com"));
//		System.out.println(StringTool.isMobile("1333011111111"));

		/*
		 * String ss = "','"; // "<p>ad,'{[</p>"
		 * System.out.println("escapeHtml=" + StringTool.escapeJavaScript(ss));
		 * System.out .println("unescapeHtml=" +
		 * StringTool.unescapeJavaScript(StringTool .escapeJavaScript(ss)));
		 */

		/*
		 * System.out.println(generateString(15));
		 * System.out.println(generateMixString(15));
		 * System.out.println(generateLowerString(15));
		 * System.out.println(generateUpperString(15));
		 * System.out.println(generateZeroString(15));
		 * System.out.println(toFixdLengthString(123, 15));
		 * System.out.println(toFixdLengthString(123L, 15));
		 */

		// System.out.println(generateStringMixUpperAndNumber(12));

		// System.out.println("aaa="+StringUtil.toUnicode("测试订单"));
		// System.out.println(StringTool.toUnicode("测试订单") + "=="+ StringTool.ascii2Native(StringTool.toUnicode("测试订单")));
         System.out.println(StringTool.removeDuplicate("hhhdwqh海底我起床就我请但为何去哦i"));
	}

	public static String toUnicode(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) <= 256) {
				sb.append("\\u00");
			} else {
				sb.append("\\u");
			}
			sb.append(Integer.toHexString(s.charAt(i)));
		}
		return sb.toString();
	}

	// unicode转为本地
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf("\\u");
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf("\\u", begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!"\\u".equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	public static String escapeJavaScript(String htmlString) {

		String aa = StringEscapeUtils.escapeJavaScript(htmlString);
		return aa;
	}

	public static String unescapeJavaScript(String htmlString) {

		String aa = StringEscapeUtils.unescapeJavaScript(htmlString);
		return aa;
	}
	/**
	 * object 转换成字符串
	 */
	public static String objectToString(Object obj){
		String str = "";
		if(obj!=null){
			str = String.valueOf(obj);
		}
		return str;
	}
	
	/**
	 * 字符串去重方法
	 */
	public static String removeDuplicate(String s){
		if(!isStringEmpty(s)){
			 String [] stringArr=s.split(",");
			 TreeSet<String> tr = new TreeSet<String>();
			 for(int i=0;i<stringArr.length;i++){
			// System.out.print(stringArr[i]+" ");
			 tr.add(stringArr[i]);
			 }
			 StringBuffer buf=new StringBuffer();
			 for(String str: tr){
				 buf.append(str);
				 buf.append(",");
			 }
			 buf=StringTool.splitTail(buf);
			return buf.toString(); 
		}else{
			return "";
		}
	}

}
