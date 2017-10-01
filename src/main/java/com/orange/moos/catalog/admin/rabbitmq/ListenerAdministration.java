package com.orange.moos.catalog.admin.rabbitmq;

import com.orange.moos.catalog.listener.E_LISTENER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;
import static com.orange.moos.catalog.config.RabbitMQConfiguration.getQueueName;

@Component
@Profile(AMQP)
public class ListenerAdministration {

    private static final Logger log = LoggerFactory.getLogger(ListenerAdministration.class);

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * Stop all AMQP listener.
     */
    public void stopAllListener() {
        this.rabbitListenerEndpointRegistry.stop();
    }

    /**
     * Start all AMQP listener defined in the application. If a listener is running, no action is realized on this listener
     */
    public void startAllListener() {
        this.rabbitListenerEndpointRegistry.start();
    }


    /**
     * This function stops a listener defined by its name.
     *
     * @param listener {@link E_LISTENER}
     */
    public void stopListener(final E_LISTENER listener) {
        this.rabbitListenerEndpointRegistry.getListenerContainer(listener.name()).stop();
    }

    /**
     * This function starts an AMQP listener defined by its name
     *
     * @param listener {@link E_LISTENER}
     */
    public void startListener(final E_LISTENER listener) {
        this.rabbitListenerEndpointRegistry.getListenerContainer(listener.name()).start();
    }

    /**
     * Get the status of a specific listener. Return true is the listener is running
     *
     * @param listener {@link E_LISTENER}
     * @return
     */
    public boolean isRunning(final E_LISTENER listener) {
        return this.rabbitListenerEndpointRegistry.getListenerContainer(listener.name()).isRunning();
    }

    /**
     * Log the status of all listeners defined in the application
     */
    public void logListenerStatus() {
        this.rabbitListenerEndpointRegistry.getListenerContainerIds().stream()
                .forEach(s -> {
                    final MessageListenerContainer listenerContainer = this.rabbitListenerEndpointRegistry.getListenerContainer(s);
                    log.info("ListenerId {} - isRunning : {}", s, listenerContainer.isRunning());
                });
    }

    /**
     * Return the number of message in a queue defined by its name.
     *
     * @param listener
     * @return
     */
    public long countMessageInQueue(final E_LISTENER listener) {
        final Properties queueProperties = this.amqpAdmin.getQueueProperties(getQueueName(listener));
        return Integer.parseInt(queueProperties.get("QUEUE_MESSAGE_COUNT").toString());
    }

    /**
     * Purge the queue listened by the listener.
     * @param listener
     */
    public void purgeQueue(final E_LISTENER listener) {
        this.amqpAdmin.purgeQueue(getQueueName(listener), false);
    }
}
