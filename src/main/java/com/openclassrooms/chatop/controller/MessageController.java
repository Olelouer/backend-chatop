package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.MessageRequest;
import com.openclassrooms.chatop.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * REST Controller for handling message operations.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Endpoints for sending messages between users")
public class MessageController {

    /**
     * Service handling message-related business logic.
     */
    private final MessageService messageService;

    /**
     * Endpoint to create a new message.
     * 
     * @param messageRequest The request payload containing message details.
     * @return Response entity containing a confirmation message.
     */
    @Operation(summary = "Create a new message", description = "Allows users to send a message related to a rental")
    @ApiResponse(responseCode = "200", description = "Message created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageRequest messageRequest) {
        messageService.createMessage(messageRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Message sent with success"));
    }
}
