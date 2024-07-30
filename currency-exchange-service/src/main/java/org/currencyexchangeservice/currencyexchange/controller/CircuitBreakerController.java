package org.currencyexchangeservice.currencyexchange.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class CircuitBreakerController {

    private Logger logger = Logger.getLogger(CircuitBreakerController.class.getName());


    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
    public String sampleApi(){
        logger.info("Sample api recieved ");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/sample-api", String.class);
        return forEntity.getBody();
    }

    public String hardCodedResponse(Exception exception){
        return "hardcoded response";
    }
}
