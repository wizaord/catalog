package com.orange.moos.catalog.config;

import com.orange.moos.catalog.admin.E_PROFILES;
import com.orange.moos.catalog.admin.Profiles;
import com.tibco.tibjms.TibjmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.JMS;
import static com.orange.moos.catalog.admin.E_PROFILES.Constants.TIBCOEMS;

@Configuration
@EnableJms
@Profile(JMS)
public class JmsConfiguration {
    
    public static final String ACTIVEMQ_BROKER = "spring.activemq.broker-url";
    public static final String ACTIVEMQ_USER = "spring.activemq.user";
    public static final String ACTIVEMQ_PASS = "spring.activemq.password";

    public static final String TIBCOEMS_BROKER = "application.tibcoems.broker-url";
    public static final String TIBCOEMS_USER = "application.tibcoems.user";
    public static final String TIBCOEMS_PASS = "application.tibcoems.password";

    public static final String FACTORY_NAME = "jmsFactory";

    private static final Logger log = LoggerFactory.getLogger(JmsConfiguration.class);

    @Autowired
    private Profiles profiles;

    @Autowired
    private Environment env;

    @PostConstruct
    @DependsOn("profiles")
    public void log() {
        log.info("PostConstruct : Configuration JMS: successfully loaded");
        if (profiles.activesProfiles.contains(E_PROFILES.ACTIVEMQ)) {
            log.info("  Factory used : ACTIVEMQ");
            log.info("  Broker Url : {}", env.getProperty(ACTIVEMQ_BROKER));
            log.info("  login : {}", env.getProperty(ACTIVEMQ_USER));
        }
        if (profiles.activesProfiles.contains(E_PROFILES.TIBCOEMS)) {
            log.info("  Factory used : TIBCOEMS");
            log.info("  Broker Url : {}", env.getProperty(TIBCOEMS_BROKER));
            log.info("  login : {}", env.getProperty(TIBCOEMS_USER));
        }
    }

    @Bean
    @Profile(TIBCOEMS)
    public ConnectionFactory connectionFactory(){
        log.info("  JMS configuration - Create TibcoEMS ConnectionFactory");
        TibjmsConnectionFactory connectionFactory = new TibjmsConnectionFactory(env.getProperty(TIBCOEMS_BROKER));
        connectionFactory.setUserName(env.getProperty(TIBCOEMS_USER));
        connectionFactory.setUserPassword(env.getProperty(TIBCOEMS_PASS));
        return connectionFactory;
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
