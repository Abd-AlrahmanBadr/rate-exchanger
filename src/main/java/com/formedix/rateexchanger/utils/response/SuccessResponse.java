package com.formedix.rateexchanger.utils.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Base Success Response Class
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {
	private Integer status;
	private HashMap<String, Object> payload;
	
	/**
	 * Default Constructor
	 * @param status	Integer						HTTP Status
	 * @param payload	HashMap<String, Object>		Response's Data
	 */
	public SuccessResponse (Integer status, HashMap<String, Object> payload) {
		this.status = status;
		this.payload = payload;
	}
}
