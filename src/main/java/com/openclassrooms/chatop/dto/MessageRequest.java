package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for message requests.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for sending a message related to a rental")
public class MessageRequest {

    /**
     * Content of the message.
     */
    @NotBlank(message = "Message cannot be empty")
    @Schema(description = "Content of the message", example = "Hello, I am interested in your rental!")
    private String message;

    /**
     * ID of the user sending the message.
     */
    @NotNull(message = "User ID is required")
    @JsonProperty("user_id")
    @Schema(description = "ID of the user sending the message", example = "1")
    private Long userId;

    /**
     * ID of the rental associated with the message.
     */
    @NotNull(message = "Rental ID is required")
    @JsonProperty("rental_id")
    @Schema(description = "ID of the rental associated with the message", example = "1")
    private Long rentalId;
}