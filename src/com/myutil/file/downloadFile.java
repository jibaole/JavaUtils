package com.myutil.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/** 
* 2015-12-4 
* DES:文件下载
* author:JiBaoLe 
*/ 
public class downloadFile {
	public static HttpServletResponse response; 
	public static HttpServletRequest  request;
	/*
	 * DES:下载网络文件，指定到本地
	 */
	 public static void downloadNet(HttpServletResponse response) throws MalformedURLException {
	        // 下载网络文件
	        int bytesum = 0;
	        int byteread = 0;

	        URL url = new URL("https://github-cloud.s3.amazonaws.com/releases/23216272/90edc59e-a29d-11e5-93e9-aba3aa06fa23.exe?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAISTNZFOVBIJMK3TQ%2F20151223%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20151223T024759Z&X-Amz-Expires=300&X-Amz-Signature=c32f457dfe34f2966f9212390b1a560efffe20dc7969c5431079846155deb259&X-Amz-SignedHeaders=host&actor_id=0&response-content-disposition=attachment%3B%20filename%3DGit-2.6.4-64-bit.exe&response-content-type=application%2Foctet-stream");

	        try {
	            URLConnection conn = url.openConnection();
	            InputStream inStream = conn.getInputStream();
	             //写入本地指定位置
	            FileOutputStream fs = new FileOutputStream("c:/Git-2.6.4-64-bit.exe");

	            byte[] buffer = new byte[1204];
	            int length;
	            while ((byteread = inStream.read(buffer)) != -1) {
	                bytesum += byteread;
	                System.out.println(bytesum);
	                fs.write(buffer, 0, byteread);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	    /*
	     * 描述：根据浏览器请求，在线下载网络&&远程服务器的文件， 视频下载(有效的视频地址源)
		 */
		public void downloadVideo() throws IOException{
			int byteread = 0;
			OutputStream out = null;
			String suffix="";
			String prefix="";
			try {
				//String path = request.getParameter("path");
				String path="http://7xl1fx.com2.z0.glb.qiniucdn.com/1450255435_480_320.mp4";
				//String videoAttFormat = request.getParameter("videoAttFormat");
				if(!StringUtils.isEmpty(path)){
					URL url = new URL(path);
					out = response.getOutputStream();
					//得到前后缀
					String fileName=path.substring(path.lastIndexOf("/")+1, path.length());
					if(fileName.lastIndexOf(".")< 0){
						prefix=fileName;
						//suffix="."+videoAttFormat;
					}else{
						prefix=fileName.substring(0, fileName.lastIndexOf("."));
						suffix=	fileName.substring(fileName.lastIndexOf("."), fileName.length());
					}
					
				    response.reset();
					response.setContentType("text/html;charset=utf-8");
					request.setCharacterEncoding("UTF-8");
					response.setContentType("application/octet-stream");
					response.setHeader("Content-disposition", "attachment;filename="
							+ new String(prefix.getBytes("utf-8"), "ISO8859-1") + suffix);
					URLConnection conn = url.openConnection();
					InputStream inStream = conn.getInputStream();

					byte[] buffer = new byte[1204];
					while ((byteread = inStream.read(buffer)) != -1) {
						out.write(buffer, 0, byteread);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * 校验视频地址是否有对应的原文件
		 */
		public void isValidVideoUrl() {
			PrintWriter out=null;
			try {
				out = response.getWriter();
				response.setHeader("content-type", "text/html;charset=UTF-8");
				String path = request.getParameter("path");
				URL serverUrl = new URL(path);
				HttpURLConnection urlcon = (HttpURLConnection) serverUrl
						.openConnection();
				String message = urlcon.getHeaderField(0);
				// 文件存在‘HTTP/1.1 200 OK’ 文件不存在
				// ‘HTTP/1.1 404 Not Found’
				if (!StringUtils.isEmpty(message) && message.startsWith("HTTP/1.1 404")) {
					out.write("no");
				} else {
					out.write("yes");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 public static void main(String[] args) {
		 
			 try {
				downloadNet(response);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
