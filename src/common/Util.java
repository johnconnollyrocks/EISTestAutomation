package common;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.commons.lang.RandomStringUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import common.exception.TestDataException;

/**
 * Generic utility methods 
 * 
 * @author Jeffrey Blaze
 * @version 1.0.0
 */
public final class Util {
	public static boolean debugMode = false;
	public static boolean silentMode = false;

	public static List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line = null;
        BufferedReader bufferedReader = null;

		try {
	        bufferedReader = new BufferedReader(new FileReader(filename));
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line.trim());
	        }
		} catch(FileNotFoundException fe) {
			throw new FileNotFoundException("File '" + filename + "' not found: " + fe.getMessage());
		} catch(IOException ioe) {
			throw new IOException("Error while reading '" + filename + "': " + ioe.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ioe) {
					throw new IOException("Caught IOException in finally block while reading '" + filename + "': " + ioe.getMessage());
				}
			}
		}

		return lines;
	}

	public static List<String[]> readKeyValuePairs(String filename, String delim, String commentToken) throws IOException {
        List<String> lines = new ArrayList<String>();
        List<String[]> keyValuePairs = new ArrayList<String[]>();
        String[] keyValuePair = new String[2];

        lines = readFile(filename);

          for (String line : lines) {
        	line = line.trim();

        	if ((line.length() > 0) && !(line.startsWith(commentToken))) {
	        	keyValuePair = line.split(delim, 2);

	        	if (keyValuePair.length == 2) {
		        	keyValuePair[0] = keyValuePair[0].trim();
		        	if (keyValuePair[0].length() != 0) {
			        	keyValuePair[1] = keyValuePair[1].trim();

			        	keyValuePairs.add(keyValuePair);
		        	}
	        	}
        	}
        }

        return keyValuePairs;
	}

	public static List<String[]> readKeyValuePairs(String filename, String delim)  throws IOException {
        return readKeyValuePairs(filename, delim, EISConstants.COMMENT_TOKEN);
	}

	public static List<String[]> readKeyValuePairs(String filename)  throws IOException {
        return readKeyValuePairs(filename, "=", EISConstants.COMMENT_TOKEN);
	}

	//This version print warnings instead of failing the test when it catches file I/O exceptions
/*	public static Properties loadPropertiesFile(String filename) {
		Properties properties = new Properties();
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(filename);
			properties.load(inputStream);
		} catch(FileNotFoundException e) {
			printWarning("Caught FileNotFoundException: " + e.getMessage());
		} catch(IOException e) {
			printWarning("Caught IOException: " + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					printWarning("Caught IOException in finally block: " + e.getMessage());
				}
			}
		}

		return properties;
	}
*/
	//This version fails the test when it catches file I/O exceptions
	public static Properties loadPropertiesFile(String filename) {
		Properties properties = new Properties();
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(filename);
			properties.load(inputStream);
		} catch(FileNotFoundException e) {
			//failTest("Error while loading properties from '" + filename + "': " + e.getMessage());
			EISTestBase.failTest("Error while loading properties from '" + filename + "': " + e.getMessage());
		} catch(IOException e) {
			//failTest("Error while loading properties from '" + filename + "': " + e.getMessage());
			EISTestBase.failTest("Error while loading properties from '" + filename + "': " + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//failTest("Error in finally block while loading properties from '" + filename + "': " + e.getMessage());
					EISTestBase.failTest("Error in finally block while loading properties from '" + filename + "': " + e.getMessage());
				}
			}
		}

		return properties;
	}

	public static void storePropertiesFile(String filename, Properties properties) {
		FileOutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(new File(filename));
			properties.store(outputStream, "");
		} catch(FileNotFoundException e) {
			printWarning("Caught FileNotFoundException: " + e.getMessage());
		} catch(IOException e) {
			printWarning("Caught IOException: " + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					printWarning("Caught IOException in finally block: " + e.getMessage());
				}
			}
		}

		return;
	}

	public static boolean fileExists(String filePath) {
		File file = new File(filePath);
		
		return file.exists();
	}
	
	public static String getCurrentDate(String outputFormat) {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        return dformat.format(new Date());
    }

    public static String getCurrentDate() {
    	return getCurrentDate(EISConstants.DEFAULT_DATE_FORMAT);
    }

	public static String getCurrentDate(int dateFormat) {
		return DateFormat.getDateInstance(dateFormat).format(new Date());
    }

	public static String getCurrentDateTime(int dateFormat, int timeFormat) {
        return DateFormat.getDateTimeInstance(dateFormat, timeFormat).format(new Date());
	}

	public static String getYesterday(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getYesterday() {
    	return getYesterday(EISConstants.DEFAULT_DATE_FORMAT);
    }
		
	public static String getToday(String outputFormat) {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        return dformat.format(new Date());
    }

    public static String getToday() {
    	return getCurrentDate(EISConstants.DEFAULT_DATE_FORMAT);
    }

	public static String getTomorrow(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.DAY_OF_YEAR, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getTomorrow() {
    	return getTomorrow(EISConstants.DEFAULT_DATE_FORMAT);
    }
		
    public static String getDateNextDay(Date startDate, String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        
        cal.add(Calendar.DAY_OF_YEAR, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateNextDay(Date startDate) {
    	return getDateNextDay(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateNextDay(String startDate, String outputFormat) throws ParseException {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        Date baseDate = new Date();
		baseDate = dformat.parse(startDate);
		
        return getDateNextDay(baseDate, outputFormat);
    }
   
	public static String getDateNextDay(String startDate) throws ParseException {
    	return getDateNextDay(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDatePriorDay(Date startDate, String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDatePriorDay(Date startDate) {
    	return getDatePriorDay(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDatePriorDay(String startDate, String outputFormat) throws ParseException {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        Date baseDate = new Date();
		baseDate = dformat.parse(startDate);
		
        return getDatePriorDay(baseDate, outputFormat);
    }
   
	public static String getDatePriorDay(String startDate) throws ParseException {
    	return getDatePriorDay(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

	public static String getDateFirstDayThisMonth(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getDateFirstDayThisMonth() {
    	return getDateFirstDayThisMonth(EISConstants.DEFAULT_DATE_FORMAT);
    }
		
	public static String getDateLastDayThisMonth(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getDateLastDayThisMonth() {
    	return getDateLastDayThisMonth(EISConstants.DEFAULT_DATE_FORMAT);
    }
		
    public static String getDateFirstDayNextMonth(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getDateFirstDayNextMonth() {
    	return getDateFirstDayNextMonth(EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateFirstDayThisYear(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.set(Calendar.DAY_OF_YEAR, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateFirstDayThisYear() {
    	return getDateFirstDayThisYear(EISConstants.DEFAULT_DATE_FORMAT);
    }

	public static String getDateLastDayThisYear(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }

	public static String getDateLastDayThisYear() {
    	return getDateLastDayThisYear(EISConstants.DEFAULT_DATE_FORMAT);
    }
		
    public static String getDateFirstDayNextYear(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.DAY_OF_YEAR, 1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateFirstDayNextYear() {
    	return getDateFirstDayNextYear(EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateMonthTerm(Date startDate, String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateMonthTerm(Date startDate) {
    	return getDateMonthTerm(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateMonthTerm(String startDate, String outputFormat) throws ParseException {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        Date baseDate = new Date();
		baseDate = dformat.parse(startDate);
		
        return getDateMonthTerm(baseDate, outputFormat);
    }
   
	public static String getDateMonthTerm(String startDate) throws ParseException {
    	return getDateMonthTerm(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateMonthTermFromToday(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateMonthTermFromToday() {
    	return getDateMonthTermFromToday(EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateYearTerm(Date startDate, String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateYearTerm(Date startDate) {
    	return getDateYearTerm(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateYearTerm(String startDate, String outputFormat) throws ParseException {
        DateFormat dformat = new SimpleDateFormat(outputFormat);

        Date baseDate = new Date();
		baseDate = dformat.parse(startDate);
		
        return getDateYearTerm(baseDate, outputFormat);
    }
   
	public static String getDateYearTerm(String startDate) throws ParseException {
    	return getDateYearTerm(startDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String getDateYearTermFromToday(String outputFormat) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);

        return new SimpleDateFormat(outputFormat).format(cal.getTime());
    }
   
	public static String getDateYearTermFromToday() {
    	return getDateYearTermFromToday(EISConstants.DEFAULT_DATE_FORMAT);
    }

/*    public static String formatDate(String stringDate, String outputFormat) {
 		String[] validParseFormats = {"MM/dd/yy", "MM-dd-yy", "MM.dd.yy"};
    	DateFormat df;
    	Date date = null;
    	boolean success = false;
    	String formattedDate = "";
    	
    	for (String format : validParseFormats) {
    		df = new SimpleDateFormat(format);
    		
    		try {
    			date = df.parse(stringDate);
    			success = true;
    			
    			break;
    		} catch (ParseException pe) {}
    	}
    	
    	if (success) {
    		df = new SimpleDateFormat(outputFormat);
    		formattedDate = df.format(date);
     	}
    	
    	return formattedDate;
    }
*/
    
    public static String formatDate(String stringDate, String outputFormat) {
    	//These represent the valid formats of the passed-in date, stringDate.
    	//  The output format is defined in outputFormat
    	//NOTE See comments at EISConstants.DEFAULT_DATE_FORMAT
 		String[] validParseFormats = {"MM/dd/yy", "MM-dd-yy", "MM.dd.yy"};
 		//String[] validParseFormats =   {"dd/MM/yy", "dd-MM-yy", "dd.MM.yy"};

 		DateFormat df;
    	Date date = null;
    	boolean success = false;
    	String formattedDate = "";
    	
    	//If stringDate is empty, we will pass it through.  This allows the user to specify
    	//  empty date fields for error checking
    	if (!stringDate.isEmpty()) {
	    	for (String format : validParseFormats) {
	    		df = new SimpleDateFormat(format);
	    		
	    		try {
	    			date = df.parse(stringDate);
	    			success = true;
	    			
	    			break;
	    		} catch (ParseException pe) {}
	    	}
	    	
	    	if (success) {
	    		df = new SimpleDateFormat(outputFormat);
	    		formattedDate = df.format(date);
	     	} else {
	     		formattedDate = EISConstants.INVALID_INPUT;
	     	}
    	}
    	
    	return formattedDate;
    }

    public static String formatDate(String stringDate) {
    	return formatDate(stringDate, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(String stringDate, int dateFormat) {
    	String formattedDate = formatDate(stringDate);
    	DateFormat df = DateFormat.getDateInstance(dateFormat);
    	
		try {
			formattedDate = df.format(df.parse(formattedDate));
		} catch (ParseException e) {}

    	return formattedDate;
    }

    public static final String formatNumber(double number, String format) {
    	return String.format(format, number);
    }    
    
    public static String addDates(String startDate, int field, int offset, String dateFormat) throws ParseException {
        DateFormat dformat = new SimpleDateFormat(dateFormat);

        Date baseDate = new Date();
		baseDate = dformat.parse(startDate);
		
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.add(field, offset);

        return dformat.format(cal.getTime());
    }

    public static String addDates(String startDate, int field, int offset) throws ParseException {
    	return addDates(startDate, field, offset, EISConstants.DEFAULT_DATE_FORMAT);
    }

    public static Date addDates(Date startDate, int field, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(field, offset);

        return cal.getTime();
    }

    public static Date addDates(int field, int offset) {
        return addDates(new Date(), field, offset);
    }

	public static String getTimestamp(String format) {
    	return getCurrentDate(format);
	}
	
	public static String getTimestamp() {
        return getTimestamp(EISConstants.DEFAULT_TIMESTAMP_FORMAT);
	}
	
	public static String getTimestamp(int dateFormat, int timeFormat) {
        return getCurrentDateTime(dateFormat, timeFormat);
	}
	
	public static String left(String value, int length) {
		String left = value;
		
		try {
			left = value.substring(0, length);
		} catch (IndexOutOfBoundsException e) {}

		return left;
	}
	
	public static String right(String value, int length) {
		String right = value;

		try {
			right = value.substring(value.length() - length);
		} catch (IndexOutOfBoundsException e) {}

		return right;
	}

	public static String left(String value, String delim) {
		String subString = "";
		int index;
		
		index = value.indexOf(delim);
		if (index >= 0) {
			subString = value.substring(0, index);
		}

		return subString;
	}
	
	public static String right(String value, String delim) {
		String subString = "";
		int index;
		
		index = value.indexOf(delim);
		if (index >= 0) {
			subString = value.substring(index + 1, value.length());
		}

		return subString;
	}
	
	public static String buildDelimitedString(String[] values, String delim) {
		String valueString = "";
		
		int length = values.length;
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				valueString += values[i];
				
				if (i < (length - 1)) {
					valueString += delim;
				}
			}
		}
		
		return valueString;
	}

	//This version stops parsing when it hits a non-digit, non-sign
/*	public static int getIntegerFromString(String inString) {
		int number = 0;
		String stringNumber = "";
		char character;
		boolean foundNumber = false;
		int sign = 1;
		
		for (int i = 0; i < inString.length(); i++) {
			character = inString.charAt(i);
			
			if (Character.isDigit(character)) {
				stringNumber += character;
				
				foundNumber = true;
			} else {
				if (Character.toString(character).equals("-")) {
					sign = -1;
				} else {
					if (foundNumber) {
						break;
					}
				}
			}
		}
		
		if (foundNumber) {
			try {
				number = Integer.parseInt(stringNumber) * sign;
			} catch (NumberFormatException e) {}
		}
		
		return number;
	}
*/
	//This version parses the entire string
	public static int getIntegerFromString(String inString) {
		int number = 0;
		String stringNumber = "";
		char character;
		boolean foundNumber = false;
		int sign = 1;
		
		for (int i = 0; i < inString.length(); i++) {
			character = inString.charAt(i);
			
			if (Character.isDigit(character)) {
				stringNumber += character;
				
				foundNumber = true;
			} else {
				if (Character.toString(character).equals("-")) {
					sign = -1;
				}
			}
		}
		
		if (foundNumber) {
			try {
				number = Integer.parseInt(stringNumber) * sign;
			} catch (NumberFormatException e) {}
		}
		
		return number;
	}

	public static double getDoubleFromString(String inString) {
		double number = 0;
		String stringNumber = "";
		char character;
		boolean foundNumber = false;
		int sign = 1;
		
		for (int i = 0; i < inString.length(); i++) {
			character = inString.charAt(i);
			
			if ((Character.isDigit(character)) || (Character.toString(character).equals("."))) {
				stringNumber += character;
				
				foundNumber = true;
			} else {
				if (Character.toString(character).equals("-")) {
					sign = -1;
				}
			}
		}
		
		if (foundNumber) {
			try {
				number = Double.valueOf(stringNumber) * sign;
			} catch (NumberFormatException e) {}
		}
		
		return number;
	}

	public static String getWordFromString(String inString) {
		String word = "";
		char character;
		boolean foundWord = false;
		
		for (int i = 0; i < inString.length(); i++) {
			character = inString.charAt(i);
			if (Character.isLetter(character)) {
				word += character;
				
				foundWord = true;
			} else {
				if (foundWord) {
					break;
				}
			}
		}
		
		return word;
	}

	public static String getLettersFromString(String inString) {
		String letters = "";
		char character;
		
		for (int i = 0; i < inString.length(); i++) {
			character = inString.charAt(i);
			if (Character.isLetter(character)) {
				letters += character;
			}
		}
		
		return letters;
	}

	public static boolean containsToken(String tokenString, String token, String delim) {
		return Arrays.asList(tokenString.split(delim)).contains(token);
	}
	
	public static boolean containsToken(String tokenString, String token) {
		return containsToken(tokenString, token, EISConstants.PROPERTY_INSTANCE_DELIM);
	}
	
	public static boolean containsToken(String inTokenString, String inToken, String delim, boolean isCaseInsensitive) {
		String tokenString = inTokenString;
		String token = inToken;

		if (isCaseInsensitive) {
			tokenString = tokenString.toUpperCase();
			token = token.toUpperCase();
		}

		return containsToken(tokenString, token, delim);
	}
	
	public static boolean containsToken(String tokenString, String token, boolean isCaseInsensitive) {
		return containsToken(tokenString, token, EISConstants.PROPERTY_INSTANCE_DELIM, isCaseInsensitive);
	}

	public static String getField(String fieldString, String startDelim, String endDelim) {
		String field	= "";
		int startIndex	= fieldString.indexOf(startDelim);
		
		if (!startDelim.isEmpty() && !endDelim.isEmpty()) {
			if (startIndex >= 0) {
				startIndex++;
				int endIndex = fieldString.lastIndexOf(endDelim);
	
				try {
					field = fieldString.substring(startIndex, endIndex);
				} catch (IndexOutOfBoundsException e) {}
			}
		}
		
		return field.replace("\u00a0", "").trim();
	}

	public static String getField(String fieldString, String delim) {
		return getField(fieldString, delim, delim);
	}

	public static boolean isCheckValue(String value) {
		return containsToken(EISConstants.FIELD_CHECK_VALUES, value, EISConstants.PROPERTY_INSTANCE_DELIM, true);
	}

	public static boolean isUncheckValue(String value) {
		return containsToken(EISConstants.FIELD_UNCHECK_VALUES, value, EISConstants.PROPERTY_INSTANCE_DELIM, true);
	}

	public static int listOfStringMatch(List<String> list, String inValue, boolean isCaseInsensitive) {
		List<String> searchList = new ArrayList<String>();
		int index = -1;
		String value = inValue;
		String item;
		
		if (isCaseInsensitive) {
			searchList = listOfStringToUpperCase(list);
			value = value.toUpperCase();
		} else {
			searchList = list;
		}
		
		//Have the matches() call ignore white space, especially \n.  (If \n is present,
		//  matches() stops when it encounters it.  That's a problem when we want to match
		//  multi-line text.)
		value = "(?s)" + value;
		
		ListIterator<String> itr = searchList.listIterator();
		while (itr.hasNext()) {
			item = itr.next();
			
			if (item.matches(value)) {
				index = itr.nextIndex() - 1;
				
				break;
			}
		}
		
		return index;
	}
	
	public static int listOfStringMatch(List<String> list, String value) {
		return listOfStringMatch(list, value, false);
	}

	public static int listOfStringFind(List<String> list, String inValue, boolean isCaseInsensitive) {
		List<String> searchList = new ArrayList<String>();
		String value = inValue;
		
		if (isCaseInsensitive) {
			searchList = listOfStringToUpperCase(list);
			value = value.toUpperCase();
		} else {
			searchList = list;
		}
		
		return searchList.indexOf(value);
	}
	
	public static int listOfStringFind(List<String> list, String value) {
		return listOfStringFind(list, value, false);
	}

	public static int listOfStringFindSubstring(List<String> list, String inValue, boolean isCaseInsensitive) {
		List<String> searchList = new ArrayList<String>();
		int index = -1;
		String value = inValue;
		String item;
		
		if (isCaseInsensitive) {
			searchList = listOfStringToUpperCase(list);
			value = value.toUpperCase();
		} else {
			searchList = list;
		}
		
		ListIterator<String> itr = searchList.listIterator();
		while (itr.hasNext()) {
			item = itr.next();
			
			if (item.indexOf(value) >= 0) {
				index = itr.nextIndex() - 1;
				
				break;
			}
		}
		
		return index;
	}
	
	public static int listOfStringFindSubstring(List<String> list, String value) {
		return listOfStringFindSubstring(list, value, false);
	}

	//Alias for listOfStringFindSubstring(List<String> list, String inValue, boolean isCaseInsensitive)
	public static int listOfStringFindContains(List<String> list, String value, boolean isCaseInsensitive) {
		return listOfStringFindSubstring(list, value, isCaseInsensitive);
	}
	
	//Alias for listOfStringFindSubstring(List<String> list, String value)
	public static int listOfStringFindContains(List<String> list, String value) {
		return listOfStringFindSubstring(list, value, false);
	}

	public static int listOfStringFindStartsWith(List<String> list, String inValue, boolean isCaseInsensitive) {
		List<String> searchList = new ArrayList<String>();
		int index = -1;
		String value = inValue;
		String item;
		
		if (isCaseInsensitive) {
			searchList = listOfStringToUpperCase(list);
			value = value.toUpperCase();
		} else {
			searchList = list;
		}
		
		ListIterator<String> itr = searchList.listIterator();
		while (itr.hasNext()) {
			item = itr.next();
			
			if (item.startsWith(value)) {
				index = itr.nextIndex() - 1;
				
				break;
			}
		}
		
		return index;
	}
	
	public static int listOfStringFindStartsWith(List<String> list, String value) {
		return listOfStringFindStartsWith(list, value, false);
	}

	public static int arrayOfStringFind(String[] array, String value, boolean isCaseInsensitive) {
		return listOfStringFind(new ArrayList<String>(Arrays.asList(array)), value, isCaseInsensitive);
	}
	
	public static int arrayOfStringFind(String[] array, String value) {
		return arrayOfStringFind(array, value, false);
	}

	public static List<String> listOfStringToUpperCase(List<String> list) {
		List<String> upperList = new ArrayList<String>();
		
		for (String item : list) {
			upperList.add(item.toUpperCase());
		}
		
		return upperList;
	}

	public static List<String> listOfStringToLowerCase(List<String> list) {
		List<String> lowerList = new ArrayList<String>();
		
		for (String item : list) {
			lowerList.add(item.toLowerCase());
		}
		
		return lowerList;
	}

	public static List<String> listOfStringTrim(List<String> list) {
		List<String> trimmedList = new ArrayList<String>();
		
		for (String item : list) {
			trimmedList.add(item.replace("\u00a0", "").trim());
		}
		
		return trimmedList;
	}

	public static String stripLineSeparators(String text, String replacement) {
		return text.replaceAll("\\r\\n|\\r|\\n", replacement);
	}
	
	public static String stripLineSeparators(String text) {
		return stripLineSeparators(text, "//");
	}
	
	public static void listPrint(List<?> objects) {
		for (Object object : objects) {
			System.out.println(object.toString());
		}
	}
	
	public static void arrayPrint(Object[] objects) {
		listPrint(new ArrayList<Object>(Arrays.asList(objects)));
	}
	
	public static String getReferencedFieldValue(String className, String fieldName) throws SecurityException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> klass = Class.forName(className);
		
	 	java.lang.reflect.Field field = klass.getDeclaredField(fieldName);

		return field.get(klass).toString();
	}

	//This version does not validate the dir name
/*	public static String getTestRootDir() {
		String dir;
		//First check for the JVM property that would be set if being called by Ant.  If that value is null,
		//  use the environment variable
		Properties sysProps = System.getProperties();
		dir = sysProps.getProperty(EISConstants.TEST_HOME_JVM_PROPERTY_NAME, "");
		if (dir.isEmpty()) {
			dir = System.getenv(EISConstants.TEST_HOME_ENV_VAR_NAME);
		}
		
    	printDebug("Test root directory is " + dir);
		
	    return dir;
	}
*/
	//This version validates the dir name and fails the test if it is invalid
	public static String getTestRootDir() {
		String dir = null;
		String propertyUsed;
		//First check for the JVM property that would be set if being called by Ant.  If that value is null,
		//  use the environment variable
		Properties sysProps =null;
		
		try {
			sysProps=System.getProperties();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Util.printInfo("Fatal Error while fetching System Properties");
		
		}
		
		if(sysProps.toString().contains("Windows")){
			System.out.println("Windows Platform");
			propertyUsed = EISConstants.TEST_HOME_JVM_PROPERTY_NAME;
			dir = sysProps.getProperty(propertyUsed, "");
			if (dir.isEmpty()) {
				propertyUsed = EISConstants.TEST_HOME_ENV_VAR_NAME;
				dir = System.getenv(propertyUsed);
			}
			
	    	//Ensure that the dir name begins with a digit, letter, or underscore
	    	if (!dir.matches("\\w.*")) {
	    		//failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    		EISTestBase.failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    	}

	    	printDebug("Test root directory is '" + dir + "' (accessed using the " + propertyUsed + " property or environment variable)");
			
		}else if(sysProps.toString().contains("Mac")) {
			System.out.println("Mac Platform");
			System.out.println("System Properties :: "+sysProps);
			propertyUsed = EISConstants.TEST_HOME_JVM_PROPERTY_NAME;
			dir = sysProps.getProperty(propertyUsed, "");
			if (dir.isEmpty()) {
				propertyUsed = EISConstants.TEST_HOME_ENV_VAR_NAME;
				dir = System.getenv(propertyUsed);
			}
			
			
			System.out.println(dir);
	    	//Ensure that the dir name begins with a digit, letter, or underscore
	    	if (dir.matches("\\w.*")) {
	    		//failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    		EISTestBase.failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    	}

	    	printDebug("Test root directory is '" + dir + "' (accessed using the " + propertyUsed + " property or environment variable)");
			
		}else{
			/*propertyUsed = EISConstants.TEST_HOME_JVM_PROPERTY_NAME;
			dir = sysProps.getProperty(propertyUsed, "");
			if (dir.isEmpty()) {
				propertyUsed = EISConstants.TEST_HOME_ENV_VAR_NAME;
				dir = System.getenv(propertyUsed);
			}
			
			
			System.out.println(dir);
	    	//Ensure that the dir name begins with a digit, letter, or underscore
	    	if (!dir.matches("\\w.*")) {
	    		//failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    		EISTestBase.failTest("The value of the " + propertyUsed + " property or environment variable ('" + dir + "') is invalid");
	    	}

	    	printDebug("Test root directory is '" + dir + "' (accessed using the " + propertyUsed + " property or environment variable)");
			*/
			
			EISTestBase.fail("Fatal error while fetching system Properties :: "+sysProps + "  Directory Path :: "+dir);
		}
		
		
	    return dir;
	}

	public static <T extends Enum<T>> List<String> valuesOfEnum(Class<T> enumClass) {
		//Call like this:
		//  Util.valuesOfEnum(EISConstants.TestResultType.class);
	
		List<String> values = new ArrayList<String>();
		
		T[] elements = enumClass.getEnumConstants();
		for (T element : elements) {
			values.add(element.toString());
		}
	
		return values;
	}

	public static void printMessage(String message) {
		System.out.println("*** " + getTimestamp(DateFormat.MEDIUM, DateFormat.LONG) + " *** " + message);
	}
	
	public static void printInfo(String message) {
		if (!silentMode) {
			if (!message.isEmpty()) {
				printMessage("INFO: " + message);
			} else {
				System.out.println(message);
			}
		}
	}
	
	
	public static String PrintInfo(String message) {
		if (!silentMode) {
			if (!message.isEmpty()) {
				printMessage("INFO: " + message);
			} else {
				System.out.println(message);
			}
		}
		return message;
	}
	public static void printWarning(String message) {
		if (!silentMode) {
			printMessage("WARNING: " + message);
		}
	}
	
	public static void printError(String message) {
		//Ignore silentMode setting when reporting errors
		printMessage("ERROR: " + message);
	}

	public static void printDebug(String message) {
		//Ignore silentMode setting while in debug mode
		if (debugMode) {
			printMessage("DEBUG: " + message);
		}
	}

	public static void printTestFailedMessage(String prefix, String message) {
		EISConstants.ASSERTION_MESSAGE_DATA=message;
		//Ignore silentMode setting!
		System.out.println("\n\n" + prefix);
		System.out.println("\t" + message + "\n\n");
		EISConstants.REPORT_TEST_STATUS_IS_FAIL=true;
		EISTestBase.reportValidation(message, "FAILED");
		EISTestBase.reortTestStatus(false);
		
	}

	public static void printTestFailedMessage(String message) {
		printTestFailedMessage(EISConstants.TEST_FAILED_MESSAGE_PREFIX, message);
	}

	public static void printAssertingMessage(String prefix, String message) {
		//Ignore silentMode setting!
		System.out.println("\n" + prefix);
		System.out.println("\t" + message);
	}

	public static void printAssertingMessage(String message) {
		EISConstants.ASSERTION_MESSAGE_DATA=message;
		printAssertingMessage(EISConstants.ASSERTING_MESSAGE_TEXT, message);
	}

	public static void printAssertResultMessage(String message) {
		String status=null;
		if (message.contains("PASSED")){
			status="PASSED";
		}else if(message.contains("FAILED")){
			status="FAILED";
		}
		EISTestBase.reportValidation( EISConstants.ASSERTION_MESSAGE_DATA, status);
		System.out.println(message + "\n");
	}

/*	public static String getSfdcObjectId(String url) {
		String id = "";
		if (!url.isEmpty()) {
			String[] elements = url.split("/");
			id = elements[elements.length - 1];
			
			elements = id.split("=");
			id = elements[elements.length - 1];
		}
		
		return id;
	}
*/
	public static String getSfdcObjectId(String url) {
		String id = "";

		if (!url.isEmpty()) {
			String[] elements = url.split("/");
			id = elements[elements.length - 1];
			
			elements = id.split("\\?");
			id = elements[0];
		}
		
		return id;
	}

	public static String resolveConstantToken(String value) throws TestDataException {
		String newValue;
		String regexTerm = ".*" + EISConstants.OBJECT_REFERENCE_REGEX_TERM + ".*";
		
		//Check for references to objects in the EISConstants class.  If the reference is to
		//  a date constant in EISConstants (e.g., TOMORROW), that value will be returned,
		//  after having the optional date offset applied
		if (value.matches(regexTerm)) {
			newValue = resolveGlobalConstant(value);
		} else {
			//Check for a date offset value and, if found,  apply it to today's date
			if (value.matches(EISConstants.DATE_OFFSET_REGEX_TERM)) {
				newValue = resolveDateOffsetConstant(value, EISConstants.TODAY);
			} else {
				//It's just a regular old value...
				newValue = value;
			}
		}
		
		return newValue;
	}

	private static String resolveGlobalConstant(String value) throws TestDataException {
		String newValue;
		String token;
		String cleanedToken;
		String newToken = "";
		boolean isCompoundValue;
		boolean isDate;
		int startIndex;
		int endIndex;
		
		if (value.matches(EISConstants.OBJECT_REFERENCE_REGEX_TERM)) {
			isCompoundValue = false;
			
			token = value;
		} else {
			isCompoundValue = true;
			
			startIndex = value.indexOf(EISConstants.OBJECT_REFERENCE_DELIM);
			endIndex = value.indexOf(EISConstants.OBJECT_REFERENCE_DELIM, (startIndex + 1));
			token = value.substring(startIndex, (endIndex + 1));
		}
		
		cleanedToken = token.replaceAll(EISConstants.OBJECT_REFERENCE_DELIM, "").trim().toUpperCase();
		
		try {
			newToken = Util.getReferencedFieldValue(EISConstants.class.getName(), cleanedToken);
		} catch (Exception e) {
			//throw new TestDataException("The value '" + value + "' references the global constant '" + cleanedToken + "', but no global constant of that name exists in the EISConstants class", e);
			//failTest("The value '" + value + "' references the global constant '" + cleanedToken + "', but no global constant of that name exists in the EISConstants class");
			EISTestBase.failTest("The value '" + value + "' references the global constant '" + cleanedToken + "', but no global constant of that name exists in the EISConstants class");
		}
		
		if (isCompoundValue) {
			//Is it a date?  If so, we'll treat the trailing part of the compound value as a date offset
			try {
				Util.addDates(newToken, Calendar.DAY_OF_YEAR, 1);

				isDate = true;
			} catch (ParseException e) {
				isDate = false;
			}

			if (isDate) {
				newValue = resolveDateOffsetConstant(value.replaceFirst(token, ""), newToken);
			} else {
				newValue = value.replaceFirst(token, newToken);
			}
		} else {
			newValue = newToken;
		}
		
		return newValue;
	}
	
	private static String resolveDateOffsetConstant(String value, String date) throws TestDataException {
		String newValue = "";
		String cleanedValue = value.replace(EISConstants.DATE_OFFSET_DELIM, "");
		EISConstants.DateOffsetType offsetType = null;
		cleanedValue = cleanedValue.replace("+", "").trim();
		
		int dateOffset = getIntegerFromString(cleanedValue);
		if (dateOffset != 0) {
			String offsetTypeRaw = getWordFromString(cleanedValue);
			
			if (!offsetTypeRaw.isEmpty()) {
				try {
					offsetType = EISConstants.DateOffsetType.valueOf(offsetTypeRaw.toUpperCase());
				} catch (IllegalArgumentException e) {
					//throw new TestDataException("The value '" + offsetTypeRaw + "' is not a member of the EISConstants.DateOffsetType enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.DateOffsetType.class), e);
					//failTest("The value '" + offsetTypeRaw + "' is not a member of the EISConstants.DateOffsetType enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.DateOffsetType.class));
					EISTestBase.failTest("The value '" + offsetTypeRaw + "' is not a member of the EISConstants.DateOffsetType enumerated type; valid values are: " + Util.valuesOfEnum(EISConstants.DateOffsetType.class));
				}
			} else {
				offsetType = EISConstants.DateOffsetType.DAYS;
			}
			
			int field = -1;

			switch (offsetType) {
				case MONTHS:
				case MONTH:
				case M:		{
					field = Calendar.MONTH;
					break;
				}
				case YEARS:
				case YEAR:
				case Y:		{
					field = Calendar.YEAR;
					break;
				}
				case DAYS:
				case DAY:
				case D:		{
					field = Calendar.DAY_OF_YEAR;
					break;
				}
				default:	{
					throw new TestDataException("Unhandled member of common.EISConstants.DateOffsetType enumerated type: " + offsetType);
				}
			}

			try {
				newValue = Util.addDates(date, field, dateOffset);
			} catch (ParseException e) {
				//throw new TestDataException("Adding the relative date offset '" + dateOffset + " " + offsetType + "' to the global constant EISConstants.TODAY ('" + EISConstants.TODAY + "') results in an invalid date", e);
				//failTest("Adding the relative date offset '" + dateOffset + " " + offsetType + "' to the global constant EISConstants.TODAY ('" + EISConstants.TODAY + "') results in an invalid date");
				EISTestBase.failTest("Adding the relative date offset '" + dateOffset + " " + offsetType + "' to the global constant EISConstants.TODAY ('" + EISConstants.TODAY + "') results in an invalid date");
			}
		} else {
			newValue = date;
		}
		
		return newValue;
	}

	@SuppressWarnings("unused")
	private static String resolveDateOffsetConstant(String value) throws TestDataException {
		return resolveDateOffsetConstant(value, EISConstants.TODAY);
	}
	
	public static void sleep(long millis) {
		if (millis > 0) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException ie) {
				throw new RuntimeException("An InterruptedException (" + ie.getMessage() + ") occurred while in Thread.sleep()");
			}
		}
	}

	public static void serialize(Object object, String resourceDir, String filename) {
		//NOTE that this is a VERY "vanilla" and naive attempt at serialization.  There is
		//  a lot more involved when serializing all but the most simple objects!
		try
		{
			FileOutputStream fileOut;
			ObjectOutputStream objectOut;
	
			fileOut = new FileOutputStream(resourceDir + filename);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(object);
			objectOut.close();
			fileOut.close();
		} catch (IOException i) {
			//TODO fail test here?
			Util.printInfo("IOException occurred while serializing object: " + i.getMessage());
			i.printStackTrace();
		}
	}
	
	public static Object deserialize(String resourceDir, String filename) {
		//NOTE that this is a VERY "vanilla" and naive attempt at serialization.  There is
		//  a lot more involved when serializing all but the most simple objects!

		Object object = null;

		try
		{
			FileInputStream fileIn;
			ObjectInputStream objectIn;

			fileIn = new FileInputStream(resourceDir + filename);
			objectIn = new ObjectInputStream(fileIn);
			object = objectIn.readObject();
			objectIn.close();
			fileIn.close();
		} catch (IOException io) {
			//TODO fail test here?
			Util.printInfo("IOException occurred while deserializing object: " + io.getMessage());
			io.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			//TODO fail test here?
			Util.printInfo("ClassNotFoundException occurred while deserializing object: " + cnf.getMessage());
			cnf.printStackTrace();
		}
		
		return object;
	}
	
	public static  HashMap<String, String> readFromExcel() throws IOException, WriteException, BiffException {
		  // TODO Auto-generated method stub
				List<String> keyList = new ArrayList<String>(); 
				List<String> list = new ArrayList<String>(); 
				HashMap<String, String> map = new HashMap<String, String>();   
				String str = null;
			
				try
		            {
		                File f1=new File("//ecs-9844/DoNotDelete/JenkinsData/input.xls");
		               //the excel sheet which contains data
		                WorkbookSettings ws=new WorkbookSettings();
		                ws.setLocale(new Locale("er","ER"));
		                Workbook workbook=Workbook.getWorkbook(f1,ws);
		                Sheet readsheet=workbook.getSheet(0);
		                //Loop to read the KEYS from Excel i.e, 1st column of the Excel
		                for(int i=0;i<readsheet.getColumns();i++) {
		              	str=readsheet.getCell(i,0).getContents().toString();
		              	list.add(str);
		                }
		              	keyList.addAll(list);
		              	// Hardcoding the first map (key, value) values           
		               	map.put(keyList.get(0), readsheet.getCell(0, 1).getContents().toString());
		               	// Loop to read TEST DATA from the Excel
		                for(int i=1;i<readsheet.getRows();i++) {
		                for(int j=1;j<readsheet.getColumns();j++) {	
		                str=readsheet.getCell(j,i).getContents().toString();
		                list.add(str);
		                System.out.println(str);
		                map.put(keyList.get(j),str); 
		                	}
		               }
		             //Print the map(key, value)   
		               System.out.println("Print map");
		               System.out.println(map);
		             
		              }
		            catch(IOException e)
		            {
		                e.printStackTrace();
		            }

		            catch(BiffException e)
		            {
		                e.printStackTrace();
		            } catch (Exception e) {
		                e.printStackTrace(); 
		            }
					return map;
	}
	
	public static void writeToExcel(String fileName , TreeMap <String, String> dataMap ) throws IOException, WriteException, BiffException, RowsExceededException {
        File f1=new File(fileName);

        	//Create excel File
        	 WorkbookSettings wbSettings = new WorkbookSettings();
        	 wbSettings.setLocale(new Locale("en", "EN"));
        	 WritableWorkbook workbook = Workbook.createWorkbook(f1, wbSettings);
        	 workbook.createSheet("Data", 0);
        	 WritableSheet excelSheet = workbook.getSheet(0);

        	//write to excel file
//			WritableSheet excelSheet = (WritableSheet) Workbook.getWorkbook(f1).getSheet(0);
        	
        	 
        	
        	Set set = dataMap.entrySet();
    		Iterator i = set.iterator(); 
    		int j=0;
    		while(i.hasNext()){
    		
    		Map.Entry me = (Map.Entry)i.next();
    		
   			Label Name2 = new Label(j,0,me.getKey().toString());
   			excelSheet.addCell(Name2);
   			
            Label Name3 = new Label(j,1,me.getValue().toString());
            excelSheet.addCell(Name3);
                
            j++;
   		}
   		
    		workbook.write();
       		workbook.close();
    

        
	}
	/**
	 * Description get the current UTC time
	 * @param curdate
	 * @return
	 */
	public static String getCurrentUTCTime(Date curdate,String formatType) {
		SimpleDateFormat sformat = new SimpleDateFormat(formatType);
		sformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String UTCTime= sformat.format(curdate).toString();		
		return UTCTime;
	}
	/**
	 * @Description get the next month time in UTC time zone
	 * @param currentDate
	 * @param formatType
	 * @return
	 */
	public static String getNextMonthDateInUTCFormat(Date currentDate, String formatType) {
     		SimpleDateFormat sformat = new SimpleDateFormat(formatType);
     		sformat.setTimeZone(TimeZone.getTimeZone("UTC"));
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(currentDate);	        
	        cal.add(Calendar.MONTH, 1);
			String UTCTime= sformat.format(cal.getTime());

	        return  UTCTime;
	    }
	/**
	 * @Description get the next Year time in UTC time zone
	 * @param currentDate
	 * @param formatType
	 * @return
	 */
	public static String getNextYearDateInUTCFormat(Date currentDate, String formatType) {
     		SimpleDateFormat sformat = new SimpleDateFormat(formatType);
     		sformat.setTimeZone(TimeZone.getTimeZone("UTC"));
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(currentDate);	        
	        cal.add(Calendar.YEAR, 1);
			String UTCTime= sformat.format(cal.getTime());

	        return  UTCTime;
	    }
	public static String getUniqueString(int lenght) {
		return RandomStringUtils.random(lenght, true, true);
	}
	
}


