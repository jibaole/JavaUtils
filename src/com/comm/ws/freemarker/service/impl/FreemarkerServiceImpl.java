package com.comm.ws.freemarker.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.ws.freemarker.service.FreemarkerService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Service("freemarkerService")
@Transactional
public class FreemarkerServiceImpl implements FreemarkerService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FreemarkerServiceImpl.class);

	@Override
	public void createHtml(Map<String, Object> data, String templatePath,
			String targetHtmlPath) {

		try {
			//	logger.error("crateHTML(ServletContext, Map<String,Object>, String, String) - ServletContext context=" + context); //$NON-NLS-1$
			Configuration freemarkerCfg = new Configuration();
			// 加载模版
			freemarkerCfg
					.setClassForTemplateLoading(this.getClass(), "/");
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
			freemarkerCfg.setLocale(Locale.SIMPLIFIED_CHINESE);

			// freemarkerCfg.setServletContextForTemplateLoading(context, "/");
			freemarkerCfg.setEncoding(Locale.CHINA, "UTF-8");
			logger.error("crateHTML(ServletContext, templatePath=" + templatePath); //$NON-NLS-1$
			// 指定模版路径
			Template template = freemarkerCfg
					.getTemplate(templatePath, "UTF-8");
			template.setEncoding("UTF-8");
			// 静态页面路径
			// String htmlPath = context.getRealPath("") + "/" + targetHtmlPath;
			String htmlPath = "/" + targetHtmlPath;

			String documentPath = htmlPath.substring(0,
					htmlPath.lastIndexOf("/"));// 目录
			logger.error("crateHTML(ServletContext, Map<String,Object>, String, String) htmlPath"
					+ htmlPath);
			File docPath = new File(documentPath);
			if (!docPath.exists()) {
				// 先生成目录
				docPath.mkdirs();
			}
			File htmlFile = new File(htmlPath);
			logger.error("crateHTML(ServletContext, Map<String,Object>, String, String) htmlFile"
					+ htmlFile.getAbsolutePath());
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			// 处理模版
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
