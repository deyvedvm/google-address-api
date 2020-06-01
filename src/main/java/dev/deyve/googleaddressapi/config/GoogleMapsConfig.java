package dev.deyve.googleaddressapi.config;

import com.google.maps.GeoApiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    private static final Logger logger = LoggerFactory.getLogger(GoogleMapsConfig.class);

    private final String apiKey = System.getenv("GOOGLE_API_KEY");

    @Bean
    public GeoApiContext getContext() {
        logger.info("GeoApiContext created!");
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }
}
