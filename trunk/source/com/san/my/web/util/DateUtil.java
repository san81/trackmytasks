package com.san.my.web.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.san.my.common.global.Config;
import com.san.my.common.global.Constants;
import com.san.my.common.global.Default;

public class DateUtil {
	static Logger logger = Logger.getLogger(DateUtil.class);

	private static HashMap months;
	private static HashMap localizedMonths = new HashMap();
	private static HashMap month_days;
	
	public static final String DEFAULT_DATE_PATTERN = "MM-dd-yyyy hh:mm aaa";
	public static final String DEFAULT_TIMEZONE = "UTC";
	public static final String DEFAULT_DATE_TIME_PATTERN = "MM-dd-yyyy HH:mm aa";

	public static String localizeDateTime(Date date, String dateFormat, String tzCode, Locale locale){
		
		String retDate = Constants.EMPTY_STRING;
		boolean addTzCode = false;
		if (StringUtil.isNullOrEmpty(tzCode)) {
			tzCode = Default.USER_TIME_ZONE;
			addTzCode = true;
		}
		if (StringUtil.isNullOrEmpty(dateFormat)) {
			dateFormat = Default.USER_DATE_FORMAT;
		}
		String dateTimeFormat = dateFormat + Constants.ONE_WHITE_SPACE
				+ Default.APP_TIME_FORMAT;

		logger.debug("dateTimeFormat = " + dateTimeFormat);

		//String defaultSrcFormat = Default.DB_JAVA_DATE_TIME_FORMAT;
		String defaultTimeZone = Default.DB_TIMEZONE;
		
		if (date!= null) {
			logger.debug("open date = " + date.toString());
			retDate = convertDateTime(date, defaultTimeZone, dateTimeFormat, tzCode, locale);
			//logger.debug("open date = " + date);

			if (addTzCode) {
				retDate += (Constants.ONE_WHITE_SPACE + tzCode);
			}			
		}
		logger.debug("date = " + retDate);
		return retDate;
	}
	
	public static String convertUTC2LocalTime(String timeStr, String newTimezone) {
		DateTime dt = new DateTime(timeStr, Default.APP_TIME_FORMAT, Default.APP_TIMEZONE);
		return dt.toString(Default.APP_TIME_FORMAT, newTimezone);
	}
	
	public static String convertLocal2UTCTime(String timeStr, String timezone) {
		DateTime dt = new DateTime(timeStr, Default.APP_TIME_FORMAT, timezone);
		return dt.toString(Default.APP_TIME_FORMAT, Default.APP_TIMEZONE);
	}

	public static String convertTime(String timeStr, String timezone,String newTimezone) {
		DateTime dt = new DateTime(timeStr, Default.APP_TIME_FORMAT, timezone);
		return dt.toString(Default.APP_TIME_FORMAT, newTimezone);
	}
	
	public static String convertDateTime(String dateTimeStr,
			String dateTimeFormat, String timezone, String newFormat,
			String newTimezone) {
		DateTime dt = new DateTime(dateTimeStr, dateTimeFormat, timezone);
		return dt.toString(newFormat, newTimezone);
	}

	public static String convertDateTime(String dateTimeStr,
			String dateTimeFormat, String timezone, String newFormat,
			String newTimezone, Locale locale) {
		DateTime dt = new DateTime(dateTimeStr, dateTimeFormat, timezone,
				locale);
		return dt.toString(newFormat, newTimezone, locale);
	}
	
	public static String convertDateTime(Date date, String timezone, String newFormat,
			String newTimezone, Locale locale) {
		DateTime dt = new DateTime(date, timezone);
		return dt.toString(newFormat, newTimezone, locale);
	}	

	public static String convertDb2UserDateFormat(String dbDateStr,
			String userFormat, Locale userLocale) throws ParseException {
		return convertDateFormat(dbDateStr,
				Default.DB_DATE_FORMAT, userFormat, userLocale);
	}
	
	public static String convertUser2DbDateFormat(String dateStr,
			String userFormat, Locale userLocale) throws ParseException {
		return convertDateFormat(dateStr,
				 userFormat, Default.DB_DATE_FORMAT, userLocale);
	}

	/**
	 * convert datetime from one simple date format to another
	 * @param sDate datetime value represented as a string
	 * @param currentFormat  the current datetime format represented in valid date format
	 * @param newFormat  the new datetime format represented in valid date format
	 * @exception ParseException   if time parsing error occurs
	 * @return datetime value as a string equivalent in the chosen time format
	 */
	public static String convertDateFormat(String sDate, String currentFormat,
			String newFormat, Locale locale) throws ParseException {
		SimpleDateFormat curFormatter = new SimpleDateFormat(currentFormat,
				locale);
		SimpleDateFormat newFormatter = new SimpleDateFormat(newFormat, locale);
		curFormatter.setLenient(false);
		Date dDate = curFormatter.parse(sDate);
		String newDate = newFormatter.format(dDate);
		return newDate;
	}

	/**
	 * convert datetime from one simple date format to another
	 * @param sDate  datetime value represented as a string
	 * @param currentFormat the current datetime format represented in valid time format
	 * @param newFormat the new datetime format represented in valid time format
	 * @exception ParseException  if time parsing error occurs
	 * @return datetime value as a string equivalent in the chosen time format
	 */
	public static String convertDateFormat(String sDate, String currentFormat,
			String newFormat) throws ParseException {
		return convertDateFormat(sDate, currentFormat, newFormat, Default.LOCALE);
	}

	/**
	 * special purpose routine to convert dates using the proprietary OEX date
	 * format representation which utilizes all uppercase and RRRR instead of
	 * YYYY for year representation. NOTE: this should only be used to convert
	 * date portion only, not any time values. Method does not support date
	 * formats like dd-MM-RRRR hh:mm
	 * 
	 * @param sDate
	 *            datetime value represented as a string
	 * @param currentFormat
	 *            the current datetime format represented in valid time format
	 * @param newFormat
	 *            the new datetime format represented in valid time format
	 * @exception ParseException
	 *                if time parsing error occurs
	 * @return datetime value as a string equivalent in the chosen time format
	 */
	public static String convertOEXDateFormat(String sDate,
			String currentFormat, String newFormat) throws ParseException {

		// convert OEX's RRRR year format to java YYYY format
		String xCurrentFormat = currentFormat.toLowerCase().replace('r', 'y');
		String xNewFormat = newFormat.toLowerCase().replace('r', 'y');
		return convertDateFormat(sDate, xCurrentFormat, xNewFormat);
	}

	/**
	 * validates and converts dates. if date conversion fails with the given
	 * format, date is validated with a default format DD-MON-RRRR.
	 * 
	 * @param dateFormat
	 *            OEX date format (RRRR for year) or Java date format (yyyy)
	 * @param realDateStr
	 * @param date
	 * @param dbDate
	 *            date converted to DB date format MM-dd-yyyy
	 * @param locale
	 * @return
	 */
	public static boolean dateValidate(String dateFormat, String realDateStr,
			StringBuffer date, StringBuffer dbDate, Locale locale) {
		String result = Constants.EMPTY_STRING;
		String dp = Constants.EMPTY_STRING;
		if (StringUtil.isNullOrEmpty(dateFormat))
			dp = "mm-dd-yy";
		dp = dateFormat.toLowerCase();
		int index = dp.indexOf("rrrr");
		if (index != -1) {
			StringBuffer sb = new StringBuffer(dp);
			sb.replace(index, index + 4, "yy");
			dp = sb.toString();
		}
		index = dp.indexOf("yyyy");
		if (index != -1) {
			StringBuffer sb = new StringBuffer(dp);
			sb.replace(index, index + 4, "yy");
			dp = sb.toString();
		}
		dateFormat = dp;

		try {
			result = DateUtil.convertOEXDateFormat(realDateStr, dateFormat,
					"MM-dd-yyyy", locale);
		} catch (ParseException pe) {
			logger.warn("ParseException when using dateFormat " + dateFormat
					+ "  in dateValidate(): " + pe.getMessage());

			try {
				result = DateUtil.convertOEXDateFormat(realDateStr,
						Default.APP_DATE_FORMAT, "MM-dd-yyyy", locale);
			} catch (ParseException ped) {
				logger
						.warn("ParseException when using default dateFormat and user's locale "
								+ Default.APP_DATE_FORMAT
								+ "  in dateValidate(): "
								+ ped.getMessage());
				Locale localeEn = new Locale("en", Constants.EMPTY_STRING);
				try {
					result = DateUtil.convertOEXDateFormat(realDateStr,
							Default.APP_DATE_FORMAT, "MM-dd-yyyy", localeEn);
				} catch (ParseException pede) {
					logger
							.error("ParseException when using default dateFormat and English  "
									+ Default.APP_DATE_FORMAT
									+ "  in dateValidate(): "
									+ pede.getMessage());
					return false;
				}
			}
		}
		dbDate.append(result);
		date.append(realDateStr);
		return true;
	}

	public static void loadMonthsHashMap(Locale currentLocale) {
		// Locale currentLocale=new Locale(locale,Constants.EMPTY_STRING);
		String locale = currentLocale.getLanguage();
		months = new HashMap();
		if ((locale.equalsIgnoreCase("EN"))
				|| (locale.equalsIgnoreCase(Constants.EMPTY_STRING))) {

			months.put("JAN", "1");
			months.put("FEB", "2");
			months.put("MAR", "3");
			months.put("APR", "4");
			months.put("MAY", "5");
			months.put("JUN", "6");
			months.put("JUL", "7");
			months.put("AUG", "8");
			months.put("SEP", "9");
			months.put("OCT", "10");
			months.put("NOV", "11");
			months.put("DEC", "12");
		} else {
			ResourceBundle appBundle = ResourceBundle.getBundle(
					Config.APPLICATION_RESOURCES_FILE, currentLocale);

			// String Jan=appBundle.getString("cal.month.jan");

			String Jan = getLocaleMonth("cal.month.jan", currentLocale,
					appBundle);
			String Feb = getLocaleMonth("cal.month.feb", currentLocale,
					appBundle);
			String Mar = getLocaleMonth("cal.month.mar", currentLocale,
					appBundle);
			String Apr = getLocaleMonth("cal.month.apr", currentLocale,
					appBundle);
			String May = getLocaleMonth("cal.month.may", currentLocale,
					appBundle);
			String Jun = getLocaleMonth("cal.month.jun", currentLocale,
					appBundle);
			String Jul = getLocaleMonth("cal.month.jul", currentLocale,
					appBundle);
			String Aug = getLocaleMonth("cal.month.aug", currentLocale,
					appBundle);
			String Sep = getLocaleMonth("cal.month.sep", currentLocale,
					appBundle);
			String Oct = getLocaleMonth("cal.month.oct", currentLocale,
					appBundle);
			String Nov = getLocaleMonth("cal.month.nov", currentLocale,
					appBundle);
			String Dec = getLocaleMonth("cal.month.dec", currentLocale,
					appBundle);

			months.put(Jan, "1");
			months.put(Feb, "2");
			months.put(Mar, "3");
			months.put(Apr, "4");
			months.put(May, "5");
			months.put(Jun, "6");
			months.put(Jul, "7");
			months.put(Aug, "8");
			months.put(Sep, "9");
			months.put(Oct, "10");
			months.put(Nov, "11");
			months.put(Dec, "12");
		}
		localizedMonths.put(locale, months);
	}

	private static String getLocaleMonth(String month, Locale locale,
			ResourceBundle appBundle) {
		String mon = appBundle.getString(month);
		if (mon.endsWith(".")) {
			mon = mon.substring(0, mon.length() - 1);
		}
		return (mon.toUpperCase(locale));
	}

	public static String ConvertLocalTimeToUTC(String dateStr, String timezone) {
		return (ConvertTimeZone(dateStr, timezone, "UTC"));
	}

	public static String ConvertUTCToLocalTime(String dateStr, String timezone) {
		return (ConvertTimeZone(dateStr, "UTC", timezone));

	}

	/**
	 * converts utc datetime string in default db datetime format to user's
	 * localized datetime in user's date format
	 * 
	 * @param dateStr
	 *            UTC datetime string in default db datetime format
	 * @param timezone
	 *            user's timezone preference code
	 * @param dateFormat
	 *            user's dateformat preference
	 * @param locale
	 *            user's current locale setting
	 * @exception ParseException
	 *                if dateformat conversion error
	 * @return datetime string localized to user's date format preferences
	 */
	public static String ConvertUTCToLocalTime(String dateStr, String timezone,
			String dateFormat, Locale locale) throws ParseException {

		// adjust datetime for GMT offset
		String localDateTime = ConvertTimeZone(dateStr, "UTC", timezone);

		// convert localized datetime in default dateformat to user's dateformat
		// preference
		String userDateTimeFormat = convertToJavaDatePattern(dateFormat)
				+ " hh:mm aaa";
		SimpleDateFormat curFormatter = new SimpleDateFormat(
				DEFAULT_DATE_PATTERN, locale);
		SimpleDateFormat newFormatter = new SimpleDateFormat(
				userDateTimeFormat, locale);

		// fix for bug 6076
		curFormatter
				.setDateFormatSymbols(new DateFormatSymbols(Locale.ENGLISH));
		newFormatter
				.setDateFormatSymbols(new DateFormatSymbols(Locale.ENGLISH));
		// eof bug 6076

		Date dDate = curFormatter.parse(localDateTime);
		String newDate = newFormatter.format(dDate);
		return newDate;
	}

	public static String buildTwoDigits(int iDigitIn) {
		String sDigitIn = Integer.toString(iDigitIn);
		if (sDigitIn.length() == 1)
			return "0" + sDigitIn;
		else
			return sDigitIn;
	}

	public static String buildStandardDateTimeString(String oldDateStr) {
		int datePos = oldDateStr.indexOf(" ");
		if (datePos <= 0)
			return oldDateStr;

		String DateString = oldDateStr.substring(0, datePos);
		int hhPos = oldDateStr.indexOf(":");
		if (hhPos <= 0)
			return oldDateStr;
		String hhString = oldDateStr.substring(datePos + 1, hhPos);
		int mmPos = oldDateStr.lastIndexOf(" ");
		String mmString = oldDateStr.substring(hhPos + 1, mmPos);
		String amString = oldDateStr.substring(mmPos + 1);
		StringBuffer sb = new StringBuffer(DateString);
		sb.append(" ").append(buildTwoDigits(Integer.parseInt(hhString)));
		sb.append(":").append(buildTwoDigits(Integer.parseInt(mmString)));
		sb.append(" ").append(amString);
		return (sb.toString());

	}

	public static String ConvertTimeZone(String oldDateStr, String oldTZ,
			String newTZ) {

		boolean dateOnly = false;

		// TimeZone TZnew=TimeZone.getTimeZone(newTZ);
		// TimeZone TZold=TimeZone.getTimeZone(oldTZ);
		TimeZone TZnew = TimeZone.getTimeZone(newTZ);
		TimeZone TZold = TimeZone.getTimeZone(oldTZ);
		Calendar cal = new GregorianCalendar(TZnew);
		Calendar calOld = new GregorianCalendar(TZold);
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		// oldDateStr=buildStandardDateTimeString(oldDateStr);
		dateFormat.setTimeZone(TZold);
		ParsePosition pos = new ParsePosition(0);
		Date date = dateFormat.parse(oldDateStr, pos);
		if (date == null) {
			dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			dateFormat.setTimeZone(TZold);
			dateOnly = true;
			date = dateFormat.parse(oldDateStr, pos);
		}
		if (date == null) {
			logger
					.error("the input string "
							+ oldDateStr
							+ "has to be in the format \"mm-dd-yyyy hh:mm aaa\" or \"mm-dd-yyyy\" ");
			logger
					.warn("the input string "
							+ oldDateStr
							+ "has to be in the format \"mm-dd-yyyy hh:mm aaa\" or \"mm-dd-yyyy\" ");
			return oldDateStr;
		}
		calOld.setTime(date);
		Date newDate = calOld.getTime();
		cal.setTime(newDate);
		int iYear = cal.get(Calendar.YEAR);
		int iMonth = cal.get(Calendar.MONTH) + 1;
		int iDay = cal.get(Calendar.DAY_OF_MONTH);
		int iHour = cal.get(Calendar.HOUR);
		// to accomodate datebase setting
		if (iHour == 0)
			iHour = 12;

		int iMinute = cal.get(Calendar.MINUTE);
		int iAmPm = cal.get(Calendar.AM_PM);

		StringBuffer sb = new StringBuffer(Constants.EMPTY_STRING);

		sb.append(buildTwoDigits(iMonth)).append("-").append(
				buildTwoDigits(iDay));
		sb.append("-").append(Integer.toString(iYear));
		if (!dateOnly) {
			sb.append(" ").append(buildTwoDigits(iHour));
			sb.append(":").append(buildTwoDigits(iMinute)).append(" ");
			if (iAmPm == 0)
				sb.append("AM");
			else
				sb.append("PM");
		}
		return (sb.toString());
	}

	/**
	 * convert UTC time to local timezone
	 * 
	 * @param sDate
	 *            string containing UTC time in MM-dd-yyyy hh:mm a format
	 * @param localZone
	 *            local timezone for the user
	 * @exception ParseException
	 *                if unable to parse date using UTC format above
	 * @return Date is the UTC DateTime converted to local time zone
	 */
	public static Date convertUTCToLocalTime(String sDate, TimeZone localZone)
			throws ParseException {

		String tzID = localZone.getID();
		String dateStr = ConvertUTCToLocalTime(sDate, tzID);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		Date localDate = formatter.parse(dateStr);
		return localDate;
	}

	public static String getBeginningOfCurrentMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String today = formatter.format(new Date());
		StringBuffer sb = new StringBuffer(Constants.EMPTY_STRING);
		sb.append(today.substring(0, 3)).append("01")
				.append(today.substring(5));
		return (sb.toString());

	}

	public static String getLastDayOfNextMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String today = formatter.format(new Date());
		int nextMonth = Integer.parseInt(today.substring(0, 2)) + 1;
		int year = Integer.parseInt(today.substring(6));

		if (nextMonth > 12) {
			nextMonth = 1;
			year = year + 1;
		}
		if (month_days == null) {
			month_days = new HashMap();
			month_days.put("1", "31");
			month_days.put("2", "28");
			month_days.put("3", "31");
			month_days.put("4", "30");
			month_days.put("5", "31");
			month_days.put("6", "30");
			month_days.put("7", "31");
			month_days.put("8", "31");
			month_days.put("9", "30");
			month_days.put("10", "31");
			month_days.put("11", "30");
			month_days.put("12", "31");
		}
		int day = Integer.parseInt((String) month_days.get(String
				.valueOf(nextMonth)));

		StringBuffer sb = new StringBuffer(Constants.EMPTY_STRING);
		sb.append(buildTwoDigits(nextMonth)).append("-").append(
				buildTwoDigits(day)).append("-").append(year);
		return sb.toString();

	}

	/**
	 * special purpose routine to convert dates using the proprietary OEX date
	 * format representation which utilizes all uppercase and RRRR instead of
	 * YYYY for year representation. NOTE: this should only be used to convert
	 * date portion only, not any time values. Method does not support date
	 * formats like dd-MM-RRRR hh:mm
	 * 
	 * @param sDate
	 *            datetime value represented as a string
	 * @param currentFormat
	 *            the current datetime format represented in valid time format
	 * @param newFormat
	 *            the new datetime format represented in valid time format
	 * @exception ParseException
	 *                if time parsing error occurs
	 * @return datetime value as a string equivalent in the chosen time format
	 */
	public static String convertOEXDateFormat(String sDate,
			String currentFormat, String newFormat, Locale locale)
			throws ParseException {

		// convert OEX's RRRR year format to java YYYY format
		// String xCurrentFormat = currentFormat.toLowerCase().replace('r',
		// 'y');
		// String xNewFormat = newFormat.toLowerCase().replace('r', 'y');
		String xCurrentFormat = convertToJavaDatePattern(currentFormat);

		String xNewFormat = convertToJavaDatePattern(newFormat);
		return convertDateFormat(sDate, xCurrentFormat, xNewFormat, locale);
	}

	/**
	 * convert GNX/OEX dataformat pattern into standard java date format pattern
	 * 
	 * @param datePattern
	 *            string representation of GNX/OEX date format pattern
	 * @return java compliant date pattern
	 */
	public static String convertToJavaDatePattern(String datePattern) {

		// in the case of empty datePattern, use db default format
		if (StringUtil.isNullOrEmpty(datePattern))
			return Default.DB_DATE_FORMAT;
		String dp = datePattern.toLowerCase();
		int index;

		// convert mm to MM per Java month symbol
		index = dp.indexOf("mm");
		if (index != -1) {
			dp = dp.replace('m', 'M');
		}

		// convert OEX's rrrrr year standard to Java year symbol
		index = dp.indexOf("r");
		if (index != -1) {
			dp = dp.replace('r', 'y');
		}

		// convert OEX's mon month abbrev standard to Java month abrev.
		index = dp.indexOf("mon");
		if (index != -1) {
			StringBuffer sb = new StringBuffer(dp);
			sb.replace(index, index + 3, "MMM");
			dp = sb.toString();
		}
		return dp;
	}
	
	/**
	 * convert java date pattern to Oracle OEX dataformat pattern
	 */
	public static String convertToOexDatePattern(String datePattern) {

		String dp = datePattern;
		if (StringUtil.isNotNullOrEmpty(datePattern)) {
			dp = datePattern.toUpperCase();
			int index;

			// convert java YYYY to OEX's RRRR year 
			index = dp.indexOf("Y");
			if (index != -1) {
				dp = dp.replace('Y', 'R');
			}

			// convert java MMM to OEX's mon 
			index = dp.indexOf("MMM");
			if (index != -1) {
				StringBuffer sb = new StringBuffer(dp);
				sb.replace(index, index + 3, "MON");
				dp = sb.toString();
			}
		}
		return dp;
	}

	public static boolean isValidDate(String date, String dateFormat,
			Locale locale) {
		boolean result = false;
		logger.debug("date: " + date);
		logger.debug("dateFormat: " + dateFormat);
		logger.debug("Locale: " + locale);
		try {
			if (StringUtil.isNotNullOrEmpty(date)
					&& StringUtil.isNotNullOrEmpty(dateFormat)) {
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
				sdf.setLenient(false);
				Date newDate = sdf.parse(date);
				logger.debug("parsed date = " + newDate.toString());
				result = true;
			}
		} catch (ParseException pe) {
			//pe.printStackTrace();
			logger.warn("ParseException in isValidDate: " + pe.getMessage());
			result = false;
		}
		logger.debug("is date " + date + " valid ? " + result);
		return result;
	}
	
	// the checkDateStr is base on the default db date format: MM-DD-YYYY
	public static boolean isPastDate(String checkDateStr) {
		// Default database date format
		String dateFormatStr = Default.DB_DATE_FORMAT;
		logger.debug("checkDateStr=" + checkDateStr);

		boolean isPastDate = false;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
			java.util.Date checkDate = dateFormat.parse(checkDateStr);
			// isPastDate = !(checkDate.after(new java.util.Date()));
			String todayStr = dateFormat.format(new java.util.Date());
			java.util.Date today = dateFormat.parse(todayStr);
			isPastDate = today.after(checkDate);
		} catch (ParseException pe) {
			logger.error("Caught a ParseException when calling isPastDate():"
					+ pe.getMessage());
		}
		return isPastDate;
	}

	public static String threeBusinessDayBefore() {
		SimpleDateFormat f = new SimpleDateFormat("EEE,HH");
		long thisMoment = System.currentTimeMillis();
		Date thisMomentDate = new Date(thisMoment);
		String ss = f.format(thisMomentDate);
		String week = ss.substring(0, 3);
		long threeDayLeft = 0;

		if ((week.equalsIgnoreCase("FRI")) || (week.equalsIgnoreCase("THU"))) {
			// next day in the morning
			threeDayLeft = 3 * 24 * 60 * 60 * 1000;

		} else if ((week.equalsIgnoreCase("MON"))
				|| (week.equalsIgnoreCase("TUE"))
				|| (week.equalsIgnoreCase("WED"))) {
			threeDayLeft = 5 * 24 * 60 * 60 * 1000;
		}

		long threeDaysAgo = System.currentTimeMillis() - threeDayLeft;
		Date dd = new Date(threeDaysAgo);
		f = new SimpleDateFormat("MM-dd-yyyy");
		String ThreeDays = f.format(dd);

		return ThreeDays;
	}

	public static String fourBusinessDayBefore() {
		SimpleDateFormat f = new SimpleDateFormat("EEE,HH");
		long thisMoment = System.currentTimeMillis();
		Date thisMomentDate = new Date(thisMoment);
		String ss = f.format(thisMomentDate);
		String week = ss.substring(0, 3);
		long fourDayLeft = 0;

		if (week.equalsIgnoreCase("FRI")) {
			// next day in the morning
			fourDayLeft = 4 * 24 * 60 * 60 * 1000;

		} else if ((week.equalsIgnoreCase("MON"))
				|| (week.equalsIgnoreCase("TUE"))
				|| (week.equalsIgnoreCase("WED"))
				|| (week.equalsIgnoreCase("THU"))) {
			fourDayLeft = 6 * 24 * 60 * 60 * 1000;
		}

		long fourDaysAgo = System.currentTimeMillis() - fourDayLeft;
		Date dd = new Date(fourDaysAgo);
		f = new SimpleDateFormat("MM-dd-yyyy");
		String fourDays = f.format(dd);

		return fourDays;
	}

	public static String getDateOnDaysBefore(int days, String dateFormat,
			Locale locale) {
		SimpleDateFormat f = new SimpleDateFormat(dateFormat, locale);
		f.setTimeZone(TimeZone.getDefault());
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.DATE, -1 * days);
		String daysBefore = f.format(cal.getTime());
		logger.debug(days + " days before today, the date was: " + daysBefore);
		return daysBefore;
	}

	/**
	 * implemention of a toString method of a Gregorian calendar object
	 * 
	 * @param cal
	 *            calendar object representing a fixed date time and timezone
	 * @param pattern
	 *            String pattern that conforms to the java.text.SimpleDateFormat
	 *            pattern rules
	 * @param locale
	 *            Locale object used to localize the output
	 * @return a string representing the calendar's date and time properties
	 *         formatted according to the pattern and locale parameters
	 */
	public static String calendarToString(GregorianCalendar cal,
			String pattern, Locale locale) {

		Date calTime = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
		String timeStr = dateFormat.format(calTime);

		// get the system timezone
		TimeZone jvmTimeZone = TimeZone.getDefault();

		/*
		 * if local timezone is diff than calendar timezone, then adjust date by
		 * rawoffset delta.
		 * 
		 * This prevents the system from automatically adjusting the time when
		 * the date object is created via cal.getTime()
		 */
		int offsetDelta = cal.get(GregorianCalendar.ZONE_OFFSET)
				- jvmTimeZone.getRawOffset();

		// if the timezone offsets are different, adjust date object to
		// compensate for
		// the calendar timezone
		if (offsetDelta != 0) {
			Date newDate = cal.getTime();
			newDate.setTime(newDate.getTime() + offsetDelta);
			timeStr = dateFormat.format(newDate);
		}

		return timeStr;
	}

	/**
	 * implemention of a toString method of a Gregorian calendar object using
	 * default date pattern and default locale
	 * 
	 * @param cal
	 *            calendar object representing a fixed date time and timezone
	 * @return a string representing the calendar's date and time properties
	 *         formatted according to the date pattern parameter and locale
	 */
	public static String calendarToString(GregorianCalendar cal) {
		return calendarToString(cal, DEFAULT_DATE_PATTERN, Locale.getDefault());
	}

	/**
	 * implemention of a toString method of a Gregorian calendar object using a
	 * default date pattern
	 * 
	 * @param cal
	 *            calendar object representing a fixed date time and timezone
	 * @param locale
	 *            Locale object used to localize the output
	 * @return a string representing the calendar's date and time properties
	 *         formatted according to the pattern and locale parameters
	 */
	public static String calendarToString(GregorianCalendar cal, Locale locale) {
		return calendarToString(cal, DEFAULT_DATE_PATTERN, locale);
	}

	/**
	 * implemention of a toString method of a Gregorian calendar object using a
	 * default Locale
	 * 
	 * @param cal
	 *            calendar object representing a fixed date time and timezone
	 * @param pattern
	 *            String pattern that conforms to the java.text.SimpleDateFormat
	 *            pattern rules
	 * @return a string representing the calendar's date and time properties
	 *         formatted according to the pattern and locale parameters
	 */
	public static String calendarToString(GregorianCalendar cal, String pattern) {
		return calendarToString(cal, pattern, Locale.getDefault());
	}

	/**
	 * converts date object to Gregorian Calendar.
	 * 
	 * Takes care of adjusting the time per the given timezone given that a date
	 * object has no timezone representation.
	 * 
	 * @param date
	 *            Date object to convert
	 * @param tz
	 *            TimeZone calendar will use be set to this timezone
	 * @return GregorianCalendar object set to a specific TimeZone
	 */
	public static GregorianCalendar dateToCalendar(Date date, TimeZone tz) {
		TimeZone localZone = TimeZone.getDefault();

		/*
		 * compute the difference in milliseconds between the local timezone
		 * offset and the specified timezone offset.
		 */
		long offsetDelta = tz.getRawOffset() - localZone.getRawOffset();

		/*
		 * adjust the epoch time to the specified timezone
		 */
		date.setTime(date.getTime() - offsetDelta);

		GregorianCalendar cal = new GregorianCalendar(tz);
		cal.setTime(date);
		return cal;
	}

	/**
	 * convert a String date to Date object
	 * 
	 * @param dateStr
	 *            the date to convert
	 * @param datePattern
	 *            date pattern used to parse the string to a Date
	 * @exception XxxxxxException
	 *                if ...
	 * @return a Date object converted from String
	 */
	public static Date stringToDate(String dateStr, String datePattern,
			Locale locale) throws ParseException {

		String javaDatePattern = convertToJavaDatePattern(datePattern);
		SimpleDateFormat sdf = new SimpleDateFormat(javaDatePattern, locale);
		return sdf.parse(dateStr);

	}
	
	public static Date toDate(String dateStr, String dateFormat,
			Locale locale) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		return sdf.parse(dateStr);
	}

	/**
	 * convert a String date to Date object
	 * 
	 * @param dateStr
	 *            the date to convert
	 * @param datePattern
	 *            date pattern used to parse the string to a Date
	 * @exception XxxxxxException
	 *                if ...
	 * @return a Date object converted from String
	 */
	public static Date stringToDate(String dateStr, String datePattern)
			throws ParseException {

		return stringToDate(dateStr, datePattern, Locale.getDefault());

	}
	
	public static String toString(Date date, String dateFormat) {		
		return toString(date, dateFormat, Locale.ENGLISH);
	}
	
	public static String toString(Date date, String dateFormat, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		sdf.setDateFormatSymbols(new DateFormatSymbols(locale));
		return sdf.format(date);
	}
	/**
	 * converts OEX internal timezone code to an abbreviated timezone display
	 * name Example: Australia/Brisbane --> EST
	 * 
	 * @param oexTimeZoneCode
	 *            internal timezone code used by OEX, same as the timezone value
	 *            stored in Reg.Person table
	 * @return timezone abbreviation if code maps to a valid timezone, else
	 *         returns GMT otherwise
	 */
	public static String getTimeZoneAbbrev(String oexTimeZoneCode) {
		// TimeZone tz = TimeZone.getTimeZone(oexTimeZoneCode);
		TimeZone tz = TimeZone.getTimeZone(oexTimeZoneCode);
		String shortName = tz.getDisplayName(true, TimeZone.SHORT);
		return shortName;
	}

	/**
	  * find if fromDate is greater than toDate where the dates are given as
	  * Strings
	  * 
	  * @param fromDateStr the fromDate
	  * @param toDateStr the toDate
	  * @param datePattern date pattern used to parse the string to a Date
	  * @exception ParseException
	  * @return true if fromDate is greater than toDate , false otherwise.
	  */
	 public static boolean compareDates(String fromDateStr, String toDateStr, String datePattern)
	 {
	     boolean flage = false;
	     Date fromDate;
	     try {
	         fromDate = stringToDate(fromDateStr, datePattern);
	         Date toDate = stringToDate(toDateStr, datePattern);
	         if (fromDate.after(toDate)) {
	             flage = true;
	         }
	     }
	     catch (ParseException e) {
	         e.printStackTrace();
	     }
       return flage;
   }
}