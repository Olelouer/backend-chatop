package com.openclassrooms.chatop.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a Rental in the system
 */
@Entity
@Data
@Table(name = "rentals")
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
    @JoinColumn(name = "owner_id", nullable = false)
    private Long ownerId;

    // Timestamp of when the rental was created
    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Timestamp of the last update
    @Column(name= "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;

    /**
     * Automatically update the update time when rental is modified
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}