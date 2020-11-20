package com.mizuho.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@Component
@Slf4j
public class VendorFilePublishedQueueListener {

    @Autowired
    JmsTemplate jmsTemplate;

//    @Autowired
//    ConnectionFactory jmsTopicListenerContainerFactory;

    @JmsListener(destination = "vendor-file-published-queue", containerFactory = "myFactory")
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        //TODO save message

        //TODO send to distribution topic
//        jmsTemplate.setConnectionFactory(jmsTopicListenerContainerFactory);
        jmsTemplate.convertAndSend("clients-topic", message);
        log.info("Published to a topic <" + message + ">");
    }

}