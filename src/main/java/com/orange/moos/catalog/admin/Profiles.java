package com.orange.moos.catalog.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.orange.moos.catalog.admin.E_PROFILES.*;

public class Profiles {

    private static final Logger log = LoggerFactory.getLogger(Profiles.class);

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    // static list to store all active profiles
    public final List<E_PROFILES> activesProfiles = new LinkedList<>();

    /**
     * Load the profiles from the Spring Application Context.
     * Check that all profiles are defined in the application.
     * Then check that these profiles are not incompatibles
     */
    @PostConstruct
    public void loadAndCheckProfiles() {
        final List<String> profiles = Arrays.asList(this.configurableApplicationContext.getEnvironment().getActiveProfiles());
        // check that all profiles are defined
        boolean status = allProfileValid((String[]) profiles.toArray());
        if (!status) {
            log.error("At least one profiles are not defined in the application. Please check your configuration !");
            return;
        }

        //store all profiles
        profiles.stream()
                .map(s -> valueOf(s))
                .forEach(e_profiles -> activesProfiles.add(e_profiles));
        //check profile
        final boolean isValid = checkProfiles();

        if (isValid) {
            log.info("PostConstruct : Configuration Profile: successfully loaded and checked");
        }
    }

    /**
     * Check if all profiles are know and if there is no incompatible
     * @return
     */
    public Boolean checkProfiles() {
        if (activesProfiles.size() == 0) {
            log.error("No profile has been set. Please check your configuration");
            return false;
        }

        boolean status = true;

        // dev and PROD are not compatibles
        if (activesProfiles.containsAll(Arrays.asList(DEV, PROD))) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
            status = false;
        }

        //if JMS is activated. ACTIVEMQ or TIBCOEMS must be activated
        if (activesProfiles.contains(JMS)) {
            //at least ACTIVEMQ or TIBCOEMS
            if (!activesProfiles.contains(ACTIVEMQ) && !activesProfiles.contains(TIBCOEMS)) {
                log.error("JMS profile is activated. You have to select a JMS connection factory : ACTIVEMQ ou TIBCOEMS");
                status = false;
            }

            //not ACTIVEMQ and TIBCOEMSw
            if (activesProfiles.containsAll(Arrays.asList(ACTIVEMQ, TIBCOEMS))) {
                status = false;
            }
        }
        return status;
    }

}
