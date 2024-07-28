package org.currencyconversion.repository;

import org.currencyconversion.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Key Point 1: This interface is a Feign client for making HTTP requests to the currency exchange service
//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient(name="currency-exchange-service")
public interface CurrencyExchangeProxy {

    // Key Point 2: This method maps to a GET request on the currency exchange service
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
            // Key Point 3: Path variables are used to dynamically construct the URL
            @PathVariable String from,
            @PathVariable String to
    );

    // Key Point 4: No method body is needed; Feign implements the HTTP call
    // Key Point 5: The commented-out annotation shows how to configure for a specific URL instead of service discovery
    // Key Point 6: Using name="currency-exchange-service" allows for service discovery and load balancing
    // Key Point 7: The return type (CurrencyConversion) will be automatically deserialized from the JSON response
    // Key Point 8: This interface can be injected and used like a regular Java interface, simplifying HTTP calls
}