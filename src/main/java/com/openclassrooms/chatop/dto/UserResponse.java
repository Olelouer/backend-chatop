package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user information responses.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for user information responses")
public class UserResponse {

    /**
     * User ID.
     */
    @Schema(description = "User ID", example = "1")
    private Long id;

    /**
     * User's full name.
     */
    @Schema(description = "User's full name", example = "John Doe")
    private String name;

    /**
     * User email address.
     */
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    /**
     * Timestamp of when the user was created.
     */
    @JsonProperty("created_at")
    @Schema(description = "Timestamp when the user was created", example = "2024/02/01 12:00:00")
    private String createdAt;

    /**
     * Timestamp of when the user was updated.
     */
    @JsonProperty("updated_at")
    @Schema(description = "Timestamp when the user was updated", example = "2024/02/01 12:00:00")
    private String updatedAt;
}