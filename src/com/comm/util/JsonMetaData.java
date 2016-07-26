package com.comm.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class JsonMetaData {
   public static String getJsonResult (List<?> list){
	   JSONObject json = new JSONObject();
	   JSONArray array = new JSONArray();
	   if (list != null && list.size()!=0){
		   for (int i=0;i<list.size();i++){
			   array.add(JSONObject.fromObject(list.get(i)));
		   }
		   json.put("data", array);
	   }else{
		   return null;
	   }
	   return json.toString();
   }
}
