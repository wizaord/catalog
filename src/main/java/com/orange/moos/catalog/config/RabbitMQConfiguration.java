package com.orange.moos.catalog.config;

import com.orange.moos.catalog.listener.E_LISTENER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;


/**
 * Global RabbitMQ configuration
 */
@Configuration
@EnableRabbit
@Profile(AMQP)
public class RabbitMQConfiguration {

    public static final String VALIDATION_QUEUE_NAME = "com.orange.moos.validation";
    public static final String VALIDATION_EXCHANGE = "com.orange.moos.validationExchange";
    public static final String DECOMPOSITION_QUEUE_NAME = "com.orange.moos.decomposition";
    public static final String DECOMPOSITION_EXCHANGE = "com.orange.moos.decompositionExchange";
    public static final String SEQUENCING_QUEUE_NAME = "com.orange.moos.sequencing";
    public static final String SEQUENCING_EXCHANGE = "com.orange.moos.sequencingExchange";
    public static final String ERROR_OUTPUT_QUEUE_NAME = "com.orange.moos.output.error";
    public static final String ERROR_OUTPUT_EXCHANGE = "com.orange.moos.output.errorExchange";
    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfiguration.class);
    @Autowired
    private PropertiesRabbitMq appProperties;

    /**
     * Return the queue name for the specific listener.
     *
     * @param listener
     * @return
     */
    public static String getQueueName(final E_LISTENER listener) {
        String queueName;
        switch (listener) {
            case SEQUENCING:
                queueName = SEQUENCING_QUEUE_NAME;
                break;
            case VALIDATION:
                queueName = VALIDATION_QUEUE_NAME;
                break;
            case DECOMPOSITION:
                queueName = DECOMPOSITION_QUEUE_NAME;
                break;
            default:
                log.error("Queue not defined. Missing implementation");
                queueName = "";
        }
        return queueName;
    }

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

    /**
     * The connection factory to the rabbitMQ instance.
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(String.join(",", this.appProperties.getAdresses()));
        connectionFactory.setUsername(this.appProperties.getUsername());
        connectionFactory.setPassword(this.appProperties.getPassword());
        connectionFactory.setPublisherConfirms(true); // to activate ack
        return connectionFactory;
    }

    // TODO : voir pour utiliser la conf spring.rabbitmq et non pas la conf custo comme j'ai fait
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(this.appProperties.getConcurrentConsumers());
        factory.setMaxConcurrentConsumers(this.appProperties.getMaxConcurrentConsumers());
        factory.setMessageConverter(jsonMessageConverter());
//        factory.setChannelTransacted(true);
        factory.setPrefetchCount(this.appProperties.getPrefetchCount());         // The number of messages to accept from the broker in one socket frame.
        return factory;
    }
}
