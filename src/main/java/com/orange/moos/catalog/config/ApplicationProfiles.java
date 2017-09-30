package com.orange.moos.catalog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ApplicationProfiles {
    DEV,
    PROD,
    AMQP,
    JMS,
    REST;

    private static final Logger log = LoggerFactory.getLogger(ApplicationProfiles.class);

    /**
     * Check that all string values passed in parameter are a profile configured in this application.
     * @param profiles
     * @return true all profiles are known
     */
    public static boolean allProfileValid(final String[] profiles) {
        if (profiles != null && profiles.length != 0) {
            for(String profile : profiles) {

                try {
                    final ApplicationProfiles applicationProfiles = ApplicationProfiles.valueOf(profile);
                } catch (IllegalArgumentException e) {
                    log.error("Profile {} is not defined in the application", profile);
                    return false;
                }
            }
        }
        return true;
    }
}
