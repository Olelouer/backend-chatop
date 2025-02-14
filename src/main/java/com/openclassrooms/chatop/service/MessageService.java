package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Service class for handling message-related operations.
 */
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new message and associates it with a user and rental.
     *
     * @param messageRequest The request object containing message details.
     * @return The saved message entity.
     * @throws EntityNotFoundException If the user or rental is not found.
     */
    public Message createMessage(MessageRequest messageRequest) {
        // Fetch the user by ID, throw an exception if not found
        User user = userRepository.findById(messageRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + messageRequest.getUserId()));

        // Fetch the rental by ID, throw an exception if not found
        Rental rental = rentalRepository.findById(messageRequest.getRentalId())
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + messageRequest.getRentalId()));

        // Create a new message entity and populate its fields
        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .user(user)
                .rental(rental)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Save and return the message
        return messageRepository.save(message);
    }
}