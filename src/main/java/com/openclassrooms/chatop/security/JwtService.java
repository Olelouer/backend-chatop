package com.openclassrooms.chatop.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Service for JWT operations, including token generation, validation, and claims extraction.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    /**
     * Extracts the username (email) from a given JWT token.
     *
     * @param token JWT token
     * @return Extracted username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a given JWT token.
     *
     * @param token JWT token
     * @param claimsResolver Function to extract claim
     * @return Extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails User details
     * @return Generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims.
     *
     * @param extraClaims Additional claims to include in the token
     * @param userDetails User details
     * @return Generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token by checking its expiration and matching username.
     *
     * @param token JWT token
     * @param userDetails User details
     * @return true if token is valid, otherwise false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token JWT token
     * @return true if token is expired, otherwise false
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token JWT token
     * @return Expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token JWT token
     * @return Claims object containing all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT token encryption.
     *
     * @return HMAC-SHA key used for signing the JWT token
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
