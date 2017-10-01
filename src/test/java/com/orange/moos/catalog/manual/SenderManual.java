package com.orange.moos.catalog.manual;

import com.orange.moos.catalog.CatalogApplication;
import com.orange.moos.catalog.domain.DeliverOrders;
import com.orange.moos.catalog.listener.rabbitmq.ValidationListener;
import com.orange.moos.catalog.service.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.AMQP;
import static com.orange.moos.catalog.config.RabbitMQConfiguration.DECOMPOSITION_EXCHANGE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class)
@ActiveProfiles(AMQP)
public class SenderManual {

    private static final Logger log = LoggerFactory.getLogger(SenderManual.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SpyBean
    private ValidationService validationService;

    @Autowired
    private ValidationListener validationListener;

    @Test
    public void send() throws Exception {
        final DeliverOrders deliverOrder = DeliverOrders.builder().orderReferenceId("orderref")
                .headerParameter("param1", "value1")
                .build();

//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            log.info("receive ack => {}", ack);
//        });

        for (int i = 0; i < 1; i++) {
            deliverOrder.setOrderId(String.valueOf(i));
            this.rabbitTemplate.convertAndSend(DECOMPOSITION_EXCHANGE, "", deliverOrder);
            log.info(" [{}] sent message", i);
            Thread.sleep(100);
        }

        Thread.sleep(5000);
    }
}
