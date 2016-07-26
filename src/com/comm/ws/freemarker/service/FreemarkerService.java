package com.comm.ws.freemarker.service;

import java.util.Map;

import javax.servlet.ServletContext;

import com.comm.util.BaseResponseObject;
import com.comm.util.page.Page;

public interface FreemarkerService {

	void createHtml(Map<String, Object> data,
			String ftlPath, String htmlPath);
}
