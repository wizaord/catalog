package com.orange.moos.catalog.config;

import com.orange.moos.catalog.admin.Profiles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class ProfileConfiguration {

    @Bean("profiles")
    @Order(1)
    public Profiles profiles() {
        final Profiles profiles = new Profiles();
        return profiles;
    }
}
