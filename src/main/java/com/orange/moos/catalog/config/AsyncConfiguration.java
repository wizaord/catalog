package com.orange.moos.catalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * Async configuration class. Allow to create schedule task
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @PostConstruct
    public void log() {
        log.info("Configuration Async: successfully loaded");
    }

}
