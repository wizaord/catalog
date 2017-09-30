package com.orange.moos.catalog.listener.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.moos.catalog.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationListener {

    private static final Logger log = LoggerFactory.getLogger(ValidationListener.class);

    @Autowired
    private ValidationService validationService;

    @RabbitListener(queues = "#{validationQueue.name}", containerFactory="rabbitListenerContainerFactory")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        log.info("Receive message {}", in);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = mapper.writeValueAsString(in);
//        System.out.println("receive =>" + jsonInString);
//        System.out.println(in.getSubSampleObjList().get(0).getMessage());
    }
}
