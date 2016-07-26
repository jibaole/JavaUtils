package com.comm.util.freemarker;

import java.util.Map;

import javax.servlet.ServletContext;

import com.comm.util.freemarker.impl.StringTemplateRender;
import com.comm.util.freemarker.impl.render.ClassPathTemplateRender;
import com.comm.util.freemarker.impl.render.ContextPathTemplateRender;

public class FtlUtil {  
    public static String renderString(Map dataModel, String text)throws Exception{  
        String ret = StringTemplateRender.getInstance().render(dataModel, text);  
        return ret;  
    }  
      
    public static String renderFile(Object dataModel, String ftlFile)throws Exception{  
        String ret = ClassPathTemplateRender.getInstance().render(dataModel, ftlFile);  
        return ret;  
    }  
      
    public static String renderFile(ServletContext sc, Object dataModel, String ftlFile)throws Exception{  
        String ret = ContextPathTemplateRender.getInstance(sc).render(dataModel, ftlFile);  
        return ret;  
    }  
}  
