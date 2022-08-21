package com.formedix.rateexchanger.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.formedix.rateexchanger.utils.response.ErrorResponseBuilder.buildErrorResponse;

/**
 * Advisor Class to Handle Rate Exchange Exceptions
 * => Extends `ResponseEntityExceptionHandler` Class to Override Default Behavior and Extend new Exceptions
 */
@RestControllerAdvice
public class RateExchangeAdvice extends ResponseEntityExceptionHandler {
	
	/**
	 * Exception Handler for Not Found Day's History Exchange Rate
	 * @param exception 	DayHistoryNotFoundException 	Caught Exception
	 * @param request		WebRequest						HTTP Request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ DayHistoryNotFoundException.class })
	public ResponseEntity<Object> handleDayHistoryNotFoundException (
		DayHistoryNotFoundException exception,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			HttpStatus.BAD_REQUEST,
			request
		);
	}
	
	/**
	 * Exception Handler for Not Found Currency's Exchange Rate in Day
	 * @param exception 	CurrencyExchangeRateNotFoundException 	Caught Exception
	 * @param request		WebRequest						HTTP Request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ CurrencyExchangeRateNotFoundException.class })
	public ResponseEntity<Object> handleCurrencyNotFoundException (
		CurrencyExchangeRateNotFoundException exception,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			HttpStatus.BAD_REQUEST,
			request
		);
	}
	
	/**
	 * Exception Handler for Invalid Date Period
	 * @param exception 	InvalidDatePeriodException 	Caught Exception
	 * @param request		WebRequest					HTTP Request
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler({ InvalidDatePeriodException.class })
	public ResponseEntity<Object> handleInvalidDatePeriodException (
		InvalidDatePeriodException exception,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			HttpStatus.BAD_REQUEST,
			request
		);
	}
	
	/**
	 * Override Default Exception Handler for Missing Parameters
	 * @param exception		MissingServletRequestParameterException		Caught Exception
	 * @param headers		HttpHeaders									Response's HTTP Headers
	 * @param status		HTTPStatus									Response's HTTP Status
	 * @param request		WebRequest									HTTP Request
	 * @return ResponseEntity<Object>
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter (
		MissingServletRequestParameterException exception,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			String.format("Missing Parameter '%s' from Request!", exception.getParameterName()),
			HttpStatus.BAD_REQUEST,
			request
		);
	}
	
	/**
	 * Override Default Exception Handler for Types Mismatch
	 * @param exception		TypeMismatchException	Caught Exception
	 * @param headers		HttpHeaders				Response's HTTP Headers
	 * @param status		HTTPStatus				Response's HTTP Status
	 * @param request		WebRequest				HTTP Request
	 * @return ResponseEntity<Object>
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch (
		TypeMismatchException exception,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request
	) {
		return buildErrorResponse(
			exception,
			String.format("Expected Parameter of Type '%s'!", exception.getRequiredType()),
			HttpStatus.BAD_REQUEST,
			request
		);
	}
	
}
