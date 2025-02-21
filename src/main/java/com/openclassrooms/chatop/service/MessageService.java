package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new message by linking a user and a rental.
     * Throws EntityNotFoundException if the user or rental is not found.
     * Any unexpected exceptions are wrapped in a RuntimeException.
     *
     * @param messageRequest The DTO containing message details.
     * @return The saved Message entity.
     * @throws EntityNotFoundException If the user or rental is not found.
     */
    public Message createMessage(MessageRequest messageRequest) {
        try {
            // Retrieve the user by ID; throw exception if not found
            User user = userRepository.findById(messageRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + messageRequest.getUserId()));

            // Retrieve the rental by ID; throw exception if not found
            Rental rental = rentalRepository.findById(messageRequest.getRentalId())
                    .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + messageRequest.getRentalId()));

            // Build the Message entity with the provided details
            Message message = Message.builder()
                    .message(messageRequest.getMessage())
                    .user(user)
                    .rental(rental)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // Save and return the message entity
            return messageRepository.save(message);
        } catch (EntityNotFoundException ex) {
            // Log specific error for missing entity and rethrow the exception
            log.error("Entity not found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            // Log any unexpected error and wrap it in a RuntimeException
            log.error("Unexpected error while creating message: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error while creating message", ex);
        }
    }
}
