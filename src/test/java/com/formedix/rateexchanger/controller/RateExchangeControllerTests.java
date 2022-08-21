package com.formedix.rateexchanger.controller;

import com.formedix.rateexchanger.exception.DayHistoryNotFoundException;
import com.formedix.rateexchanger.repository.RateExchangeRepository;
import com.formedix.rateexchanger.utils.formatter.DateFormatter;
import com.formedix.rateexchanger.utils.parser.DateParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RateExchangeControllerTests {
	
	@Autowired
	private MockMvc mockMVC;
	@MockBean
	private RateExchangeRepository rateExchangeRepository;
	
	@Test
	public void getDayExchangeRates_success () throws Exception {
		final ArrayList<Date> testDates = new ArrayList<>();
		// Set Test Dates
		testDates.add(DateParser.parseDate("2022-08-01"));
		testDates.add(DateParser.parseDate("2022-07-07"));
		testDates.add(DateParser.parseDate("2022-06-14"));
		testDates.add(DateParser.parseDate("2022-05-23"));
		
		testDates.forEach(dayDate -> {
			// Set Expected PayLoad
			HashMap<String, Object> expectedPayload = new HashMap<>();
			expectedPayload.put("exchange_day", DateFormatter.formatDate(dayDate));
			expectedPayload.put("exchange_rates", rateExchangeRepository.getDayRates(dayDate));
			
			// Set Expected Response
			HashMap<String, Object> expectedResponse = new HashMap<>();
			expectedResponse.put("status", HttpStatus.OK.value());
			expectedResponse.put("payload", expectedPayload);
			
			try {
				mockMVC
					.perform(
						MockMvcRequestBuilders
							.get("/api/v1/rate_exchanger/day")
							.param("date", DateFormatter.formatDate(dayDate))
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").value(expectedResponse));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Test
	public void getDayExchangeRates_failure_missingDayArgument () throws Exception {
		// Set Expected Response
		HashMap<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("status", HttpStatus.BAD_REQUEST.value());
		expectedResponse.put("message", "Missing Parameter 'date' from Request!");
		
		mockMVC
			.perform(
				MockMvcRequestBuilders
					.get("/api/v1/rate_exchanger/day")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value(expectedResponse.get("status")))
			.andExpect(jsonPath("$.message").value(expectedResponse.get("message")));
	}
	
	@Test
	public void getDayExchangeRates_failure_dayNotFound () throws Exception {
		final ArrayList<Date> testDates = new ArrayList<>();
		// Set Test Dates
		testDates.add(DateParser.parseDate("2022-08-14"));
		testDates.add(DateParser.parseDate("2022-05-22"));
		
		testDates.forEach(dayDate -> {
			// Set Expected Response
			HashMap<String, Object> expectedResponse = new HashMap<>();
			expectedResponse.put("status", HttpStatus.BAD_REQUEST.value());
			expectedResponse.put("message", new DayHistoryNotFoundException(dayDate).toString());
			
			try {
				mockMVC
					.perform(
						MockMvcRequestBuilders
							.get("/api/v1/rate_exchanger/day")
							.param("date", DateFormatter.formatDate(dayDate))
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.status").value(expectedResponse.get("status")))
					.andExpect(jsonPath("$.message").value(expectedResponse.get("message")));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Test
	public void getDayExchangeRates_failure_invalidDayDate () {
		final ArrayList<String> testDates = new ArrayList<>();
		// Set Test Dates
		testDates.add("2022-0522");
		testDates.add("2022-05");
		
		// Set Expected Response
		HashMap<String, Object> expectedResponse = new HashMap<>();
		expectedResponse.put("status", HttpStatus.BAD_REQUEST.value());
		expectedResponse.put("message", "Expected Parameter of Type 'class java.util.Date'!");
		
		testDates.forEach(dayDate -> {
			try {
				mockMVC
					.perform(
						MockMvcRequestBuilders
							.get("/api/v1/rate_exchanger/day")
							.param("date", dayDate)
							.contentType(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.status").value(expectedResponse.get("status")))
					.andExpect(jsonPath("$.message").value(expectedResponse.get("message")));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	/**
	 * Implement Tests For:
	 * - exchange
	 * - - success
	 * - - failure_missingArguments
	 * - - failure_dayNotFound
	 * - - failure_currencyExchangeNotFound
	 * - - failure_invalidAmount
	 * - - failure_invalidDatePeriod
	 * - max_rate
	 * - - success
	 * - - failure_missingArguments
	 * - - failure_dayNotFound
	 * - - failure_currencyExchangeNotFound
	 * - - failure_invalidDatePeriod
	 * - avg_rate
	 * - - success
	 * - - failure_missingArguments
	 * - - failure_dayNotFound
	 * - - failure_currencyExchangeNotFound
	 * - - failure_invalidDatePeriod
	 */
	
}
