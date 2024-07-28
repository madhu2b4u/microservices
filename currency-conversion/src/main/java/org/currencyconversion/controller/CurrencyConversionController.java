package org.currencyconversion.controller;

import org.currencyconversion.model.CurrencyConversion;
import org.currencyconversion.repository.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    // Key Point 1: Injecting the Feign client for currency exchange service
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    // Key Point 2: REST endpoint using RestTemplate for currency conversion
    @GetMapping("/currency-conversion/from/{fromCurrency}/to/{toCurrency}/quantity/{qty}")
    public CurrencyConversion calculateConversion(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency,
            @PathVariable BigDecimal qty) {

        // Key Point 3: Preparing URI variables for RestTemplate
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", fromCurrency);
        uriVariables.put("to", toCurrency);

        // Key Point 4: Using RestTemplate to make a HTTP GET request
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        // Key Point 5: Creating and returning a new CurrencyConversion object
        return new CurrencyConversion(
                currencyConversion.getId(),
                fromCurrency,
                toCurrency,
                qty,
                currencyConversion.getConversionMultiple(),
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()
        );
    }

    // Key Point 6: REST endpoint using Feign client for currency conversion
    @GetMapping("/currency-conversion-feign/from/{fromCurrency}/to/{toCurrency}/quantity/{qty}")
    public CurrencyConversion calculateFeignConversion(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency,
            @PathVariable BigDecimal qty) {

        // Key Point 7: Using Feign client to make the service call
        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(fromCurrency, toCurrency);

        // Key Point 8: Creating and returning a new CurrencyConversion object
        return new CurrencyConversion(
                currencyConversion.getId(),
                fromCurrency,
                toCurrency,
                qty,
                currencyConversion.getConversionMultiple(),
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " feign"
        );
    }
}

// Key Point 9: This controller provides two ways to perform currency conversion:
// 1. Using RestTemplate (more manual, direct HTTP call)
// 2. Using Feign client (more declarative, easier to use and maintain)

// Key Point 10: Both methods return a CurrencyConversion object with calculated results