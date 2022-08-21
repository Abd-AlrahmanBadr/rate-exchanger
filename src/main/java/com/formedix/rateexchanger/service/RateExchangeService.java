package com.formedix.rateexchanger.service;

import com.formedix.rateexchanger.exception.InvalidDatePeriodException;
import com.formedix.rateexchanger.repository.RateExchangeRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Rate Exchange Service
 * => Contains Logic Behind Rate Exchange Controller
 */
@Service
public class RateExchangeService {
	
	/**
	 * Default Constructor
	 */
	public RateExchangeService () {
		RateExchangeRepository.loadData();
	}
	
	/**
	 * Retrieve Full Day Exchange Rates for a Specific Day
	 * @param dayDate	Date	Requested Day's Date
	 * @return HashMap<String, Double>
	 */
	public HashMap<String, Double> getDayExchangeRates (Date dayDate) {
		return RateExchangeRepository.getDayRates(dayDate);
	}
	
	/**
	 * Exchange Amount From Currency to Another in a Specific Day
	 * @param dayDate	Date	Requested Day's Date
	 * @param source	String	Currency's Name
	 * @param target	String 	Currency's Name
	 * @param amount	Double	Amount Exchanged
	 * @return	Double
	 */
	public Double exchangeAmount (Date dayDate, String source, String target, Double amount) {
		Double result = amount;
		
		// Convert Amount From Source to Euro
		result /= RateExchangeRepository.getDayCurrencyRate(dayDate, source);
		
		// Convert Amount From Euro to Target
		result *= RateExchangeRepository.getDayCurrencyRate(dayDate, target);
		
		return result;
	}
	
	/**
	 * Find Max Exchange Rate For Currency in Period
	 * @param startDayDate	Date	Period Start Date
	 * @param endDayDate	Date 	Period End Date
	 * @param currency		String	Currency's Name
	 * @return Double
	 */
	public Double maxExchangeRateInPeriod (Date startDayDate, Date endDayDate, String currency) {
		Double maxRate = -1.0;
		Calendar currentDayCalender = Calendar.getInstance();
		currentDayCalender.setTime(startDayDate);
		Calendar endDayCalender = Calendar.getInstance();
		endDayCalender.setTime(endDayDate);
		
		// Validate Period's Dates => (Start <= End)
		if (currentDayCalender.compareTo(endDayCalender) > 0) {
			throw new InvalidDatePeriodException(startDayDate, endDayDate);
		}
		
		while (currentDayCalender.compareTo(endDayCalender) <= 0) {
			// Retrieve Exchange Rate
			Double currencyExchangeRate = RateExchangeRepository.getDayCurrencyRate(currentDayCalender.getTime(), currency);
			
			// Set Max Exchange Rate
			maxRate = maxRate > currencyExchangeRate ? maxRate : currencyExchangeRate;
			
			currentDayCalender.add(Calendar.DATE, 1);
		}
		
		return maxRate;
	}
	
	/**
	 * Find Average Exchange Rate For Currency in Period
	 * @param startDayDate	Date	Period Start Date
	 * @param endDayDate	Date 	Period End Date
	 * @param currency		String	Currency's Name
	 * @return Double
	 */
	public Object avgExchangeRateInPeriod (Date startDayDate, Date endDayDate, String currency) {
		Double rates_total = 0.0;
		Integer daysCount = 0;
		
		Calendar currentDayCalender = Calendar.getInstance();
		currentDayCalender.setTime(startDayDate);
		Calendar endDayCalender = Calendar.getInstance();
		endDayCalender.setTime(endDayDate);
		
		// Validate Period's Dates => (Start <= End)
		if (currentDayCalender.compareTo(endDayCalender) > 0) {
			throw new InvalidDatePeriodException(startDayDate, endDayDate);
		}
		
		while (currentDayCalender.compareTo(endDayCalender) <= 0) {
			// Retrieve Exchange Rate
			Double currencyExchangeRate = RateExchangeRepository.getDayCurrencyRate(currentDayCalender.getTime(), currency);
			
			// Set Max Exchange Rate
			rates_total += currencyExchangeRate;
			
			currentDayCalender.add(Calendar.DATE, 1);
			daysCount++;
		}
		
		return rates_total / daysCount;
	}
	
}
