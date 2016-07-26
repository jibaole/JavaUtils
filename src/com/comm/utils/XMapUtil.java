package com.comm.utils;

import java.io.InputStream;
import java.util.List;

import org.nuxeo.common.xmap.XMap;

/**
 * 
 * 类名称： XMapUtil 类描述： 创建人： huojianjun 创建时间：2014-1-9 上午10:58:13 修改人： huojianjun
 * 修改时间：2014-1-9 上午10:58:13 修改备注：
 * 
 */
public class XMapUtil {
	private static final XMap xmap;

	static {
		xmap = new XMap();
	}

	/**
	 * 注册Object。
	 * 
	 * @param clazz
	 */
	public static void register(Class<?> clazz) {
		if (clazz != null) {
			xmap.register(clazz);
		}
	}

	public static Object[] load(InputStream is) throws Exception {
		Object[] obj = null;
		try {
			obj = xmap.loadAll(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return obj;
	}

	public static String asXml(Object obj, String encoding,
			List<String> outputsFields) throws Exception {

		return xmap.asXmlString(obj, encoding, outputsFields);
	}
	

}
