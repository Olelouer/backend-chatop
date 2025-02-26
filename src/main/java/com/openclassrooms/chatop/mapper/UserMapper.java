package com.openclassrooms.chatop.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.dto.UserResponse;
import com.openclassrooms.chatop.model.Role;
import com.openclassrooms.chatop.model.User;

import lombok.RequiredArgsConstructor;

/**
 * Mapper class for transforming between User entities and DTOs.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    /**
     * Converts a registration request to a User entity.
     *
     * @param request The registration request DTO.
     * @return The constructed User entity.
     */
    public User toEntity(RegisterRequest request) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
    }

    /**
     * Create a UserResponse DTO for returning user details
     * without sensitive information like passwords.
     *
     * @param user The User entity.
     * @return A UserResponse DTO.
     */
    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}