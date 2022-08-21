package com.formedix.rateexchanger.controller;

import com.formedix.rateexchanger.service.RateExchangeService;
import com.formedix.rateexchanger.utils.constants.DateConstants;
import com.formedix.rateexchanger.utils.formatter.DateFormatter;
import com.formedix.rateexchanger.utils.response.SuccessResponse;
import com.formedix.rateexchanger.utils.response.SuccessResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * Rate Exchange Controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/rate_exchanger")
public class RateExchangerController {
	
	@Autowired
	private final RateExchangeService _rateExchangeService;
	
	/**
	 * GET Route for Retrieving Day's Exchange Rates
	 * @route /api/v1/rate_exchanger/day?date=<DATE>
	 * @param dayDate	Date	Requested Day Date
	 * @return ResponseEntity<SuccessResponse>
	 */
	@GetMapping("day")
	public ResponseEntity<SuccessResponse> getDayExchangeRates (
		@RequestParam("date") @DateTimeFormat(pattern=DateConstants.defaultFormat) Date dayDate
	) {
		HashMap<String, Object> result = new HashMap<>();
		
		// Set Arguments Data
		result.put("exchange_day", DateFormatter.formatDate(dayDate));
		
		// Retrieve Day Exchange Rates
		result.put("exchange_rates", this._rateExchangeService.getDayExchangeRates(dayDate));
		
		return SuccessResponseBuilder.buildSuccessResponse(result);
	}
	
	/**
	 * GET Route for Exchanging Amount From Source Currency to Target Currency in Specific Date
	 * @route /api/v1/rate_exchanger/exchange?date=<DATE>&source=<CURRENCY>&target=<CURRENCY>&amount=<AMOUNT>
	 * @param dayDate			Date	Requested Day Date
	 * @param sourceCurrency	String	Source Currency's Name
	 * @param targetCurrency	String	Target Currency's Name
	 * @param amount			Double	Exchange Amount
	 * @return	ResponseEntity<SuccessResponse>
	 */
	@GetMapping("exchange")
	public ResponseEntity<SuccessResponse> exchangeAmount (
		@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date dayDate,
		@RequestParam("source") String sourceCurrency,
		@RequestParam("target") String targetCurrency,
		@RequestParam("amount") Double amount
	) {
		HashMap<String, Object> result = new HashMap<>();
		
		// Set Arguments Data
		result.put("exchange_day", DateFormatter.formatDate(dayDate));
		result.put("source_currency", sourceCurrency);
		result.put("target_currency", targetCurrency);
		result.put("exchange_amount", amount);
		
		// Retrieve Exchange Amount
		result.put("exchange_result", this._rateExchangeService.exchangeAmount(dayDate, sourceCurrency, targetCurrency, amount));
		
		return SuccessResponseBuilder.buildSuccessResponse(result);
	}
	
	/**
	 * GET Route for Finding Max Exchange Rate for Specific Currency in Date Period
	 * @route /api/v1/rate_exchanger/max_rate?start_date=<DATE>&end_date=<DATE>&currency=<CURRENCY>
	 * @param startDayDate 	Date	Requested Period Start Day Date
	 * @param endDayDate	Date	Requested Period End Day Date
	 * @param currency		String	Currency's Name
	 * @return ResponseEntity<SuccessResponse>
	 */
	@GetMapping("max_rate")
	public ResponseEntity<SuccessResponse> findMaxExchangeRate (
		@RequestParam("start_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDayDate,
		@RequestParam("end_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDayDate,
		@RequestParam("currency") String currency
	) {
		HashMap<String, Object> result = new HashMap<>();
		
		// Set Arguments Data
		result.put("start_date", DateFormatter.formatDate(startDayDate));
		result.put("end_date", DateFormatter.formatDate(endDayDate));
		result.put("currency", currency);
		
		// Retrieve Max Exchange Rate in Period
		result.put("max_exchange_rate", this._rateExchangeService.maxExchangeRateInPeriod(startDayDate, endDayDate, currency));
		
		return SuccessResponseBuilder.buildSuccessResponse(result);
	}
	
	/**
	 * GET Route for Finding Average Exchange Rate for Specific Currency in Date Period
	 * @route /api/v1/rate_exchanger/day?avg_rate=<DATE>&end_date=<DATE>&currency=<CURRENCY>
	 * @param startDayDate 	Date	Requested Period Start Day Date
	 * @param endDayDate	Date	Requested Period End Day Date
	 * @param currency		String	Currency's Name
	 * @return ResponseEntity<SuccessResponse>
	 */
	@GetMapping("avg_rate")
	public ResponseEntity<SuccessResponse> findAvgExchangeRate (
		@RequestParam("start_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDayDate,
		@RequestParam("end_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDayDate,
		@RequestParam("currency") String currency
	) {
		HashMap<String, Object> result = new HashMap<>();
		
		// Set Arguments Data
		result.put("start_date", DateFormatter.formatDate(startDayDate));
		result.put("end_date", DateFormatter.formatDate(endDayDate));
		result.put("currency", currency);
		
		// Retrieve Max Exchange Rate in Period
		result.put("avg_exchange_rate", this._rateExchangeService.avgExchangeRateInPeriod(startDayDate, endDayDate, currency));
		
		return SuccessResponseBuilder.buildSuccessResponse(result);
	}
	
}
