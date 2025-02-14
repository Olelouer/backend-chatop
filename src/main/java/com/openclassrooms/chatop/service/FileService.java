package com.openclassrooms.chatop.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    /**
     * Retrieves a file as a Resource.
     *
     * @param filename Name of the file.
     * @return File as a Resource.
     * @throws IOException If file is not found or cannot be read.
     */
    public Resource loadFileAsResource(String filename) throws IOException {
        Path filePath = fileStorageLocation.resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("File not found: " + filename);
        }
        return resource;
    }

    /**
     * Determines the content type of a file.
     *
     * @param filePath Path of the file.
     * @return MIME type of the file.
     * @throws IOException If unable to determine type.
     */
    public String getFileContentType(Path filePath) throws IOException {
        String contentType = Files.probeContentType(filePath);
        return (contentType != null) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
