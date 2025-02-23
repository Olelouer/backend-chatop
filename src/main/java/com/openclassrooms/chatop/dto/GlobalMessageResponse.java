package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for general message requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for API response messages")
public class GlobalMessageResponse {
    @Schema(description = "Response message", example = "Rental created successfully")
    private String message;
}
