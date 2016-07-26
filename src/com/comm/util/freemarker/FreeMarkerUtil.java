package com.comm.util.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerUtil {
	/**
	 * Logger for this class
	 */
	//private static final Logger logger = Logger.getLogger(FreeMarkerUtil.class);

	/**
	 * 生成静态页面主方法
	 * 
	 * @param context
	 *            ServletContext
	 * @param data
	 *            一个Map的数据结果集
	 * @param templatePath
	 *            ftl模版路径
	 * @param targetHtmlPath
	 *            生成静态页面的路径
	 */

	public static void crateHtmlPage(Locale locale, String url,
			Map<String, Object> data, String htmlPath) {

		try {

			Locale.setDefault(locale);

			Configuration freemarkerCfg = new Configuration();
			freemarkerCfg.setObjectWrapper(new DefaultObjectWrapper());

			URLTemplateLoaderImpl URLTemplateLoader = new URLTemplateLoaderImpl();

			URLTemplateLoader.getURL(url);

			freemarkerCfg.setTemplateLoader(URLTemplateLoader);
			// freemarkerCfg.setServletContextForTemplateLoading(context, "/");

			// Configuration freemarkerCfg = new Configuration();
			// 加载模版

			// freemarkerCfg.setServletContextForTemplateLoading(context, "/");

			// freemarkerCfg.setServletContextForTemplateLoading(
			// context, "WEB-INF/tmpl");
			// - 设置模板延迟时间，测试环境设置为0，正是环境可提高数值.
			// freemarkerCfg.setTemplateUpdateDelay(0);
			// - 设置错误句柄
			freemarkerCfg
					.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
			// freemarkerCfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			// - 设置默认模板编码
			freemarkerCfg.setDefaultEncoding("UTF-8");
			// - 设置输出编码
			freemarkerCfg.setOutputEncoding("UTF-8");

			// freemarkerCfg.setOutputEncoding("GBK");
			freemarkerCfg.setLocale(Locale.getDefault());
			freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");

			System.out.println("crateHTML(ServletContext, templatePath=" + url); //$NON-NLS-1$

			// 指定模版路径
			Template template = freemarkerCfg.getTemplate(url,Locale.getDefault(), "UTF-8");
			template.setEncoding("UTF-8");

			System.out.println("crateHTML system templatePath=" + template.getName()); //$NON-NLS-1$

			// 静态页面路径

			String documentPath = htmlPath.substring(0,htmlPath.lastIndexOf("/"));// 目录
			System.out.println("crateHTML log htmlPath=" + htmlPath);
			File docPath = new File(documentPath);
			if (!docPath.exists()) {
				// 先生成目录
				docPath.mkdirs();
			}
			File htmlFile = new File(htmlPath);

			System.out.println("htmlFile=" + htmlFile);

			System.out.println("crateHTML log htmlFile.getAbsolutePath()="
					+ htmlFile.getAbsolutePath());
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));

			System.out.println("out=" + out);

			// 处理模版
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "D:\\Workspaces\\chunyu\\didi\\src\\main\\resources\\templates\\doctorLogin.ftl";
		Map<String, Object> data = new HashMap<String, Object>();
		String htmlPath = "d:/1.html";
		FreeMarkerUtil.crateHtmlPage(Locale.CHINA, url, data, htmlPath);
	}

}
