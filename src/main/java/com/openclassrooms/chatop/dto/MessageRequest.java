package com.openclassrooms.chatop.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for message requests
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @NotBlank(message = "Message cannot be empty")
    private String message;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "User ID is required")
    private Long rentalId;
}
