package com.formedix.rateexchanger.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * Success Response Builder Class
 */
public class SuccessResponseBuilder {
	
	/**
	 * Static Method for Building Success Response
	 * @param payload	HashMap<String, Object>		Response's Payload
	 * @return ResponseEntity<SuccessResponse>
	 */
	public static ResponseEntity<SuccessResponse> buildSuccessResponse (
		HashMap<String, Object> payload
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new SuccessResponse(HttpStatus.OK.value(), payload));
	}
	
}
