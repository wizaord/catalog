package com.orange.moos.catalog.listener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orange.moos.catalog.admin.E_PROFILES.Constants.REST;

@RestController
@RequestMapping("/admin")
@Profile(REST)
public class AdminApi {

    private final Logger log = LoggerFactory.getLogger(AdminApi.class);

    public void stopApplication() {
        // TODO
    }

    // TODO AJout aussi du stop par kill -XXX
}
