package com.mizuho.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates the way clients can subscribe to a topic to receive prices updates from vendors
 * Clients will receive a notification with a file path to the .csv data file they can read
 *
 * @author Alexander Khlystov
 */
@Component
@Slf4j
public class ReadVendorDataTopicListener {

    @JmsListener(destination = "vendor-data-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageLGIM(String message) {
        //for logging purposes
        log.info("LGIM has received vendor data <" + message + ">");
    }

    @JmsListener(destination = "vendor-data-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageMizuho(String message) {
        //for logging purposes
        log.info("Mizuho has received vendor data <" + message + ">");
    }
}