package com.myutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
* 2015-12-4 
* DES:业务处理常用算法
* author:JiBaoLe 
*/ 
public class algorithmUtils {

	/*
	 * 根据传入的字符串，按规则返回指定分组好List串
	 */
	
	public static List getGroupString(String str){
		String[] intArrary = str.split(",");
		List list=new ArrayList();
		int n = 0;
		int x = 5;//自己控制分成几个一组
		
		if(intArrary.length%x == 0){
			n = intArrary.length/x;
		}else{
			n = intArrary.length/x + 1;
		}
		for (int i = 1; i <= n; i++) {
			String[] newArr = new String[x];
			for (int j = 0; j < x; j++) {
				if(j+x*(i-1) >= intArrary.length){
					break;
				}
				newArr[j] = intArrary[j+x*(i-1)];
				 
			}
			String ff=(String) Arrays.toString(newArr).subSequence(1, Arrays.toString(newArr).length()-1);
			
			list.add(ff.substring(0, ff.indexOf("null")==-1?13:ff.indexOf("null")-2));
		}
		return list;

	
	}
	
	public static void main(String[] args) {
		String str =  "1,2,3,4,5,6,7,8,9,z,b,c,d,e,f,g";
	List dd=	getGroupString(str);
	for (int i = 0; i < dd.size(); i++) {
		System.out.println(dd.get(i));
	}
	}
}
