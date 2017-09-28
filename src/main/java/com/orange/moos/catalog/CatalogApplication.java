package com.orange.moos.catalog;

import com.orange.moos.catalog.config.PropertiesRabbitMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

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
                        "Version '{}' \n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getActiveProfiles());
    }
}
