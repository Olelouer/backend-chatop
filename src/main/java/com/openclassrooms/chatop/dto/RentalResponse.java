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
@Schema(description = "DTO for rental property responses")
public class RentalResponse {
    @Schema(description = "Unique identifier of the rental", example = "1")
    private Long id;

    @Schema(description = "Name of the rental property", example = "Cozy Apartment")
    private String name;

    @Schema(description = "Surface area of the rental in square meters", example = "45")
    private Integer surface;

    @Schema(description = "Price of the rental", example = "1200")
    private Integer price;

    @Schema(description = "URL of the rental picture", example = "http://localhost:3001/uploads/image.jpg")
    private String picture;

    @Schema(description = "Detailed description of the rental")
    private String description;

    @Schema(description = "ID of the owner who posted the rental", example = "10")
    private Long ownerId;

    @Schema(description = "Timestamp when the rental was created", example = "2024/02/01 12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp of the last update", example = "2024/02/02 15:30:00")
    private LocalDateTime updatedAt;
}
