package com.mizuho;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import com.mizuho.model.Price;
import com.mizuho.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJms
@Slf4j
@EnableScheduling
public class PricesApp {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setErrorHandler(t -> log.error("error occurred during processing q", t));
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);

        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(t -> log.error("error occurred during processing t", t));

        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public DynamicDestinationResolver destinationResolver() {
        return new DynamicDestinationResolver() {
            @Override
            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
                if (destinationName.endsWith("topic")) {
                    pubSubDomain = true;
                }
                return super.resolveDestinationName(session, destinationName, pubSubDomain);
            }
        };
    }

//    @Autowired
//    PriceService priceService;

    public static void main(String[] args) {
        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(PricesApp.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
//        jmsTemplate.setPubSubDomain(true);

        // Send a message with a POJO - the template reuse the message converter
        log.info("Sending a test message.");
        jmsTemplate.convertAndSend("mailbox", "info@example.com Hello");

        PriceService priceService = context.getBean(PriceService.class);
        priceService.savePrices(
                new ArrayList<>(Arrays.asList(
                        Price.builder().vendor("TestVendor").instrument("name1")
                                .isin("isin1").price(BigDecimal.valueOf(110.00D)).build(),
                        Price.builder().vendor("TestVendor").instrument("name2")
                                .isin("isin2").price(BigDecimal.valueOf(220.00D))
                                .created(LocalDateTime.now().minusDays(60)).build()
                )));
    }

}
