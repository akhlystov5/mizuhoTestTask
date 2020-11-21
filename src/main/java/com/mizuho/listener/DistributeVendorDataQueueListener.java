package com.mizuho.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DistributeVendorDataQueueListener {

    @JmsListener(destination = "clients-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
    }

    @JmsListener(destination = "clients-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessage2(String message) {
        log.info("Received2  <" + message + ">");
    }
}