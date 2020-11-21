package com.mizuho.transformer;

import com.mizuho.model.Price;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PriceTransformerTest {

    PriceTransformer priceTransformer = new PriceTransformer();

    @Test
    public void extractPricesBloomberg() {
        List<String> lines = new ArrayList<>();
        lines.add("Instrument Name, ISIN, Price");
        lines.add("Nike Inc Stock,US6541061031,100.00");
        lines.add("Adidas AG Stock,DE000A1EWWW0,120.00");

        List<Price> prices = priceTransformer.extractPrices("Bloomberg 1.csv", lines);
        assertEquals(2, prices.size());

        Price firstPrice = prices.get(0);
        assertEquals("Nike Inc Stock", firstPrice.getInstrument());
        assertEquals("US6541061031", firstPrice.getIsin());
        assertEquals(new BigDecimal("100.00"), firstPrice.getPrice());
        assertEquals("Bloomberg", firstPrice.getVendor());

        Price secondPrice = prices.get(1);
        assertEquals("Adidas AG Stock", secondPrice.getInstrument());
        assertEquals("DE000A1EWWW0", secondPrice.getIsin());
        assertEquals(new BigDecimal("120.00"), secondPrice.getPrice());
        assertEquals("Bloomberg", secondPrice.getVendor());
    }

    @Test
    public void extractPricesIce() {
        List<String> lines = new ArrayList<>();
        lines.add("Instrument Name, ISIN, Price");
        lines.add("Nike Inc Stock,US6541061031,105.00");
        lines.add("Adidas AG Stock,DE000A1EWWW0,125.00");

        List<Price> prices = priceTransformer.extractPrices("ICE 2.csv", lines);
        assertEquals(2, prices.size());

        Price firstPrice = prices.get(0);
        assertEquals("Nike Inc Stock", firstPrice.getInstrument());
        assertEquals("US6541061031", firstPrice.getIsin());
        assertEquals(new BigDecimal("105.00"), firstPrice.getPrice());
        assertEquals("ICE", firstPrice.getVendor());
    }

}