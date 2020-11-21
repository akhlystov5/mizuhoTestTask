package com.mizuho.listener;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import com.mizuho.transformer.PriceTransformer;
import com.mizuho.util.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * is triggered by JMS Message when Vendor .csv file is ready to be read
 */
@Component
@Slf4j
public class VendorFilePublishedQueueListener {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    PriceTransformer priceTransformer;

    @Autowired
    PriceService priceService;

    @Autowired
    FileReader fileReader;

    @JmsListener(destination = "vendor-file-published-queue", containerFactory = "myFactory")
    public void receiveMessage(String message) throws IOException {
        log.info("Received <" + message + ">");

        List<String> lines = fileReader.readFileContent(message);
        log.info("lines.size()="+lines.size()+", lines="+lines);

        List<Price> prices = priceTransformer.extractPrices(message, lines);
        priceService.savePrices(prices);

        //send to distribution topic
        jmsTemplate.convertAndSend("vendor-data" +
                "-topic", message);
        log.info("Published to a topic <" + message + ">");
    }

    //TODO think about exception handling
}