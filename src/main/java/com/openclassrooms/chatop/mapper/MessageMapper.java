package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.dto.MessageResponse;
import com.openclassrooms.chatop.model.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper class responsible for converting Message entities to DTOs and vice versa.
 * Handles the mapping between Message domain objects and their corresponding request/response DTOs.
 */
@Component
public class MessageMapper {

    /**
     * Converts a Message entity to a MessageResponse DTO.
     * Maps all relevant fields including associated user and rental IDs.
     *
     * @param message The Message entity to convert
     * @return A MessageResponse containing the message data
     */
    public MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .message(message.getMessage())
                .userId(message.getUser().getId())
                .rentalId(message.getRental().getId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }

    /**
     * Converts a MessageRequest DTO to a Message entity.
     * Sets the creation and update timestamps to current time.
     * Note: User and Rental associations must be set separately.
     *
     * @param request The MessageRequest DTO to convert
     * @return A new Message entity with basic fields populated
     */
    public Message toEntity(MessageRequest request) {
        return Message.builder()
                .message(request.getMessage())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}