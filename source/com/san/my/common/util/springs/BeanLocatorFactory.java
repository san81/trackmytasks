/*
 * Copyright © 2007-2008 , Agentrics LLC – All Rights Reserved.
 *
 */
package com.san.my.common.util.springs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.san.my.common.exception.BeanLookupException;


/**
 * Locates a given bean or service from application context. 
 */
public class BeanLocatorFactory {
    private static Logger logger = Logger.getLogger(BeanLocatorFactory.class);
    private static ApplicationContext applicationContext = null;


    /**
     *  Locates a bean from applicationContext and returns it.
     * @param beanId the Id of the bean to lookup
     * @return the bean object 
     * @throws MGSFatalException if applicationContext is null (that means either this method is called before
     * the spring configuration file is loaded or the file is loaded but applicationContext is not set here).
     */
    public static Object getBean(String beanId) throws Exception {
        try {
            if (applicationContext == null) {
                logger.error("applicationContext not available to lookup the bean "+beanId);
                throw new Exception("Application context not yet initialized.");
            }

            Object o = applicationContext.getBean(beanId);
            return o;
        } catch (NoSuchBeanDefinitionException e) {
            logger.error("No Bean found in applicationContext with the id : "+beanId );
            throw new Exception("No Bean found in applicationContext with the id : "+beanId);
        }
    }
    
    /**
     * This should only be called by the <code>SpringConfigurationLoader</code>.
     *
     * @param context the application context to use as a service locator.
     */
    public static synchronized void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    
    /**
     * Locates a service from applicationContext and returns it. Type cast the object to your interface type
     * @param Class interfaceClass - the Class of the interface whose implementation is to be lookedup. 
     * @return the interface implementation
     * @throws MGSFatalException if applicationContext is null (that means either this method is called before
     * the spring configuration file is loaded or the file is loaded but applicationContext is not set here).
     */
    public static Object getService(Class interfaceClass) throws BeanLookupException{
        String className = new String(interfaceClass.getName());
        int index = className.lastIndexOf(".");
        StringBuffer simpleName = new StringBuffer (className.toString().substring(index+1, className.length()));
        simpleName.setCharAt(0, Character.toLowerCase(simpleName.charAt(0)));
        String beanLookupId = simpleName.toString();
        logger.debug("looking up for bean with id "+beanLookupId);
       	try{
       		return getBean(beanLookupId);
       	}catch(Exception excep){
       		throw new BeanLookupException(excep);
       	}
       
    }

}
