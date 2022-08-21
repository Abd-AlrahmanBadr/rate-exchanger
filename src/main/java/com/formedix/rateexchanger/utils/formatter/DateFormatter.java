package com.formedix.rateexchanger.utils.formatter;

import com.formedix.rateexchanger.utils.constants.DateConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formatter Class for Date Related Formatting
 */
public class DateFormatter {
	
	public static String formatDate (Date date) {
		DateFormat df = new SimpleDateFormat(DateConstants.defaultFormat);
		return df.format(date);
	}
	
}
