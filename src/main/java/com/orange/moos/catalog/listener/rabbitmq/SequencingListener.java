package com.orange.moos.catalog.listener.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orange.moos.catalog.domain.DeliverOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;
import static com.orange.moos.catalog.listener.E_LISTENER.Constants.SEQUENCING;


@Component
@Profile(AMQP)
public class SequencingListener {

    private static final Logger log = LoggerFactory.getLogger(SequencingListener.class);

    @RabbitListener(id = SEQUENCING,
            queues = "#{sequencingQueue.name}",
            containerFactory = "rabbitListenerContainerFactory")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        log.info("Receive message {}", in);
    }

}
