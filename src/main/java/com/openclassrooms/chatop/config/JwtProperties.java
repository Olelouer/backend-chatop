package com.openclassrooms.chatop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * JWT configuration properties
 */
@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtProperties {
    /** Secret key for JWT signature */
    private String secretKey;

    /** Token expiration time in ms */
    private long expiration;

    /** Refresh token expiration time in ms */
    private long refreshExpiration;
}