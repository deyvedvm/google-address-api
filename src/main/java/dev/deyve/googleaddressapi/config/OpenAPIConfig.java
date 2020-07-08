package dev.deyve.googleaddressapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private static final Logger logger = LoggerFactory.getLogger(OpenAPIConfig.class);

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.description}") String appDescription
            , @Value("${application.version}") String appVersion) {

        logger.info("Custom OpenAPI created!");

        return new OpenAPI()
                .info(new Info().title("Google Address API")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
