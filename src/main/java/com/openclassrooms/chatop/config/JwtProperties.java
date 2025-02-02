package com.openclassrooms.chatop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration properties for JWT (JSON Web Token) settings.
 */
@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtProperties {

    /**
     * Secret key used for JWT signature.
     */
    private String secretKey;

    /**
     * Token expiration time in milliseconds.
     */
    private long expiration;

    /**
     * Refresh token expiration time in milliseconds.
     */
    private long refreshExpiration;
}