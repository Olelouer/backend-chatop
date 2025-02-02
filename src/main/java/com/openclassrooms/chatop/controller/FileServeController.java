package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

/**
 * REST Controller for serving uploaded files.
 */
@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class FileServeController {

    private final FileService fileService;

    /**
     * Serves a requested file if it exists.
     *
     * @param filename The name of the requested file.
     * @return The requested file as a resource.
     * @throws IOException If there is an issue accessing the file.
     */
    @Operation(summary = "Serve an uploaded file", description = "Retrieves and serves a file stored in the uploads directory")
    @ApiResponse(responseCode = "200", description = "File retrieved successfully")
    @ApiResponse(responseCode = "404", description = "File not found")
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(
            @PathVariable @Parameter(description = "Name of the requested file") String filename) throws IOException {

        Resource resource = fileService.loadFileAsResource(filename);
        Path filePath = resource.getFile().toPath();
        String contentType = fileService.getFileContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}