package com.allin.ws.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.json.JSONObject;

public class AddressUtils {
	private static AddressUtils addressUtils;   
	 public static AddressUtils getInstance(){
    	if (addressUtils == null) {
			addressUtils = new AddressUtils();
		}
		return addressUtils;
    }
    
    /** 
     * 获取地址 
     * @param params 
     * @param encoding 
     * @return 
     * @throws Exception 
     */  
    public  String getAddress(String params, String encoding){  
       try{  
        String path = "http://ip.taobao.com/service/getIpInfo.php";  
          
        String returnStr = this.getRs(path, params, encoding);  
          
        JSONObject json=null;  
          
        if(returnStr != null){  
              
            json = new JSONObject(returnStr);  
              
            if( "0".equals(json.get("code").toString())  && returnStr.startsWith("{") && returnStr.endsWith("}")){  
                  
                StringBuffer buffer = new StringBuffer();  
                  
                //buffer.append(decodeUnicode(json.optJSONObject("data").getString("country")));//国家  
                  
                //buffer.append(decodeUnicode(json.optJSONObject("data").getString("area")));//地区  
                //buffer.append(decodeUnicode(json.optJSONObject("data").getString("region_id")));  
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("region"))).append("-");//省份  
                  
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("city"))).append("-");//市区  
                  
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("county"))).append("-");//地区  
                  
                buffer.append(decodeUnicode(json.optJSONObject("data").getString("isp")));//ISP公司  
                  
                return buffer.toString();  
                  
            }else{  
                  
                return "无";  
                  
            }  
              
        }  
       }catch(Exception e){
    	   e.printStackTrace();
       }
        return "";  
          
    }  
    /** 
     * 从url获取结果 
     * @param path 
     * @param params 
     * @param encoding 
     * @return 
     */  
    public static String getRs(String path, String params, String encoding){  
          
        URL url = null;  
          
        HttpURLConnection connection = null;  
              
        try {  
              
            url = new URL(path);  
                  
            connection = (HttpURLConnection)url.openConnection();// 新建连接实例  
                  
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫?  
              
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫?  
              
            connection.setDoInput(true);// 是否打开输出? true|false  
              
            connection.setDoOutput(true);// 是否打开输入流true|false  
              
            connection.setRequestMethod("POST");// 提交方法POST|GET  
              
            connection.setUseCaches(false);// 是否缓存true|false  
              
            connection.connect();// 打开连接端口  
              
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
              
            out.writeBytes(params);  
              
            out.flush();  
              
            out.close();  
              
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));  
              
            StringBuffer buffer = new StringBuffer();  
              
            String line = "";  
              
            while ((line = reader.readLine())!= null) {  
                  
                buffer.append(line);  
                  
            }  
              
            reader.close();  
              
            return buffer.toString();  
              
        } catch (Exception e) {  
              
          //  e.printStackTrace();  
              
        }finally{  
              
            connection.disconnect();// 关闭连接  
              
        }  
          
        return null;  
    }  
    /** 
     * 字符转码 
     * @param theString 
     * @return 
     */  
    public static String decodeUnicode(String theString){  
          
        char aChar;  
          
        int len = theString.length();  
          
        StringBuffer buffer = new StringBuffer(len);  
          
        for (int i = 0; i < len;) {  
              
            aChar = theString.charAt(i++);  
              
            if(aChar == '\\'){  
                  
                aChar = theString.charAt(i++);  
              
                if(aChar == 'u'){  
                      
                    int val = 0;  
                      
                    for(int j = 0; j < 4; j++){  
                          
                        aChar = theString.charAt(i++);  
                          
                        switch (aChar) {  
                          
                        case '0':  
                              
                        case '1':  
                              
                        case '2':  
                              
                        case '3':  
                              
                        case '4':  
                          
                        case '5':  
                              
                        case '6':  
                          
                        case '7':  
                              
                        case '8':  
                              
                        case '9':  
                              
                        val = (val << 4) + aChar - '0';  
                          
                        break;  
      
                        case 'a':  
                              
                        case 'b':  
                              
                        case 'c':  
                              
                        case 'd':  
                              
                        case 'e':  
                              
                        case 'f':  
                              
                        val = (val << 4) + 10 + aChar - 'a';  
                                 
                        break;  
                          
                        case 'A':  
                              
                        case 'B':  
                              
                        case 'C':  
                              
                        case 'D':  
                              
                        case 'E':  
                              
                        case 'F':  
                            
                        val = (val << 4) + 10 + aChar - 'A';  
                                 
                        break;  
                                 
                        default:  
                          
                        throw new IllegalArgumentException(  
                               
                            "Malformed      encoding.");  
                        }  
                          
                    }  
                      
                    buffer.append((char) val);  
                      
                    }else{  
                          
                        if(aChar == 't'){  
                              
                            aChar = '\t';  
                        }  
                          
                        if(aChar == 'r'){  
                              
                            aChar = '\r';  
                        }  
                          
                        if(aChar == 'n'){  
                              
                            aChar = '\n';  
                        }  
                          
                        if(aChar == 'f'){  
                              
                            aChar = '\f';  
                              
                        }  
                          
                        buffer.append(aChar);  
                    }  
                  
                }else{  
                      
                    buffer.append(aChar);  
                      
                }  
                  
            }  
          
        return buffer.toString();  
          
    }  
    
    
    public  String getIpAddr(HttpServletRequest request) {        
    	String ip = request.getHeader("x-forwarded-for");    
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {           
    		ip = request.getHeader("Proxy-Client-IP");     
    	}        
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {           
    		ip = request.getHeader("WL-Proxy-Client-IP");        
    	}        
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {            
    		ip = request.getRemoteAddr();        
    	}    
    	return ip;   
    	
    } 
    

 @Resource 
 private WebServiceContext wsContext; 
  
private String getClientInfo() { 
 String clientIP = null; 

 try { 

	
	 MessageContext mc = wsContext.getMessageContext(); 
	
	HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST)); 
	
	clientIP = request.getRemoteAddr(); 
	System.out.println("client IP : " + clientIP); 
	} catch (Exception e) { 
	
	e.printStackTrace(); 
	
	} 
	
	return clientIP; 
	
	} 

  
}
