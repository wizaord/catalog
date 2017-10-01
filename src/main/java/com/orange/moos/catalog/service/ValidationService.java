package com.orange.moos.catalog.service;

import com.orange.moos.catalog.domain.DeliverOrders;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class ValidationService {
    /**
     * Validate the input message. A valid message must :
     * - XXX
     * - XXX
     *
     * @param deliverOrders : {@link DeliverOrders}
     */
    public void validateMessage(DeliverOrders deliverOrders, Consumer<DeliverOrders> successAction, BiConsumer<DeliverOrders, String> failAction) {

        successAction.accept(deliverOrders);
    }
}
