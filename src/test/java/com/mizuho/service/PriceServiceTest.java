package com.mizuho.service;

import com.mizuho.model.Price;
import com.mizuho.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class PriceServiceTest {

    @InjectMocks
    PriceService priceService;

    @Mock
    PriceRepository priceRepository;

    @Test
    public void savePrices() {
        ArrayList<Price> vendorPrices = new ArrayList<>(Arrays.asList(Price.builder().isin("isin1").vendor("vendor1").build()));
        priceService.savePrices(vendorPrices);

        Mockito.verify(priceRepository).deleteAllByVendor("vendor1");
        Mockito.verify(priceRepository).saveAll(vendorPrices);
    }

    @Test
    public void getPrices() {
        priceService.getPricesByVendor("vendor1");
        Mockito.verify(priceRepository).findByVendor("vendor1");
    }

    @Test
    public void getPricesByIsin() {
        priceService.getPricesByIsin("isin1");
        Mockito.verify(priceRepository).findByIsin("isin1");
    }

    @Test
    public void deletePricesOlderThan() {
        LocalDateTime now = LocalDateTime.now();
        priceService.deletePricesOlderThan(now);
        Mockito.verify(priceRepository).deleteAllByCreatedLessThanEqual(now);

    }
}