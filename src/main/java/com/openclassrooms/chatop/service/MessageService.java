package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.dto.GlobalMessageResponse;
import com.openclassrooms.chatop.mapper.MessageMapper;
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

/**
 * Service class for managing messages operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    /**
     * Creates a new message by linking a user and a rental.
     * Throws EntityNotFoundException if the user or rental is not found.
     * Any unexpected exceptions are wrapped in a RuntimeException.
     *
     * @param messageRequest The DTO containing message details.
     * @return The saved Message entity.
     * @throws EntityNotFoundException If the user or rental is not found.
     */
    public GlobalMessageResponse createMessage(MessageRequest messageRequest) {
        try {
            User user = userRepository.findById(messageRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + messageRequest.getUserId()));

            Rental rental = rentalRepository.findById(messageRequest.getRentalId())
                    .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + messageRequest.getRentalId()));

            Message message = messageMapper.toEntity(messageRequest);
            message.setUser(user);
            message.setRental(rental);
            messageRepository.save(message);

            return new GlobalMessageResponse("Message send with success");
        } catch (EntityNotFoundException ex) {
            log.error("Entity not found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while creating message: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error while creating message", ex);
        }
    }
}