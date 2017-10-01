package com.orange.moos.catalog.admin.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("AMQP")
public class ListenerAdministration {

    private static final Logger log = LoggerFactory.getLogger(ListenerAdministration.class);

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    public void stopAllListener() {
        // TODO
    }

    public void startAllListener() {
        // TODO
    }

    public void listAllListener() {
        // TODO
    }

    public void stopListener(String listenerName) {
        // TODO
    }

    public void startListener(String listenerName) {
        // TODO
    }
}
