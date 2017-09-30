package com.orange.moos.catalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Global RabbitMQ configuration
 */
@Configuration
public class RabbitMQConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfiguration.class);
    public static final String VALIDATION_QUEUE_NAME = "com.orange.moos.validation";
    public static final String VALIDATION_EXCHANGE = "com.orange.moos.validationExchange";
    public static final String DECOMPOSITION_QUEUE_NAME = "com.orange.moos.decomposition";
    public static final String DECOMPOSITION_EXCHANGE = "com.orange.moos.decompositionExchange";
    public static final String SEQUENCING_QUEUE_NAME = "com.orange.moos.sequencing";
    public static final String SEQUENCING_EXCHANGE = "com.orange.moos.sequencingExchange";
    public static final String ERROR_OUTPUT_QUEUE_NAME = "com.orange.moos.output.error";
    public static final String ERROR_OUTPUT_EXCHANGE = "com.orange.moos.output.errorExchange";

    @Autowired
    private PropertiesRabbitMq appProperties;

    @PostConstruct
    public void log() {
        log.info("Configuration RabbitMQ: successfully loaded");
        log.info("  - Addresses nodes: {}", String.join(",", this.appProperties.getAdresses()));
        log.info("  - rabbitMQ username: {}", this.appProperties.getUsername());
        log.info("  - rabbitMQ listener init consumers: {}", this.appProperties.getConcurrentConsumers());
        log.info("  - rabbitMQ listener max consumers: {}", this.appProperties.getMaxConcurrentConsumers());
    }

    @Bean
    public FanoutExchange fanoutValidation() {
        return new FanoutExchange(VALIDATION_EXCHANGE);
    }
    @Bean
    public FanoutExchange fanoutDecomposition() {
        return new FanoutExchange(DECOMPOSITION_EXCHANGE);
    }
    @Bean
    public FanoutExchange fanoutSequencing() {
        return new FanoutExchange(SEQUENCING_EXCHANGE);
    }
    @Bean
    public FanoutExchange fanoutOutputError() {
        return new FanoutExchange(ERROR_OUTPUT_EXCHANGE);
    }

    @Bean
    public Queue validationQueue() {
        return new Queue(VALIDATION_QUEUE_NAME, true);
    }
    @Bean
    public Queue decompositionQueue() {
        return new Queue(DECOMPOSITION_QUEUE_NAME, true);
    }
    @Bean
    public Queue sequencingQueue() {
        return new Queue(SEQUENCING_QUEUE_NAME, true);
    }
    @Bean
    public Queue errorOutputQueue() {
        return new Queue(ERROR_OUTPUT_QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingValidation(FanoutExchange fanoutValidation, Queue validationQueue) {
        return BindingBuilder.bind(validationQueue).to(fanoutValidation);
    }
    @Bean
    public Binding bindingDecomposition(FanoutExchange fanoutDecomposition, Queue decompositionQueue) {
        return BindingBuilder.bind(decompositionQueue).to(fanoutDecomposition);
    }
    @Bean
    public Binding bindingSequencing(FanoutExchange fanoutSequencing, Queue sequencingQueue) {
        return BindingBuilder.bind(sequencingQueue).to(fanoutSequencing);
    }
    @Bean
    public Binding bindingError(FanoutExchange fanoutOutputError, Queue errorOutputQueue) {
        return BindingBuilder.bind(errorOutputQueue).to(fanoutOutputError);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
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
