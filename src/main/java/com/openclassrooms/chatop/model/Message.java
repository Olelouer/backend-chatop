package com.openclassrooms.chatop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a message in the system.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@Schema(description = "Entity representing a user message related to a rental")
public class Message {

    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the message", example = "1")
    private Long id;

    /**
     * Body of the message.
     */
    @NotBlank(message = "The message cannot be empty")
    @Column(nullable = false)
    @Schema(description = "Content of the message", example = "Hello, I am interested in your rental!")
    private String message;

    /**
     * The user who sent the message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User who sent the message")
    private User user;

    /**
     * The rental related to the message.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    @Schema(description = "Rental associated with the message")
    private Rental rental;

    /**
     * Timestamp of when the message was created.
     */
    @Column(name= "created_at", nullable = false)
    @Schema(description = "Timestamp when the message was created", example = "2024-02-01T12:00:00")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Timestamp of the last update.
     */
    @Column(name= "updated_at", nullable = false)
    @Schema(description = "Timestamp of the last update", example = "2024-02-02T15:30:00")
    private LocalDateTime updatedAt = LocalDateTime.now();
}