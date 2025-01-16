package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.chatop.dto.AuthenticationRequest;
import com.openclassrooms.chatop.dto.AuthenticationResponse;
import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Controller handling authentication endpoints
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user
     * @param request User registration details
     * @return Authentication response with JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user
     * @param request Login credentials
     * @return Authentication response with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /**
     * Gets current authenticated user
     * @return Current user details
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}