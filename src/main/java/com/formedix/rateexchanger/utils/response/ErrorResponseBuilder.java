package com.formedix.rateexchanger.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

/**
 * Error Response Builder Class
 */
public class ErrorResponseBuilder {
	
	/**
	 * Static Method for Building Error Response
	 * @param exception		Exception	Caught Exception
	 * @param httpStatus	HttpStatus	Response's HTTP Status
	 * @param request		WebRequest	Request
	 * @return ResponseEntity<Object>
	 */
	public static ResponseEntity<Object> buildErrorResponse (
		Exception exception,
		HttpStatus httpStatus,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			exception.toString(),
			httpStatus,
			request
		);
	}
	
	/**
	 * Static Method for Building Error Response
	 * @param exception		Exception	Caught Exception
	 * @param message		String		Error Messages
	 * @param httpStatus	HttpStatus	Response's HTTP Status
	 * @param request		WebRequest	Request
	 * @return ResponseEntity<Object>
	 */
	public static ResponseEntity<Object> buildErrorResponse (
		Exception exception,
		String message,
		HttpStatus httpStatus,
		WebRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(
			httpStatus.value(),
			message
		);
		
		return ResponseEntity
			.status(httpStatus)
			.body(errorResponse);
	}
	
}
