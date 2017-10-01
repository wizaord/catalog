package com.orange.moos.catalog;

import com.orange.moos.catalog.config.PropertiesRabbitMq;
import com.orange.moos.catalog.admin.E_PROFILES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication //@EnableAutoConfiguration , @ComponentScan, SpringBootConfiguration
@EnableConfigurationProperties(PropertiesRabbitMq.class)
public class CatalogApplication {

    private static final Logger log = LoggerFactory.getLogger(CatalogApplication.class);

    private final Environment env;

    public CatalogApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CatalogApplication.class);
        Environment env = app.run(args).getEnvironment();


        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running!\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getActiveProfiles());

        //check profile
        checkProfiles(Arrays.asList(env.getActiveProfiles()));
    }

    public static boolean checkProfiles(List<String> profiles) {
        if (profiles == null || profiles.size() == 0) {
            log.error("No profile has been set. Please check your configuration");
            return false;
        }

        // check that all profiles are knows
        boolean status = E_PROFILES.allProfileValid((String[]) profiles.toArray());

        // dev and PROD are not compatibles
        if (profiles.contains(E_PROFILES.DEV.name()) && profiles.contains(E_PROFILES.PROD.name())) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
            status = false;
        }
        return status;
    }
}
