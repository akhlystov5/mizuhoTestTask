package com.mizuho;


import com.mizuho.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class SchedulerConfig {

    @Autowired
    PriceService priceService;

    @Scheduled(initialDelay = 30*1000, fixedDelay = 30*1000)
    public void scheduleFixedDelayTask() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(30);
        log.info( "Fixed delay task. should delete prices older than - " + localDateTime);
        priceService.deletePricesOlderThan(localDateTime);
    }
}
