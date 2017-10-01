package com.orange.moos.catalog.manual;

import com.orange.moos.catalog.CatalogApplication;
import com.orange.moos.catalog.admin.E_PROFILES;
import com.orange.moos.catalog.listener.rabbitmq.ValidationListener;
import com.orange.moos.catalog.service.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@ActiveProfiles(AMQP)
public class SenderManual {

    private static final Logger log = LoggerFactory.getLogger(SenderManual.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutValidation;

    @SpyBean
    private ValidationService validationService;

    @Autowired
    private ValidationListener validationListener;

    @Test
    public void send() throws Exception {
        String message = "Hello World!";

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("receive ack => {}", ack);
        });

        for (int i = 0; i < 10; i++) {
            this.rabbitTemplate.convertAndSend(fanoutValidation.getName(), "", message);
            log.info(" [{}] sent '{}'", i, message);
        }

        Thread.sleep(5000);
    }
}
