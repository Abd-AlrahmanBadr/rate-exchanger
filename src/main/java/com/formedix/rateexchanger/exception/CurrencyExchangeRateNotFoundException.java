package com.formedix.rateexchanger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception for Not Found Currencies Cases
 */
public class CurrencyExchangeRateNotFoundException extends ResponseStatusException {
	
	/**
	 * Default Constructor
	 * @param currency 	String 		Currency Name
	 */
	public CurrencyExchangeRateNotFoundException (String currency) {
		super(
			HttpStatus.BAD_REQUEST,
			String.format(
				"Requested Currency '%s' is Not Found!",
				currency
			)
		);
	}
	
	/**
	 * Override `toString` Function to get Reason Instead of Message
	 * @return 	String		Exception Reason
	 */
	@Override
	public String toString() {
		return super.getReason();
	}
	
}
