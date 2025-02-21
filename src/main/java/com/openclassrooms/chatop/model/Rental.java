package com.openclassrooms.chatop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a Rental in the system.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
@Schema(description = "Entity representing a rental property")
public class Rental {

    /**
     * Unique identifier for the rental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the rental", example = "1")
    private Long id;

    /**
     * Name of the rental property.
     */
    @NotBlank(message = "The name cannot be empty")
    @Schema(description = "Name of the rental property", example = "Cozy Apartment")
    private String name;

    /**
     * Surface area of the rental in square meters.
     */
    @NotNull(message = "The surface cannot be empty")
    @Schema(description = "Surface area of the rental in square meters", example = "50")
    private Integer surface;

    /**
     * Price of the rental.
     */
    @NotNull(message = "The price cannot be empty")
    @Schema(description = "Price of the rental", example = "1200")
    private Integer price;

    /**
     * URL or path to the rental's picture.
     */
    @Schema(description = "URL or path to the rental's picture", example = "https://example.com/images/rental.jpg")
    private String picture;

    /**
     * Detailed description of the rental.
     */
    @NotBlank(message = "The description cannot be empty")
    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the rental", example = "A beautiful apartment in the city center with all amenities.")
    private String description;

    /**
     * ID of the owner/user who posted this rental.
     */
    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    @Schema(description = "ID of the owner who posted the rental", example = "10")
    private Long ownerId;

    /**
     * Timestamp of when the rental was created.
     */
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Timestamp when the rental was created", example = "2024/02/01 12:00:00")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Timestamp of the last update.
     */
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Timestamp of the last update", example = "2024/02/02 15:30:00")
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Automatically updates the timestamp when the rental is modified.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
