package com.openclassrooms.chatop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI chatopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chatop API")
                        .description("API pour la gestion des locations")
                        .version("1.0"));
    }
}