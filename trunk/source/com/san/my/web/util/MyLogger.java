package com.san.my.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MyLogger {
	static Logger logger = null;
	
	public static void initLog4j(String logFileName) {
		Properties properties = new Properties();
		try {
			InputStream fin = MyLogger.class.getClassLoader()
					.getResourceAsStream(logFileName);
			if (fin == null) {
				System.out
						.println("inputstream is null; cannot read log4j properties file: "
								+ logFileName);
			}
			properties.load(fin);
			PropertyConfigurator.configure(properties);
			logger = Logger.getLogger(MyLogger.class);
			logger.info("Log4j initialized based on :" + logFileName);
		} catch (IOException ie) {
			System.out.println("IOException in initLog4j().\nReason:"
							+ ie.getMessage());
		}
	}
}
