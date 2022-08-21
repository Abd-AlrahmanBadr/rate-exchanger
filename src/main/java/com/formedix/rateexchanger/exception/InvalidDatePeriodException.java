package com.formedix.rateexchanger.exception;

import com.formedix.rateexchanger.utils.formatter.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

public class InvalidDatePeriodException extends ResponseStatusException {
	
	/**
	 * Default Constructor
	 * @param date1 	Date		Requested Start Day Date
	 * @param date2 	Date		Requested End Day Date
	 */
	public InvalidDatePeriodException (Date date1, Date date2) {
		super(
			HttpStatus.BAD_REQUEST,
			String.format(
				"Requested Date Period [%s, %s] Is Not Valid!",
				DateFormatter.formatDate(date1),
				DateFormatter.formatDate(date2)
			)
		);
	}
	
	/**
	 * Override `toString` Function to get Reason Instead of Message
	 * @return String
	 */
	@Override
	public String toString () {
		return super.getReason();
	}
	
}
