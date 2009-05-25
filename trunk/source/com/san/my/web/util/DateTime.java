package com.san.my.web.util;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.san.my.common.global.Default;

public class DateTime {

	static Logger logger = Logger.getLogger(DateTime.class);

	Date date;
	TimeZone timezone;
	
	/**
	 * instantiate an object to store date, time with timezone and locale information
	 * @param _dateTime date and time string eg., 2007/01/01 12:12 PM
	 * @param _format java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @param _timeZone timezone code eg., EST, IST
	 * @param locale desired locale eg., Locale.US
	 */
	public DateTime(String _dateTime, String _format, String _timeZone, Locale locale) {
		init(_dateTime, _format, _timeZone, locale);
	}
	
	/**
	 * instantiate an object to store date, time with timezone in default locale.
	 * @param _dateTime date and time string eg., 2007/01/01 12:12 PM
	 * @param _format java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @param _timeZone timezone code eg., EST, IST
	 * @see Default 
	 */
	public DateTime(String _dateTime, String _format, String _timeZone) {
		init(_dateTime, _format, _timeZone, Default.LOCALE);
	}
	
	/**
	 * instantiate an object to store date, time in default timezone and locale info
	 * @param _dateTime date and time string eg., 2007/01/01 12:12 PM
	 * @param _timeZone timezone code eg., EST, IST
	 * @param locale desired locale eg., Locale.US
	 * @see Default
	 */
	public DateTime(String _dateTime, String _format, Locale locale) {
		init(_dateTime, _format, Default.APP_TIMEZONE, locale);
	}
	
	/**
	 * instantiate an object to store date, time in default timezone and default locale.
	 * Use this to store source date for date format conversion
	 * @param _dateTime date and time string eg., 2007/01/01 12:12 PM
	 * @param _format java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @see Default
	 */
	public DateTime(String _dateTime, String _format) {
		init(_dateTime, _format, Default.APP_TIMEZONE, Default.LOCALE);
	}

	
	private void init(String _dateTime, String _format, String _timeZone, Locale locale) {
		timezone = TimeZone.getTimeZone(_timeZone);
		SimpleDateFormat dateFormat = new SimpleDateFormat(_format, locale);
		dateFormat.setTimeZone(timezone);
		ParsePosition pos = new ParsePosition(0);
		date = dateFormat.parse(_dateTime, pos);
	}
	
	/**
	 * instantiate an object to store date, time with timezone in default locale.
	 * @param _date java.util.Date 
	 * @param _timeZone timezone code eg., EST, IST
	 * @see Default
	 */
	public DateTime(Date _date, String _timeZone) {
		timezone = TimeZone.getTimeZone(_timeZone);
		//logger.debug("date : " + _date.toString());
		Calendar cal = new GregorianCalendar(timezone);
		cal.setTime(_date);		
		date = cal.getTime();
		
		//logger.debug("date : " + date.toString());
		//logger.debug("tz: " + timezone);
		//date = _date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DateTime convertTime(String timeZone) {
		Calendar calOld = new GregorianCalendar(timezone);
		calOld.setTime(date);
		Date newDate = calOld.getTime();
		
		DateTime newDateTime = new DateTime(newDate, timeZone);
		return newDateTime;
	}
	
	/**
	 * converts the stored date/time to requested timezone and returns
	 * a string representation of the date/time in requested locale
	 * @param dateFormat	java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @param timeZone timezone code eg., EST, IST
	 * @param locale desired locale eg., Locale.US
	 * @return string representation of converted date/time in requested locale
	 */
	public String toString(String dateFormat, String timeZone, Locale locale) {
		DateTime newDateTime = this.convertTime(timeZone);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		sdf.setDateFormatSymbols(new DateFormatSymbols(locale));
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(newDateTime.getDate());
	}
	
	/**
	 * converts the stored date/time to requested timezone and returns
	 * a string representation of the date/time in default locale
	 * @param dateFormat	java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @param timeZone timezone code eg., EST, IST
	 * @return string representation of converted date/time in default locale
	 * @see Default
	 */
	public String toString(String dateFormat, String timeZone) {
		return toString(dateFormat, timeZone, Default.LOCALE);
	}
	
	/**
	 * converts the stored date/time to default timezone and formats
	 * date/time in requested format and locale.
	 * Use this for format and locale conversion for dates 
	 * @param dateFormat	java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @param locale desired locale eg., Locale.US
	 * @return string representation of date/time in requested format and locale
	 */
	public String toString(String dateFormat, Locale locale) {
		return toString(dateFormat, Default.APP_TIMEZONE, locale);
	}
	
	/**
	 * converts the stored date/time to default timezone and formats
	 * date/time in requested format and default locale.
	 * Use this for format conversion for dates 
	 * @param dateFormat	java date/time pattern eg., yyyy/MM/dd hh:mm aaa
	 * @return string representation of date/time in requested format
	 * @see Default	 
	 */
	public String toString(String dateFormat) {
		return toString(dateFormat, Default.APP_TIMEZONE);
	}
	
	/**
	 * converts the stored date/time to requested timezone and returns
	 * a string representation of the date/time in default format
	 * @param timeZone timezone code eg., EST, IST
	 * @param locale desired locale eg., Locale.US	 
	 * @return string representation of converted date/time in requested locale and default format
	 * @see Default
	 */
	public String toDefaultDateTimeString(String timeZone, Locale locale){
		return toString(Default.APP_DATE_TIME_FORMAT, timeZone, locale);
	}
	
	/**
	 * converts the stored date/time to requested timezone and returns
	 * a string representation of the date/time in default format
	 * @param timeZone timezone code eg., EST, IST
	 * @return string representation of converted date/time in default locale and format
	 * @see Default
	 */
	public String toDefaultDateTimeString(String timeZone){
		return toString(Default.APP_DATE_TIME_FORMAT, timeZone);
	}
	
	/**
	 * converts the stored date/time to default timezone and returns
	 * a string representation of the date/time in default format
	 * @return string representation of converted date/time in default locale and format
	 * @see Default
	 */
	public String toDefaultDateTimeString(){
		return toString(Default.APP_DATE_TIME_FORMAT, Default.APP_TIMEZONE);
	}
	
	/**
	 * converts the stored date/time to default timezone and returns
	 * a string representation of the date/time in default format
	 * @param locale desired locale eg., Locale.US	
	 * @return string representation of converted date/time in requested locale and default format
	 * @see Default
	 */
	public String toDefaultDateTimeString(Locale locale){
		return toString(Default.APP_DATE_TIME_FORMAT, Default.APP_TIMEZONE, locale);
	}
	
	public static void main(String[] args) {
	}
}
