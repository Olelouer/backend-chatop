package com.openclassrooms.chatop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures OpenAPI documentation
     * @return OpenAPI configuration for Chatop API
     */
    @Bean
    public OpenAPI chatopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chatop API")
                        .description("API for rental management")
                        .version("1.0"));
    }
}