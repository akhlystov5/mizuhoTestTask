package com.mizuho.controller;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PriceRestController {

    @Autowired
    PriceService priceService;

    @GetMapping(path = "/vendor/{vendor}")
    public ResponseEntity<List<Price>> byVendor(@PathVariable String vendor) {
        log.info("get prices by vendor {}", vendor);
        List<Price> prices = priceService.getPrices(vendor);
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }
}
