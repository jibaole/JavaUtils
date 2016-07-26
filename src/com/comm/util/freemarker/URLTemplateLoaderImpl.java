package com.comm.util.freemarker;

import java.net.MalformedURLException;
import java.net.URL;

import freemarker.cache.URLTemplateLoader;

public class URLTemplateLoaderImpl extends URLTemplateLoader{

	@Override
	protected URL getURL(String url) {
		URL conn_url =null;
		try {
			 conn_url = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn_url;
		
		
	}

}
