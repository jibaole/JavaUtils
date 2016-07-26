package com.comm.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 
 * 描        述：jsoup解析HTML
 * 创建时间：2016-7-26
 * @author Jibaole
 */
public class UrlUtil {
	/**
	 * jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。
	 * @param url
	 * @return
	 */
	public static String invoke(String url) {
		StringBuffer out = new StringBuffer();
		try {
			URL conn_url = new URL(url);
			URLConnection con = conn_url.openConnection();
			InputStream is = con.getInputStream();
			InputStream inputStream = new BufferedInputStream(
					conn_url.openStream());
			byte[] b = new byte[4096];
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			System.out.println(out.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return out.toString();
	}

	public static void main(String[] args) {
		String url = "https://www.baidu.com/index.php?tn=monline_3_dg";
		System.out.println(UrlUtil.invoke(url));
		try {
			Document doc = Jsoup.connect(url).get();
			// Elements contents = doc.getElementsByAttribute("a");
			Elements datas = doc.getElementsByTag("a");// getElementsByAttribute("a");
			for (Element data : datas) {
				System.out.println("data=" + data.attr("href").toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
