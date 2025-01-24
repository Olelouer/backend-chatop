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


@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public Message createMessage(MessageRequest messageRequest) {
        User user = userRepository.findById(messageRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + messageRequest.getUserId()));

        Rental rental = rentalRepository.findById(messageRequest.getRentalId())
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + messageRequest.getRentalId()));

        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .user(user)
                .rental(rental)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        System.out.println("Sauvegarde du message");
        return messageRepository.save(message);
    }
}