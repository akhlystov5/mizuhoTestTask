package com.mizuho.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsReceiver {

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(String email) {
        log.info("Received <" + email + ">");
    }

}