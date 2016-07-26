package com.comm.util.freemarker.impl.render;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletContext;

import com.comm.util.freemarker.TemplateRender;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ContextPathTemplateRender implements TemplateRender {  
    private static Configuration config = null;  
      
    public static ContextPathTemplateRender getInstance(ServletContext sc){  
        return new ContextPathTemplateRender(sc);  
    }  
      
    public ContextPathTemplateRender(ServletContext sc){  
        if(config == null){  
            config = new Configuration();  
            config.setServletContextForTemplateLoading(sc, "/freemarker/template"); //第二个参数指定模板所在的根目录，必须以“/”开头。  
              
            try{  
                config.setSetting("datetime_format", "yyyy-MM-dd HH:mm:ss");  
                config.setLocale(Locale.CHINA);  
                  
            }catch(Exception ex){  
                ex.printStackTrace();  
            }  
        }  
    }  
      
    public String render(Object dataModel, String ftlFile)throws Exception {  
        StringWriter stringWriter = new StringWriter();  
        BufferedWriter writer = new BufferedWriter(stringWriter);  
          
        Template template = config.getTemplate(ftlFile, Locale.CHINA, "UTF-8");  
        template.process(dataModel, writer);  
        writer.flush();  
          
        return stringWriter.toString();  
    }  
}  