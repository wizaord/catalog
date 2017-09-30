package com.orange.moos.catalog.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class ValidationService {
    /**
     * Validate the input message. A valid message must :
     *  - XXX
     *  - XXX
     * @param message
     */
    public void validateMessage(String message, Consumer<String> successAction, BiConsumer<String, String> failAction) {

        successAction.accept(message);
    }
}
