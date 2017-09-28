package com.orange.moos.catalog.listener.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ValidationListener {

    @RabbitListener(queues = "#{validationQueue.name}", containerFactory="rabbitListenerContainerFactory")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(in);
        System.out.println("receive =>" + jsonInString);
//        System.out.println(in.getSubSampleObjList().get(0).getMessage());

    }
}
