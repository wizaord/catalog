package com.orange.moos.catalog.listener.jms;

import com.orange.moos.catalog.config.JmsConfiguration;
import com.orange.moos.catalog.domain.DeliverOrders;
import com.orange.moos.catalog.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.JMS;


@Component
@Profile(JMS)
public class JmsValidationListener {

    private static final Logger log = LoggerFactory.getLogger(JmsValidationListener.class);

    @Autowired
    private ValidationService validationService;

    @JmsListener(destination = "${application.jms.validationQueue}", containerFactory = JmsConfiguration.FACTORY_NAME)
    public void receiveMessage(DeliverOrders deliverOrders) {
        log.info("Received <" + deliverOrders.getOrderId() + ">");
    }
}
