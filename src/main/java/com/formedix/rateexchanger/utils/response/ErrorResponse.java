package com.formedix.rateexchanger.utils.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Base Error Response Class
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private Integer status;
	private String message;
	
	/**
	 * Default Constructor
	 * @param status	Integer						HTTP Status
	 * @param message	String		Error Message
	 */
	public ErrorResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
}

