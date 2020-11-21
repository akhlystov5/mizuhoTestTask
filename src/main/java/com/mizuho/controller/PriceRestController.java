package com.mizuho.controller;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class PriceRestController {

    @Autowired
    PriceService priceService;

    //TODO move this to be
    //GET /tickets?sort=-priority - Retrieves a list of tickets in descending order of priority
    @GetMapping(path = "/prices/vendor/{vendor}")
    public ResponseEntity<List<Price>> pricesByVendor(@PathVariable String vendor) {
        log.info("get prices by vendor {}", vendor);
        List<Price> prices = priceService.getPrices(vendor);
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping(path = "/prices/isin/{isin}")
    public ResponseEntity<List<Price>> pricesByIsin(@PathVariable String isin) {
        log.info("get prices by isin {}", isin);
        List<Price> prices = priceService.getPricesByIsin(isin);
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    //TODO delete used for testing only
//    @PostMapping(path = "/delete/old")
//    public ResponseEntity<String> deleteOld() {
//        LocalDateTime timestamp = LocalDateTime.now().minusSeconds(30);
//        log.info("deleting data older than" + timestamp);
//        long deleted = priceService.deletePricesOlderThan(timestamp);
//        return new ResponseEntity<>(deleted + " price(s) deleted", HttpStatus.OK);
//    }

}
