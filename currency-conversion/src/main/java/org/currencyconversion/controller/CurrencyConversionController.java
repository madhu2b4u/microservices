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

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{fromCurrency}/to/{toCurrency}/quantity/{qty}")
    public CurrencyConversion calculateonversion(@PathVariable String fromCurrency,
                                                  @PathVariable String toCurrency,
                                                  @PathVariable BigDecimal qty){

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from", fromCurrency);
        uriVariables.put("to", toCurrency);

        ResponseEntity<CurrencyConversion> responseEntity =  new RestTemplate().getForEntity( "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();
        return  new CurrencyConversion(
                currencyConversion.getId(),
                fromCurrency,
                toCurrency,
                qty,
                currencyConversion.getConversionMultiple(),
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()
        );
    }

    @GetMapping("/currency-conversion-feign/from/{fromCurrency}/to/{toCurrency}/quantity/{qty}")
    public CurrencyConversion calculateFeignCConversion(@PathVariable String fromCurrency,
                                                        @PathVariable String toCurrency,
                                                        @PathVariable BigDecimal qty){

        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(fromCurrency, toCurrency);
        return  new CurrencyConversion(
                currencyConversion.getId(),
                fromCurrency,
                toCurrency,
                qty,
                currencyConversion.getConversionMultiple(),
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+ " feign"
        );
    }
}
