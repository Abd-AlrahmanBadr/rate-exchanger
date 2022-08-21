package com.formedix.rateexchanger.model;

import com.formedix.rateexchanger.exception.CurrencyExchangeRateNotFoundException;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * Day's Exchange Rates Model
 * => Keeps the Exchange Rates for One Day
 */
public class DayExchangeRate {
	
	private final HashMap<String, Double> _exchangeRates;
	
	/**
	 * Default Constructor
	 */
	public DayExchangeRate () {
		this._exchangeRates = new HashMap<>();
	}
	
	/**
	 * Set/Update Currency's Exchange Rate Against Euro
	 * @param currency	String	Currency's Name
	 * @param rate		Double	Currency's Exchange Rate Against Euro
	 */
	public void setCurrencyExchangeRate (String currency, Double rate) {
		this._exchangeRates.put(currency, rate);
	}
	
	/**
	 * Retrieve Currency's Exchange Rate if Found
	 * @throws CurrencyExchangeRateNotFoundException if Currency Not Found
	 * @param currency	String	Currency's Name
	 * @return Double
	 */
	public Double getCurrencyExchangeRate (String currency) {
		// Check Currency Exists
		if (!this._exchangeRates.containsKey(currency)) {
			throw new CurrencyExchangeRateNotFoundException(currency);
		}
		
		return this._exchangeRates.get(currency);
	}
	
	/**
	 * Retrieve All Currency's Exchange Rates
	 * => Makes a Copy to Prevent Editing Outside
	 * @return HashMap<String, Double>
	 */
	public HashMap<String, Double> getAllRates () {
		return new HashMap<>(this._exchangeRates);
	}
	
}
