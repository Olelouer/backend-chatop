package com.openclassrooms.chatop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    // Unique identifier for the message
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Body of the message
    @NotBlank(message = "The message cannot be empty")
    @Column(nullable = false)
    private String message;

    // Sender's id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Rental's id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    // Timestamp of when the message was created
    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Timestamp of the last update
    @Column(name= "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
