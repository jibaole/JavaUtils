package com.comm.util.rest;

import java.io.IOException;
import java.util.List;

import org.restlet.data.CharacterSet;
import org.restlet.representation.Representation;

import com.comm.util.json.JsonUtil;

public class RepresentationUtil {

	public static List representationToList(
			Representation representation,Class objClass) throws IOException {
		List list=null;
		try{
			representation.setCharacterSet(CharacterSet.UTF_8);
			String returnValue = representation.getText();
//			System.out.println("==returnValue=="+returnValue);
			
			if(returnValue!=null && returnValue.length()>0){
				String jsonString = returnValue.substring(returnValue.indexOf(":[") + 1, returnValue.indexOf("]") + 1);
				if (jsonString != null && jsonString.length()>0) {
					list = JsonUtil.getDTOList(jsonString, objClass,"yyyy-MM-dd hh:mm:ss");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
}
