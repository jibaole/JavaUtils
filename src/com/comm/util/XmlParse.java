package com.comm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * xml解析
 * @author lyq
 *
 */
public class XmlParse {
	
	/**
	 * xml转化为Map
	 * @param xmlText
	 * @param nodeName
	 * @return
	 */
	public static Map nodeToMap(String xmlText, String nodeName) {
		Map map = new HashMap();
		Document document;
		try {
			document = DocumentHelper.parseText(xmlText);
			Element rootElt = document.getRootElement();
			List<Element> nodes = null;
			if (StringUtils.isNotEmpty(nodeName)) {
				nodes = rootElt.elements(nodeName);
			} else {
				nodes = rootElt.elements();
			}
			for (Element node : nodes) {

				String nName = node.getName();
				String nValue = node.getText();
				map.put(nName, nValue);
				System.out.println(nName + "" + nValue);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}
}
