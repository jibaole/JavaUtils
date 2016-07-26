package com.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Student;


public class JsonDemo {
	public static void main(String[] args) {
		
		 List<Map> activityDetialList = new ArrayList();
		 
		for (int i = 0; i < 3; i++) {
			Map parent_menu = new HashMap();
			
			parent_menu.put("id", i);
			parent_menu.put("name", i);
			for (int j = 5; j < 8; j++) {
				Student ss=new Student();
				ss.setId(j);
				ss.setName("name"+j);
				ss.setAge(10+j);
				parent_menu.put("srudentList",ss);
				//System.out.println(new JSONArray().fromObject(ss));
			}
			activityDetialList.add(parent_menu);
		}
		
		System.out.println(activityDetialList);
	}
}
