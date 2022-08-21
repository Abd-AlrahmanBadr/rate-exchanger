package com.formedix.rateexchanger.model;

import com.formedix.rateexchanger.exception.DayHistoryNotFoundException;

import java.util.Date;
import java.util.HashMap;

/**
 * Exchange Rates History
 * => Keeps the History of Exchange Rates for All Days
 */
public class RateExchangeHistory {
	
	private final HashMap<Date, DayExchangeRate> _history;
	
	/**
	 * Default Constructor
	 */
	public RateExchangeHistory () {
		this._history = new HashMap<>();
	}
	
	/**
	 * Set/Update Currency's Exchange Rate in a Specific Day
	 * @param dayDate		Date	Targeted Day's Date
	 * @param currency		String	Currency's Name
	 * @param rate			Double	Currency's Exchange Rate Against Euro
	 */
	public void setCurrencyExchangeRate (Date dayDate, String currency, Double rate) {
		// Validate Day Exists
		if (!this._history.containsKey(dayDate)) {
			this._history.put(dayDate, new DayExchangeRate());
		}
		
		// Set/Update Currency's Exchange Rate
		this._history.get(dayDate).setCurrencyExchangeRate(currency, rate);
	}
	
	/**
	 * Retrieve Currency's Exchange Rate for a Specific Day
	 * @param dayDate		Date	Requested Day's Date
	 * @param currency		String	Currency's Name
	 * @return Double
	 */
	public Double getCurrencyExchangeRate (Date dayDate, String currency) {
		// Validate Day Exists
		if (!this._history.containsKey(dayDate)) {
			throw new DayHistoryNotFoundException(dayDate);
		}
		
		return this._history.get(dayDate).getCurrencyExchangeRate(currency);
	}
	
	/**
	 * Retrieve Full Day Exchange Rates for a Specific Day
	 * @param dayDate		Date	Requested Day's Date
	 * @return DayExchangeRate
	 */
	public HashMap<String, Double> getDayExchangeRates (Date dayDate) {
		// Validate Day Exists
		if (!this._history.containsKey(dayDate)) {
			throw new DayHistoryNotFoundException(dayDate);
		}
		
		return this._history.get(dayDate).getAllRates();
	}
	
}
