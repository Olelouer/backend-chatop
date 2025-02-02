package com.openclassrooms.chatop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures OpenAPI documentation for the Chatop API.
     * @return OpenAPI configuration with API metadata.
     */
    @Bean
    public OpenAPI chatopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chatop API")
                        .description("API for rental management, providing authentication, messaging, and rental operations.")
                        .version("1.0"));
    }
}