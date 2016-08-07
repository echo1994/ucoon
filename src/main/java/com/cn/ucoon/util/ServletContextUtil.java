package com.cn.ucoon.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


/**
 * 全局缓存servletcontext 
 * @author mlk
 *
 */
public class ServletContextUtil {

	private static ServletContext serveltContext = null;  
    
    private ServletContextUtil(){};  
      
    public synchronized static ServletContext get() {  
          
        if(null == serveltContext) {  
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            serveltContext = webApplicationContext.getServletContext();   
        }   
        return serveltContext;  
    }  
}
