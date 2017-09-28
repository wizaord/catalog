package com.orange.moos.catalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Global RabbitMQ configuration
 */
@Configuration
public class RabbitMQConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    @Autowired
    private PropertiesRabbitMq appProperties;

    @PostConstruct
    public void log() {
        log.info("Configuration RabbitMQ: successfully loaded");
        log.info("  - Addresses nodes: {}", String.join(",", this.appProperties.getAdresses()));
        log.info("  - rabbitMQ username: {}", this.appProperties.getUsername());
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(String.join(",", this.appProperties.getAdresses()));
        connectionFactory.setUsername(this.appProperties.getUsername());
        connectionFactory.setPassword(this.appProperties.getPassword());
        connectionFactory.setPublisherConfirms(true); // to activate ack
        return connectionFactory;
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(this.appProperties.getConcurrentConsumers());
        factory.setMaxConcurrentConsumers(this.appProperties.getMaxConcurrentConsumers());
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
