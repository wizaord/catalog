package com.orange.moos.catalog.listener.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.moos.catalog.config.RabbitMQConfiguration;
import com.orange.moos.catalog.domain.DeliverOrders;
import com.orange.moos.catalog.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;
import static com.orange.moos.catalog.listener.E_LISTENER.Constants.VALIDATION;


@Component
@Profile(AMQP)
public class ValidationListener {

    private static final Logger log = LoggerFactory.getLogger(ValidationListener.class);
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ValidationService validationService;

    private void sendValidMessage(DeliverOrders deliverOrders) {
        template.convertAndSend(RabbitMQConfiguration.DECOMPOSITION_EXCHANGE, "", deliverOrders);
    }

    private void sendErrorMessage(DeliverOrders deliverOrders, String errorMessage) {
        template.convertAndSend(RabbitMQConfiguration.ERROR_OUTPUT_EXCHANGE, "", deliverOrders);
    }

    /**
     * The receive method definition.
     * All message from the AMQP queue 'decompositionQueue' will be consume by this method.
     *
     * @param deliverOrders
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @RabbitListener(id = VALIDATION,
            queues = "#{validationQueue.name}",
            containerFactory = "rabbitListenerContainerFactory")
    public void receive(DeliverOrders deliverOrders) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(deliverOrders);
        log.info("Validation receive message=>" + jsonInString);

        // call the validation service
        this.validationService.validateMessage(deliverOrders,
                s -> this.sendValidMessage(s), // call this message is message is valid
                (s, e) -> this.sendErrorMessage(s, e)); // call this function when validation fails
    }
}
