package com.formedix.rateexchanger.utils.parser;

import com.formedix.rateexchanger.utils.constants.DateConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parser Class for Date Related Parsing
 */
public class DateParser {
	
	public static Date parseDate (String date) throws ParseException {
		DateFormat df = new SimpleDateFormat(DateConstants.defaultFormat);
		return df.parse(date);
	}
	
}
