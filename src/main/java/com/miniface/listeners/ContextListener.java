package com.miniface.listeners;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

import com.miniface.utils.QueryHolder;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
	
	public static ThreadPoolExecutor executor;
 
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
         
        PropertyConfigurator.configure(fullPath);
        
        QueryHolder.loadProperties();
        
        executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }
     
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	executor.shutdown(); 
    }  
}