package com.mizuho.service;

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
        //TODO save message

        //TODO send to distribution topic
    }

    @JmsListener(destination = "clients-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessage2(String message) {
        log.info("Received2  <" + message + ">");
        //TODO save message

        //TODO send to distribution topic
    }
}