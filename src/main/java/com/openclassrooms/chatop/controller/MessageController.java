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

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Endpoints for sending messages between users")
public class MessageController {

    private final MessageService messageService;

    /**
     * Endpoint to create a new message.
     * Exceptions thrown in the service layer are handled globally by the GlobalExceptionHandler.
     *
     * @param messageRequest The DTO containing message details.
     * @return A response confirming that the message was sent successfully.
     */
    @Operation(summary = "Create a new message", description = "Allows users to send a message related to a rental")
    @ApiResponse(responseCode = "200", description = "Message created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    @PostMapping
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageRequest messageRequest) {
        messageService.createMessage(messageRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Message sent successfully"));
    }
}
