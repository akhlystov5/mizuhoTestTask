package com.mizuho.listener;

import com.mizuho.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class VendorFilePublishedQueueListener {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    PriceService priceService;

    @JmsListener(destination = "vendor-file-published-queue", containerFactory = "myFactory")
    public void receiveMessage(String message) throws IOException {
        log.info("Received <" + message + ">");
        //TODO save message

        List<String> lines = FileUtils.readLines(new File(message), "UTF-8");
        log.info("lines.size()="+lines.size()+", lines="+lines);
        priceService.savePrices(message, lines);
        //TODO send to distribution topic
        jmsTemplate.convertAndSend("clients-topic", message);
        log.info("Published to a topic <" + message + ">");
    }

    //TODO think about exception handling
    //23:43:35.272 [DefaultMessageListenerContainer-1] WARN  o.s.j.l.DefaultMessageListenerContainer - Execution of JMS message listener failed, and no ErrorHandler has been set.
}