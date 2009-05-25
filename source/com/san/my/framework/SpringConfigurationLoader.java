/*
 * Copyright © 2007-2008 , Agentrics LLC – All Rights Reserved.
 *
 */
package com.san.my.framework;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.san.my.common.global.AppConstants;
import com.san.my.common.util.springs.BeanLocatorFactory;


/**
 * This class loads the necessery application related spring configuration files using
 * <code>ClassPathXmlApplicationContext</code>.
 *
 * @author hari
 */
public class SpringConfigurationLoader {
    private static Logger logger = Logger.getLogger(SpringConfigurationLoader.class);
    private static String[] defaultConfigPaths = {AppConstants.APP_DAO_CONFIG,AppConstants.APP_BOTransaction_CONFIG,AppConstants.APP_CONFIG};
    /**
     * loads the given configuration files. The instanitated beans will be
     * available in appContext.
     *
     * @param configPaths - Array paths of xml files to be loaded. A path can be
     *            a regex path also like to load all the config files whose name
     *            starts with 'application-', we can give the path as
     *            'application-*.xml'.
     * @return true or false depending on wether the configuration loading was
     *         successful or not respectively.
     */
    public static boolean loadConfiguration(String[] configPaths) {
        ApplicationContext appContext = null;
        try {
            appContext = new ClassPathXmlApplicationContext(configPaths);

            // passing a reference of this appContext to the ServiceLocator, so
            // that it
            // can be used later to lookup beans.
            BeanLocatorFactory.setApplicationContext(appContext);
            if (appContext == null) {
                return false;
            }
        } catch (BeansException e) {
            logger.error("Excpetion while creating appContext from configfile ", e);
            return false;
        }
        return true;
    }
    public static boolean loadConfiguration(){
        return loadConfiguration(defaultConfigPaths);
    }
}
