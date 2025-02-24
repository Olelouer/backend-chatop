package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.chatop.dto.AuthenticationRequest;
import com.openclassrooms.chatop.dto.AuthenticationResponse;
import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Controller handling authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user.
     *
     * @param request User registration details.
     * @return Authentication response with JWT token.
     */
    @Operation(summary = "Register a new user", description = "Creates a new user and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters !", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized !", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates a user.
     *
     * @param request Login credentials.
     * @return Authentication response with JWT token.
     */
    @Operation(summary = "Authenticate user and generate JWT token", description = "Validates user credentials and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    /**
     * Gets the currently authenticated user.
     *
     * @return Current user details.
     */
    @Operation(summary = "Get current authenticated user details", description = "Retrieves the currently logged-in user based on the JWT token")
    @ApiResponse(responseCode = "200", description = "Authenticated user details retrieved successfully")
    @ApiResponse(responseCode = "401", description = "User not authenticated !", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
