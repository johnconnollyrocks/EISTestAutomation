package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtils {
	/**
	 * Gets the current time.
	 *
	 * @return the current time.
	 */
	/** The date format . */
	private static String DATE_FORMAT_NOW = "MM-dd-yyy";

	/** The time format . */
	private static String TIME_FORMAT_NOW = "HH:mm:ss";

	/** The date format for folder. */
	private static String DATE_FORMAT_FOR_FOLDER = "yy-MMM-dd";

	/** The time format for folder. */
	private static String TIME_FORMAT_FOR_FOLDER = "HH-mm-ss";
	
	public static String timeNow() {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);	  
		
		return sdf.format(cal.getTime());
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date.
	 */
	public static String dateNow() {		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		
		return sdf.format(cal.getTime());
	}

	/**
	 * Gets the current date for folder.
	 *
	 * @return the current date for folder.
	 */
	public static String dateNowForFolder() {		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FOR_FOLDER);
		
		return sdf.format(cal.getTime());
	}

	/**
	 * Get the time for folder.
	 *
	 * @return the current time for folder.
	 */
	public static String timeNowForFolder() {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FOR_FOLDER);
		
		return sdf.format(cal.getTime());
	}

		
}
