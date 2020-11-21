package com.mizuho.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReadVendorDataTopicListener {

    @JmsListener(destination = "clients-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageLGIM(String message) {
        log.info("LGIM has received vendor data <" + message + ">");
    }

    @JmsListener(destination = "clients-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageMizuho(String message) {
        log.info("Mizuho has received vendor data <" + message + ">");
    }
}