package org.limitservice.limits.controller;

import org.limitservice.limits.Configuration;
import org.limitservice.limits.model.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {

    @Autowired
    private Configuration configuration;


    @GetMapping("/limits")
    public Limit retrieveLimits() {
        return new Limit(configuration.getMinimum(), configuration.getMaximum());
        //return new Limit(1, 1000);
    }
}
