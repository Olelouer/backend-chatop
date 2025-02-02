package com.openclassrooms.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object (DTO) for rental property creation or update requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for rental property creation or update requests")
public class RentalRequest {

    /**
     * Name of the rental property.
     */
    @NotBlank(message = "Rental name is required")
    @Schema(description = "Name of the rental property", example = "Cozy Apartment")
    private String name;

    /**
     * Surface area of the rental in square meters.
     */
    @NotNull(message = "Surface area is required")
    @Schema(description = "Surface area of the rental in square meters", example = "45")
    private Integer surface;

    /**
     * Price of the rental.
     */
    @NotNull(message = "Price is required")
    @Schema(description = "Price of the rental", example = "1200")
    private Integer price;

    /**
     * Image file for the rental property.
     */
    @Schema(description = "Image file for the rental property")
    private MultipartFile picture;

    /**
     * Detailed description of the rental.
     */
    @NotBlank(message = "Description is required")
    @Schema(description = "Detailed description of the rental", example = "A beautiful apartment in the city center with all amenities.")
    private String description;
}