package com.orange.moos.catalog.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum E_PROFILES {
    DEV(Constants.DEV),
    PROD(Constants.PROD),
    AMQP(Constants.AMQP),           // use to activate RABBITMQ Listener
    ACTIVEMQ(Constants.ACTIVEMQ),   // use to activate ACTIVEMQ factory (must be used with JMS)
    JMS(Constants.JMS),             // use to activate JMS listener
    TIBCOEMS(Constants.TIBCOEMS),   // use to activate TIBCO EMS factory (must be used with JMS)
    REST(Constants.REST);           // use to activate the REST API (receiver and admin)

    // default logger
    private static final Logger log = LoggerFactory.getLogger(E_PROFILES.class);

    /**
     * Contient l'ensemble des profiles au format String.
     * Permet d'utiliser ces profiles dans des annotations.
     */
    public static class Constants {
        public static final String DEV = "DEV";
        public static final String PROD = "PROD";
        public static final String AMQP = "AMQP";
        public static final String ACTIVEMQ = "ACTIVEMQ";
        public static final String JMS = "JMS";
        public static final String TIBCOEMS = "TIBCOEMS";
        public static final String REST = "REST";
    }


    /**
     * Default constructor. Do nothing
     *
     * @param rest
     */
    E_PROFILES(final String rest) {

    }

    /**
     * Check that all string values passed in parameter are a profile configured in this application.
     *
     * @param profiles
     * @return true all profiles are known
     */
    public static boolean allProfileValid(final String[] profiles) {
        if (profiles != null && profiles.length != 0) {
            for (String profile : profiles) {

                try {
                    final E_PROFILES EPROFILES = E_PROFILES.valueOf(profile);
                } catch (IllegalArgumentException e) {
                    log.error("Profile {} is not defined in the application", profile);
                    return false;
                }
            }
        }
        return true;
    }
}
