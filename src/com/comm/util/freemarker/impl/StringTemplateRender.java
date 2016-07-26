package com.comm.util.freemarker.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import com.comm.util.freemarker.TemplateRender;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class StringTemplateRender implements TemplateRender {
	private static Configuration config = null;

	public static StringTemplateRender getInstance() {
		return new StringTemplateRender();
	}

	public StringTemplateRender() {
		if (config == null) {
			config = new Configuration();
			config.setTemplateLoader(new StringTemplateLoader());

			try {
				config.setSetting("datetime_format", "yyyy-MM-dd HH:mm:ss");
				config.setLocale(Locale.CHINA);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public String render(Object dataModel, String text) throws Exception {
		String ret = null;

		if ((dataModel instanceof Map) == false) {
			throw new IllegalArgumentException("参数dataModel必须为一个Map对象");
		}

		BufferedReader reader = new BufferedReader(new StringReader(text));
		Template template = null;
		try {
			template = new Template(null, reader, config, "UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SimpleHash root = new SimpleHash();
		root.putAll((Map) dataModel);
		try {
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			template.process(root, writer);
			writer.flush();

			ret = stringWriter.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
			ret = null;
		}
		return ret;
	}
}
