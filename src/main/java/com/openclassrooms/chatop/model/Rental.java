package com.openclassrooms.chatop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Entity representing a Rental in the system
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(columnDefinition = "TEXT")
    private String description;

    // ID of the owner/user who posted this rental
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    // Timestamp of when the rental was created
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Timestamp of the last update
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Automatically update the update time when rental is modified
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}