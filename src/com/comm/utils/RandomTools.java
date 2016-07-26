package com.comm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


/** 
* 2015-9-21 
* DES:生成随机数工具类
* author:JiBaoLe 
*/ 

public class RandomTools {

	 public RandomTools(){
	    }
	 
	 /** 
	 * 2015-9-21 
	 * DES:生成6位数字、大小写字母组合不重复的6位验证码
	 * author:JiBaoLe 
	 */ 	 
	public static String generateCode() {
		// 前面基本和你自己的一致
		final Random r = new Random();
		final char[] arr = new char[62];
		for (int q = 0; q < 26; q++) {
			arr[q] = (char) ('A' + q);
		}
		for (int q = 26; q < 52; q++) {
			arr[q] = (char) ('a' + q - 26);
		}
		for (int q = 52; q < arr.length; q++) {
			arr[q] = (char) ('0' + q - 52);
		}
		// 后面我们这次不用复杂的数据结构
		final char[] code = new char[6];
		for (int i = 0; i < code.length; i++) {
			int index;
			do {
				final int rnd = r.nextInt();
				index = (rnd < 0 ? -rnd : rnd) % arr.length;
				// 如果碰到'\0'证明取到了上次相同的元素，应当重新取，直到不重复
			} while (arr[index] == '\0');
			code[i] = arr[index];
			// 使用过一次设定的'\0'，下次就不能用了
			arr[index] = '\0';
		}
		return String.valueOf(code);
	}
	
	 /** 
		 * 2015-9-21 
		 * DES:批量生成6位数字、大小写字母组合不重复的6位验证码
		 * author:JiBaoLe 
		 */
	public  static Set<String>  getGenerateCode(int num){
		Set<String> set = new HashSet<String>(); 
		for(int i=0;i<num;i++){
			set.add(generateCode());
		}
		return set;
	}
	
	public static String getCurrentTime(){
    	DateFormat format=new SimpleDateFormat("yyyyMMdd");
    	String time=format.format(new Date()); 
		return time;
		
	}
	
	public static void main(String[] args) {
		//测试生成6位随机码
		Set hh=getGenerateCode(200000);
		
		/*Iterator<String> it = hh.iterator();
		while (it.hasNext()) {
		  String str = it.next();
		  System.out.println(str);
		}*/
		System.out.println(hh.size());
      System.out.println(getCurrentTime());	
}
}
