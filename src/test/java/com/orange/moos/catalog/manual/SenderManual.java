package com.orange.moos.catalog.manual;

import com.orange.moos.catalog.CatalogApplication;
import com.orange.moos.catalog.service.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@ActiveProfiles("amqp")
public class SenderManual {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutValidation;

    @SpyBean
    private ValidationService validationService;

    @Test
    public void send() throws Exception {
        String message = "Hello World!";

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("receive ack => " + ack);
        });

        for (int i = 0; i < 10; i++) {
            this.rabbitTemplate.convertAndSend(fanoutValidation.getName(), "", message);
            System.out.println(" [" + i + "] sent '" + message + "'");
        }

        Thread.sleep(100000);
    }
}
