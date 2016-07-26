package com.comm.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 描        述：
 * 创建时间：2016-7-26
 * @author Jibaole
 */
public class JoaUtil
{

	/**
	 * 获取网络端数据，保存本地
	 * @param url
	 * @param xmlPath
	 * @throws Exception
	 */
	
	public static void downloadFilterXml(String url, String xmlPath)
			throws Exception {
		try {
			String line = "";
			StringBuilder content = new StringBuilder();

			URL url_conn = new URL(url);
			HttpURLConnection http_conn = (HttpURLConnection) url_conn
					.openConnection();
			InputStream inputStream = http_conn.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream,
					"utf-8");
			BufferedReader br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				content.append(line.trim());
			}
			// &ldquo
			String buffContent = content.toString().replace("&ldquo", "");
			buffContent = buffContent.replace("摘要：", "");

			FileOutputStream fileOutStream = new FileOutputStream(new File(xmlPath));
			BufferedOutputStream Buff = new BufferedOutputStream(fileOutStream);
			Buff.write(buffContent.getBytes());
			Buff.flush();
			Buff.close();
			fileOutStream.close();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static Object[] getXmlObject(String xmlPath) throws Exception
	{

		Object[] objectArray = null;
		try
		{
			InputStream is = new FileInputStream(xmlPath);
			//XMapUtil.register(CmsJoaColumn.class);
			objectArray = (Object[]) XMapUtil.load(is);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return objectArray;
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			String url = "https://www.baidu.com/index.php?tn=monline_3_dg" ;
			String xmlPath = "d:\\2013_4.xml" ;
			JoaUtil.downloadFilterXml(url, xmlPath) ;
			Object[] objArray = JoaUtil.getXmlObject(xmlPath);
			//CmsJoaColumn joa = (CmsJoaColumn) objArray[0];
			//System.out.println(joa.getColumn());
			//System.out.println(joa.getItemList().get(0).getJoaTitle());

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
