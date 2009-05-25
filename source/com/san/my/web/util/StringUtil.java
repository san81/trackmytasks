package com.san.my.web.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.san.my.common.global.Constants;



/**
 * Basic Utility Class
 * Creation date: (10/09/02 )
 * @author: Yibing 
 * Modified by Xiaozhu Zhu (10/14/02)
 */

public class StringUtil {
	private char DELIMITER = ',';
	final static String EMPTY_STRING = Constants.EMPTY_STRING;
	private char FIELD_ENCLOSING = '"';

	public StringUtil(char delimiter, char fieldEnclosing) {
		DELIMITER = delimiter;
		FIELD_ENCLOSING = fieldEnclosing;
	}

	public StringUtil() {
	}

	public static String trim(String text) {
		String result = Constants.EMPTY_STRING;
		if (text != null)
			result = text.trim();
		return result;
	}
	
	public static String getFullName(String f_name, String l_name){
		String full_name = Constants.EMPTY_STRING;
		if (isNotNullOrEmpty(l_name)){
			full_name = l_name;
		}
		if (isNotNullOrEmpty(f_name)){
			full_name = f_name + Constants.ONE_WHITE_SPACE + full_name;
		}
		return full_name;
	}
	
	public static String removeDecimalPart(String number) {
		int start = 0;
		int end = 0;
		if (number != null) {
			int ndx = number.indexOf('.');
			end = number.length();
			if ((number.length() < 2) || (ndx == -1)) {
				return number;
			}
			if (ndx == 0) {
				start = 1;
			} else {
				end = ndx;
			}
		}
		return number.substring(start, end);
	}
	
	//strip the double occurences of the quote ("") chars from the string
	public String strip2Quotes(String s) {
		if (s == null)
			return null;
		char c;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			if (i == s.length() - 1) {
				c = s.charAt(i);
				sb.append(c);
				break;
			}
			c = s.charAt(i);
			char c1 = s.charAt(i + 1);

			if (c == FIELD_ENCLOSING && c1 == FIELD_ENCLOSING) {
				i++;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String strip(String s) {
		if (s == null)
			return null;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c != '-' && c != '(' && c != ')')
				sb.append(c);

		}
		return sb.toString();
	}

	public static String dupQuote(String s) {
		if (s == null)
			return null;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\'')
				sb.append(c);
			sb.append(c);
		}
		return sb.toString();
	}

	public static String quoteComma(String s) {
		if (s == null)
			return null;

		StringBuffer sb = new StringBuffer();
		sb.append("\"").append(s).append("\"");
		return sb.toString();
	}

	public static String stripComma(String s) {
		if (s == null)
			return null;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ',')
				sb.append(' ');
			else
				sb.append(c);
		}
		return sb.toString();
	}

	public ArrayList parse(String s) {
		s = strip2Quotes(s);
		int k = 0;
		ArrayList results = new ArrayList();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == FIELD_ENCLOSING) {
				// find the next " symbol 
				int j = s.indexOf(FIELD_ENCLOSING, i + 1);
				if (j < 0)
					return results;
				String comp = s.substring(i + 1, j);
				results.add(comp);
				i = j + 1;
				k = i + 1;
			} else if (c == DELIMITER) {

				String simp = s.substring(k, i);
				k = i + 1;
				results.add(simp);
			}
		}
		if (k < s.length())
			results.add(s.substring(k, s.length()));

		return results;
	}

	public static ArrayList getSubList(ArrayList list, int startIndex,
			int endIndex) {
		ArrayList nList = new ArrayList();
		if (list.size() <= startIndex)
			return list;
		int k = endIndex + 1;
		if (k > list.size())
			k = list.size();
		for (int i = startIndex; i < k; i++)
			nList.add(list.get(i));
		return nList;

	}

	public static int isExisting(String line, ArrayList list) {
		if (line == null)
			return 0;

		for (int i = 0; i < list.size(); i++) {

			if (line.equalsIgnoreCase((String) list.get(i)))
				return i + 1;

		}
		return 0;
	}

	public static ArrayList parseDateTimeString(String dateTimeStr) {

		if (dateTimeStr == null || dateTimeStr.equals(Constants.EMPTY_STRING))
			return null;
		dateTimeStr = dateTimeStr.trim();
		System.out.println("dateTimeStr=" + dateTimeStr);

		//assume the datetime string is like: 15-JAN-2003,09:07:AM
		ArrayList results = new ArrayList();
		int temp = dateTimeStr.indexOf(" ");
		String dateStr = dateTimeStr.substring(0, temp);
		int temp1 = dateTimeStr.indexOf(":");
		String hourStr = dateTimeStr.substring(temp + 1, temp1);
		if (hourStr.length() == 1){
			//append a zero if hourStr has only one digit 1-9
			hourStr = "0"+hourStr;
		}
		int temp2 = dateTimeStr.lastIndexOf(" ");
		String minuteStr = dateTimeStr.substring(temp1 + 1, temp2);
		String amPm = dateTimeStr.substring(temp2 + 1);
		results.add(dateStr);
		results.add(hourStr);
		results.add(minuteStr);
		results.add(amPm);
		return results;
	}

	public static boolean isNullOrEmpty(String value) {	
		boolean retVal = false;
		if (value == null){
			retVal = true;
		} else if (EMPTY_STRING.equals(value.trim())) {
			retVal = true;
		}
		return retVal;
	}
	
	public static boolean isNotNullOrEmpty(String value) {	
		return !isNullOrEmpty(value);
	}	

	/**
	 * remove characters classified as whitespace from a string
	 * @param source  string to cleanse
	 * @return string with whitespace characters removed
	 */
	public static String stripWhiteSpace(String source) {
		String result = null;
		if (isNotNullOrEmpty(source)) {
			char[] chars = source.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < chars.length; i++) {
				if (!Character.isWhitespace(chars[i])) {
					sb.append(chars[i]);
				}
			}
			result = sb.toString();
		}
		return result;
	}

	/**
	 * remove punctuation characters from a string
	 * @param source  the string to cleanse
	 * @return string containing only characters
	 * classified as letters or digits
	 */
	public static String stripPunctuation(String source) {
		char[] chars = source.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isLetterOrDigit(chars[i])) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * determine if string contains only latin characters
	 * per Unicode standards
	 * @param source  string to check
	 * @return true if string contains only latin charachters
	 * false otherwise
	 */
	public static boolean isLatinCharSet(String source) {

		for (int i = 0; i < source.length(); i++) {
			char x = source.charAt(i);
			if (x > 0x24f) {
				return false;
			}
		}
		return true;
	}

	/**
	 * determine if string contains only letters or digits
	 * per Unicode standards
	 * @param source  string to check
	 * @return true if contains only letters or digits,
	 * false otherwise
	 */
	public static boolean isLettersOrDigits(String source) {
		char[] chars = source.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetterOrDigit(chars[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Transfer string array to ArrayList
	 * @param source  string array
	 * @return ArrayList
	 */
	public static ArrayList StringArrayToList(String[] source) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < source.length; i++) {
			list.add(source[i]);
		}
		return list;
	}



	
	/**
	 * Transfer string array to String separated with empty space
	 * @param source  string array
	 * @return String
	 */
	public static String convertArrayToString(String[] source) {
		String result = null;
		if (source != null && source.length > 0) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < source.length; i++) {
				sb.append(source[i]).append(" ");
			}
			result = sb.toString().trim();
		}
		return result;
	}

	/**
	 * convert a string date to java.util.Date
	 * @param sDate string date parseable in MM-dd-yyyy format
	 * @return java.util.Date object
	 */
	public static Date toDate(String sDate) {
		if (sDate == null)
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date _sDate = null;
		try {
			_sDate = sdf.parse(sDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return _sDate;
	}

	public static final String[] split(String s, char delimiter) {
		ArrayList vec = new ArrayList();

		if (s == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != delimiter)
				sb.append(c);
			else {
				vec.add(sb.toString().trim());
				sb = new StringBuffer();
			}

		}
		vec.add(sb.toString().trim());
		String[] arrStr = new String[vec.size()];
		for (int i = 0; i < vec.size(); i++)
			arrStr[i] = (String) vec.get(i);

		return arrStr;

	}

	public static boolean containSpecialCharacters(String s) {
		if (s == null)
			return false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == '"' || c == '<' || c == '>')
				return true;

		}
		return false;
	}

	public static void writeOutput(String str) {
		try {
			FileOutputStream fos = new FileOutputStream("test.txt");
			Writer out = new OutputStreamWriter(fos, "UTF8");
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static String readInput() {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream("test.txt");
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			Reader in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);

			}
			in.close();
			return buffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String singleQuote(String org) {		
		if (isNullOrEmpty(org)) {
			return null;
		} else {
			return "'" + dupQuote(org) + "'";
		}
	}
	
	/**
	 * determine if string contains any
	 * Central European characters
	 * per Unicode standards
	 * @param source  string to check
	 * @return true if string contains any 
	 * central european chars,
	 * false otherwise
	 */
	public static boolean hasCentralEuropeanChars(String source) {
		for (int i = 0; i < source.length(); i++) {
			char x = source.charAt(i);
			if (x >= 0x100 && x <= 0x17f) {
				return true;
			}
		}
		return false;
	}

	/**
	 * determine if string contains any
	 * Cryllic characters
	 * per Unicode standards
	 * @param source  string to check
	 * @return true if string contains any 
	 * Cryllic chars, false otherwise
	 */
	public static boolean hasCryllicChars(String source) {
		for (int i = 0; i < source.length(); i++) {
			char x = source.charAt(i);
			if (x >= 0x400 && x <= 0x52f) {
				return true;
			}
		}
		return false;
	}

	/**
	 * determine if string contains any
	 * special Turkish characters
	 * @param source  string to check
	 * @return true if string contains any 
	 * special turkish chars,
	 * false otherwise
	 */
	public static boolean hasTurkishChars(String source) {
		for (int i = 0; i < source.length(); i++) {
			char x = source.charAt(i);
			if ((x >= 0x11e && x <= 0x11f) || (x >= 0x130 && x <= 0x131)
					|| (x >= 0x15e && x <= 0x15f)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * determine if string contains any
	 * special Turkish I character
	 * @param source  string to check
	 * @return true if string contains any 
	 * special turkish chars,
	 * false otherwise
	 */ 
	  
	public static boolean hasTurkishAlphabetI(String source) {
		return ((source.indexOf(Constants.TURKISH_I_LOWER) != -1)
				||(source.indexOf(Constants.TURKISH_I_UPPER) != -1));
	}

	/**
	 * truncates string at max length.
	 * if string length < max length, string is returned as is
	 * @param value
	 * @param max_length
	 * @return truncated string
	 */
	public static String truncate(String value, int max_length) {
		String newValue = value;

		if (value != null && value.length() > max_length) {
			newValue = value.substring(0, max_length - 1);
		}
		return newValue;
	}

	/**
	 * returns Integer object representing the number in the string text 
	 * @param text
	 * @return Integer value of string if its a valid number, null otherwise
	 */

	public static Integer getIntegerValue(String text) {
		Integer number = null;
		try {
			number = Integer.valueOf(text);
		} catch (Exception e) {
			//ignore in case of format exceptions and send back null
		}
		return number;
	}

	private static final String JS_DELIMITER_1 = "\'";

	public static String getEscapedStringForJS(String text) {
		String result = Constants.EMPTY_STRING;
		String delimiter = JS_DELIMITER_1;
		StringTokenizer st = new StringTokenizer(text, delimiter, true);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			if (!w.equals(delimiter) && st.hasMoreTokens()) {
				result = result + w + "\\";
			} else {
				result = result + w;
			}
		}
		return result;
	}

	public static void main(String[] args) {

		System.out.println(StringUtil.getIntegerValue("abcd"));
		StringUtil.writeOutput("averk\u00e4ufer");
		String inputString = readInput();
		System.out.println("inputString=" + inputString);

		boolean rdout = StringUtil.containSpecialCharacters("ABC xv");
		System.out.println("rdout=" + rdout);
		String[] source = { "Yibing", "Li" };
		ArrayList list = StringUtil.StringArrayToList(source);
		System.out.println("size of list=" + list.size());
		String dateTimeStr = "01/02/2003 10:00 AM";
		ArrayList results = StringUtil.parseDateTimeString(dateTimeStr);
		String dateStr = (String) results.get(0);
		String hourStr = (String) results.get(1);
		String minuteStr = (String) results.get(2);
		String amPm = (String) results.get(3);
		System.out.println("dateStr=" + dateStr);
		System.out.println("hourStr=" + hourStr);
		System.out.println("minuteStr=" + minuteStr);
		System.out.println("amPm=" + amPm);

	}
	
	public static List<Long> convertStringToList(String string){
		List<Long> list = new ArrayList<Long>();
		StringTokenizer stringTokenizer = new StringTokenizer(string,",",false);
		
		while(stringTokenizer.hasMoreTokens()){
			list.add(Long.parseLong(stringTokenizer.nextToken()));
		}
		return list;
	}

}