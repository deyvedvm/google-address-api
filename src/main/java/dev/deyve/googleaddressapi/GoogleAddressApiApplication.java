package dev.deyve.googleaddressapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleAddressApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(GoogleAddressApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GoogleAddressApiApplication.class, args);
        logger.info("Running Google Address Application!");
        logger.info("Running on java: " + System.getProperty("java.version"));
    }
}
