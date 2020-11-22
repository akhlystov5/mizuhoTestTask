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
public class ClientSubscriptionController {

    @JmsListener(destination = "vendor-data-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageLGIM(String message) {
        log.info("LGIM has received vendor data <" + message + ">");
        //LGIM should read file from the filepath passed as a parameter
    }

    @JmsListener(destination = "vendor-data-topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveMessageMizuho(String message) {
        log.info("Mizuho has received vendor data <" + message + ">");
        //Mizuho should read file from the filepath passed as a parameter
    }
}