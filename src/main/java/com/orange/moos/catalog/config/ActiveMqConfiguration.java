package com.orange.moos.catalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.JMS;

@Configuration
@EnableJms
@Profile(JMS)
public class ActiveMqConfiguration {

    public static final String FACTORY_NAME = "jmsFactory";
    private static final Logger log = LoggerFactory.getLogger(ActiveMqConfiguration.class);

    @PostConstruct
    public void log() {
        log.info("Configuration JMS: successfully loaded");
//        log.info("  - Addresses nodes: {}", String.join(",", this.appProperties.getAdresses()));
//        log.info("  - rabbitMQ username: {}", this.appProperties.getUsername());
//        log.info("  - rabbitMQ listener init consumers: {}", this.appProperties.getConcurrentConsumers());
//        log.info("  - rabbitMQ listener max consumers: {}", this.appProperties.getMaxConcurrentConsumers());
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
                                                     DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
