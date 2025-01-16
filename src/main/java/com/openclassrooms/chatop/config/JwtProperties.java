package com.openclassrooms.chatop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private long expiration;
    private long refreshExpiration;
}