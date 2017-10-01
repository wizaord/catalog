package com.orange.moos.catalog.listener.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orange.moos.catalog.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
    private ValidationService validationService;

    private static void sendValidMessage(String message) {
        log.info("Valid => " + message);
    }

    private static void sendErrorMessage(String message, String errorMessage) {
        log.info("Not Valid => " + message + " because " + errorMessage);
    }

    /**
     * The receive method definition.
     * All message from the AMQP queue 'decompositionQueue' will be consume by this method.
     *
     * @param in
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @RabbitListener(id = VALIDATION,
            queues = "#{validationQueue.name}",
            containerFactory = "rabbitListenerContainerFactory")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        log.info("Receive message {}", in);
        validateMessageAndSendNext(in);

//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(in);
//        System.out.println(in.getSubSampleObjList().get(0).getMessage());
    }

    public void validateMessageAndSendNext(String message) {
        //convert message

        //valid message
        this.validationService.validateMessage(message,
                ValidationListener::sendValidMessage, //call this message is message is valid
                ValidationListener::sendErrorMessage);
    }
}
