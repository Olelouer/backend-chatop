package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.dto.UserResponse;
import com.openclassrooms.chatop.mapper.UserMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.dto.AuthenticationRequest;
import com.openclassrooms.chatop.dto.AuthenticationResponse;
import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.security.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Service handling authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    /**
     * Registers a new user, encodes their password, assigns a role, and generates a JWT token.
     *
     * @param request The registration request containing user details.
     * @return AuthenticationResponse containing the generated JWT token.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        // Use the mapper to convert from DTO to entity
        User user = userMapper.toEntity(request);

        // Save the user entity
        userRepository.save(user);

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user by verifying credentials and generating a JWT token.
     *
     * @param request The authentication request containing email and password.
     * @return AuthenticationResponse containing the generated JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated User object.
     * @throws RuntimeException if the user is not found.
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
    }
}