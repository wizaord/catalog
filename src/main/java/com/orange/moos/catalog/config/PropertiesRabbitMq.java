package com.orange.moos.catalog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Properties specific to MOOS Catalog RABBITMQ.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application.rabbitmq", ignoreUnknownFields = false)
@Validated
@Profile("AMQP")
public class PropertiesRabbitMq {

    private List<String> adresses;
    private String username;
    private String password;
    private Integer concurrentConsumers = 3;
    private Integer maxConcurrentConsumers = 10;


    public List<String> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<String> adresses) {
        this.adresses = adresses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxConcurrentConsumers() {
        return maxConcurrentConsumers;
    }

    public void setMaxConcurrentConsumers(Integer maxConcurrentConsumers) {
        this.maxConcurrentConsumers = maxConcurrentConsumers;
    }

    public Integer getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(Integer concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }
}
