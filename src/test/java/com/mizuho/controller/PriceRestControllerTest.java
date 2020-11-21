package com.mizuho.controller;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class PriceRestControllerTest {

    @InjectMocks
    PriceRestController priceRestController;

    @Mock
    PriceService priceService;

    @Test
    public void byVendor() {
        List<Price> prices = new ArrayList<>();
        Price price = Price.builder().vendor("vendor").isin("isin").price(BigDecimal.valueOf(100.00D)).build();
        prices.add(price);
        Mockito.when(priceService.getPricesByVendor("vendor")).thenReturn(prices);
        ResponseEntity<List<Price>> response = priceRestController.pricesByVendor("vendor");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prices.size(), response.getBody().size());

        Price receivedPrice = response.getBody().get(0);
        assertEquals("vendor", receivedPrice.getVendor());
        assertEquals("isin", receivedPrice.getIsin());
        assertEquals(BigDecimal.valueOf(100.00D), receivedPrice.getPrice());
    }

    @Test
    public void byIsin() {
        List<Price> prices = new ArrayList<>();
        Price price = Price.builder().vendor("vendor").isin("isin").price(BigDecimal.valueOf(100.00D)).build();
        prices.add(price);
        Mockito.when(priceService.getPricesByIsin("isin")).thenReturn(prices);
        ResponseEntity<List<Price>> response = priceRestController.pricesByIsin("isin");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prices.size(), response.getBody().size());

        Price receivedPrice = response.getBody().get(0);
        assertEquals("vendor", receivedPrice.getVendor());
        assertEquals("isin", receivedPrice.getIsin());
        assertEquals(BigDecimal.valueOf(100.00D), receivedPrice.getPrice());

    }

    @Test
    public void deleteOld() {
    }
}