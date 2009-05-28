package com.san.my.common.global;


public interface IConstants  {

  public static final String KEY_USER_IN_SESSION  = "USER_OBJECT_IN_SESSION";
  
  //global action result mappings
  public static final String LOGIN_ACTION  = "goToLogin";
  
  public static final String KEY_USER_OBJECT  = "REGISTRY_USER_OBJECT";
  public static final String KEY_SETUP_OBJECT = "USER_SETUP_OBJECT";
  // session tokens
  public static final String CALENDAR_KEY = "calendarBean";  
      
    public static final String ERROR_LEVEL_FATAL = "FATAL";
    public static final String ERROR_LEVEL_WARN = "WARN";
    public static final int ERROR_CODE_404 = 404;
    //public static final int ERROR_CODE_4505 = 505;
    public static final String SYSTEM_ERROR_DEFAULT_KEY = "error.system.default.message";
    public static final String APPLICATION_ERROR_DEFAULT_KEY = "error.application.default.message";   
    
    public final static int MAX_SIZE_COMPANY_NAME=80;
    public final static int MAX_SIZE_PERSON_FIRST_NAME=100;
    public final static int MAX_SIZE_PERSON_LAST_NAME=100;    
    public final static int MAX_SIZE_PERSON_EMAIL=250; 
    public final static int MAX_SIZE_ADDRESS_NICKNAME = 30;
    public final static int MAX_SIZE_POSTAL_CODE_US = 10;
    public final static int MAX_SIZE_POSTAL_CODE = 40;
    public final static int MAX_SIZE_PHONE = 40;
    public final static int MAX_SIZE_PHONE_EXT = 20;
    public final static int MAX_SIZE_CITY = 60;
    public final static int MAX_SIZE_PROVINCE_OR_STATE = 60;
    public final static int MAX_SIZE_ADDRESS1 = 240;
    public final static int MAX_SIZE_ADDRESS2 = 240;
    public final static int MAX_SIZE_DUNS_NUMBER =11;  
    public final static int MIN_SIZE_LOGIN_NAME = 20;
    public final static int MAX_SIZE_LOGIN_NAME = MAX_SIZE_PERSON_LAST_NAME + 1;
    public final static int MAX_SIZE_AUCTION_NAME=200;
    
	
    //Number formats
    public final static String NUMBER_FORMAT_US = "#,###.##";
    public final static String NUMBER_FORMAT_GERMANY = "#.###,##";
    public final static String NUMBER_FORMAT_FRANCE = "# ###,##";
   
    
}