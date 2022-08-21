package com.formedix.rateexchanger.repository;

import com.formedix.rateexchanger.model.RateExchangeHistory;
import com.formedix.rateexchanger.utils.parser.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Rate Exchange Repository
 * => Manages Data Store for Exchange Rates
 */
@Repository
public class RateExchangeRepository {
	
	private static final Logger _logger = LoggerFactory.getLogger(RateExchangeRepository.class);
	private static final RateExchangeHistory _rateExchangeHistory = new RateExchangeHistory();
	
	/**
	 * Static Method for Data Loading
	 * => Read/Parse Exchange Rates File
	 * => Ignores 'N/A' Values and Not Set Currency's Exchange Rate
	 */
	public static void loadData () {
		Boolean readCurrencies = false;
		ArrayList<String> currencies = new ArrayList<>();
		String line;
		String[] splitLine;
		Integer lineLength;

		try {
			InputStream is = RateExchangeRepository.class.getClassLoader().getResourceAsStream("csv/eurofxref-hist.csv"); // CONSTANT
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			// Read From Buffer till End
			while ((line = br.readLine()) != null) {
				splitLine = line.split(",");
				lineLength = splitLine.length;

				// Check if Currencies Not Set
				if (!readCurrencies) {
					for (int i = 1; i < lineLength; i++) {
						currencies.add(splitLine[i]);
					}

					readCurrencies = true;
				}
				// Set Date Exchange Rates
				else {
					// Parse Day's Date
					Date dayDate = DateParser.parseDate(splitLine[0]);
					
					// Validate Exchange Rates Count
					if (lineLength - 1 != currencies.size()) {
						throw new Exception("Exchange Rates Doesn't match Header!");
					}

					// Build Day's Exchange Rates Map
					for (int i = 1; i < lineLength; i++) {
						// Skip Currency if N/A
						if (splitLine[i].equals("N/A")) { // CONSTANT
							continue;
						}
						
						// Set Day's Currency's Exchange Rate
						_rateExchangeHistory
							.setCurrencyExchangeRate(
								dayDate,
								currencies.get(i - 1),
								Double.parseDouble(splitLine[i])
							);
					}
				}
			}
		} catch (Exception e) {
			_logger.error("Couldn't Read Rate Exchange History!");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retrieve Currency's Exchange Rate for a Specific Day
	 * @param dayDate		Date	Requested Day's Date
	 * @param currency		String	Currency's Name
	 * @return Double
	 */
	public static Double getDayCurrencyRate (Date dayDate, String currency) {
		return _rateExchangeHistory.getCurrencyExchangeRate(dayDate, currency);
	}
	
	/**
	 * Retrieve Full Day Exchange Rates for a Specific Day
	 * @param dayDate		Date	Requested Day's Date
	 * @return HashMap<String, Double>
	 */
	public static HashMap<String, Double> getDayRates (Date dayDate) {
		return _rateExchangeHistory.getDayExchangeRates(dayDate);
	}
	
}
