package com.orange.moos.catalog.listener.jms;

import com.orange.moos.catalog.CatalogApplication;
import com.orange.moos.catalog.domain.DeliverOrders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.JMS;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles(JMS)
public class JmsValidationListenerTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsListenerEndpointRegistry jmsListenerEndpointRegistry;

    @Value("application.jms.validation")
    private String jmsQueueName;

    @Before
    public void startListener() {
        this.jmsListenerEndpointRegistry.start();
    }

    @After
    public void stopListener() {
        this.jmsListenerEndpointRegistry.stop();
    }

    @Test
    public void receiveMessage() throws Exception {
        final DeliverOrders deliverOrder = DeliverOrders.builder()
                .orderId("orderId")
                .receivedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        this.jmsTemplate.convertAndSend(jmsQueueName, deliverOrder);
    }

}