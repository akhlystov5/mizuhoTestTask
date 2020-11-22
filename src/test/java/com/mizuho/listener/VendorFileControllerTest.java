package com.mizuho.listener;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import com.mizuho.transformer.PriceTransformer;
import com.mizuho.util.FileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class VendorFileControllerTest {

    @InjectMocks
    VendorFileController vendorFileController;

    @Mock
    PriceService priceService;

    @Mock
    JmsTemplate jmsTemplate;

    @Mock
    PriceTransformer priceTransformer;

    @Mock
    FileReader fileReader;

    @Test
    public void receiveMessage() throws IOException {
        String message = "filePath";
        List<String> lines = new ArrayList<>();
        lines.add("Instrument Name, ISIN, Price");
        lines.add("Nike Inc Stock,US6541061031,100.00");
        lines.add("Adidas AG Stock,DE000A1EWWW0,120.00");
        Mockito.when(fileReader.readFileContent(message)).thenReturn(lines);
        List<Price> prices = new ArrayList<>(Arrays.asList(
                Price.builder().instrument("Nike Inc Stock").isin("US6541061031").price(BigDecimal.valueOf(100.00d)).build(),
                Price.builder().instrument("Adidas AG Stock").isin("DE000A1EWWW0").price(BigDecimal.valueOf(120.00d)).build()
        ));
        Mockito.when(priceTransformer.extractPrices(message, lines)).thenReturn(prices);

        vendorFileController.receiveMessage(message);

        Mockito.verify(priceService,Mockito.times(1)).savePrices(prices);
        Mockito.verify(jmsTemplate).convertAndSend("vendor-data-topic", message);
    }
}