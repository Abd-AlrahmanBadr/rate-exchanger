package com.formedix.rateexchanger.exception;

import com.formedix.rateexchanger.utils.formatter.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

/**
 * Exception for Not Found Day's Exchange Rates
 */
public class DayHistoryNotFoundException extends ResponseStatusException {
	
	/**
	 * Default Constructor
	 * @param dayDate 	Date		Requested Day Date
	 */
	public DayHistoryNotFoundException (Date dayDate) {
		super(
			HttpStatus.BAD_REQUEST,
			String.format(
				"Requested Date '%s' Not Found in History!",
				DateFormatter.formatDate(dayDate)
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
