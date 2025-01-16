package com.openclassrooms.chatop.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a Rental in the system
 */
@Entity
@Data
public class Rental {
    // Unique identifier for the rental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the rental property
    private String name;

    // Surface area of the rental in square meters
    private Integer surface;

    // Price of the rental
    private Integer price;

    // URL or path to the rental's picture
    private String picture;

    // Detailed description of the rental
    private String description;

    // ID of the owner/user who posted this rental
    private Long ownerId;

    // Timestamp of when the rental was created
    private LocalDateTime createdAt;

    // Timestamp of the last update
    private LocalDateTime updatedAt;

    /**
     * Automatically set creation time when rental is first saved
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Automatically update the update time when rental is modified
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}