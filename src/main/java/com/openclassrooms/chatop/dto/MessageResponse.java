package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for message details responses")
public class MessageResponse {
    @Schema(description = "Unique identifier of the message", example = "1")
    private Long id;

    @Schema(description = "Content of the message", example = "Hello, I am interested in your rental!")
    private String message;

    @Schema(description = "ID of the user who sent the message", example = "1")
    private Long userId;

    @Schema(description = "ID of the rental associated with the message", example = "1")
    private Long rentalId;

    @Schema(description = "Timestamp when the message was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp of the last update")
    private LocalDateTime updatedAt;
}