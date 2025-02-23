package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.dto.GlobalMessageResponse;
import com.openclassrooms.chatop.dto.RentalListResponse;
import com.openclassrooms.chatop.dto.RentalRequest;
import com.openclassrooms.chatop.dto.RentalResponse;
import com.openclassrooms.chatop.mapper.RentalMapper;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

/**
 * Service class for managing rental operations.
 */
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Creates a new rental listing.
     *
     * @param rentalRequest The request containing rental details.
     * @return The created Rental object.
     * @throws IOException If there is an error saving the rental image.
     */
    public GlobalMessageResponse createRental(RentalRequest rentalRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("User not authenticated");
            }

            User user = (User) authentication.getPrincipal();
            String pictureUrl = null;
            if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
                String uniqueFileName = saveImage(rentalRequest.getPicture());
                pictureUrl = "http://localhost:3001/uploads/" + uniqueFileName;
            }

            // Use mapper instead of direct builder
            Rental rental = rentalMapper.toEntity(rentalRequest);
            rental.setOwnerId(user.getId());
            rental.setPicture(pictureUrl);

            rentalRepository.save(rental);
            return new GlobalMessageResponse("Rental created !");
        } catch (IOException e) {
            throw new RuntimeException("Error creating rental: " + e.getMessage());
        }
    }

    /**
     * Retrieves all rental listings.
     *
     * @return A list of all available rentals.
     */
    public RentalListResponse getAllRentals() {
        return new RentalListResponse(rentalMapper.toResponseList(rentalRepository.findAll()));
    }

    public RentalResponse getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        return rentalMapper.toResponse(rental);
    }

    /**
     * Updates an existing rental listing.
     *
     * @param id The ID of the rental to update.
     * @param rentalRequest The updated rental details.
     * @param picture The updated rental image.
     * @return The updated rental object.
     * @throws IOException If there is an error saving the image.
     */
    public GlobalMessageResponse updateRental(Long id, RentalRequest rentalRequest, MultipartFile picture) {
        try {
            Rental rental = rentalRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));

            // Use mapper to update fields
            Rental updatedRental = rentalMapper.updateEntity(rental, rentalRequest);

            // Handle picture separately since it requires file operations
            if (picture != null && !picture.isEmpty()) {
                if (rental.getPicture() != null) {
                    deleteImage(rental.getPicture());
                }
                updatedRental.setPicture(saveImage(picture));
            }

            updatedRental.setUpdatedAt(LocalDateTime.now());
            rentalRepository.save(updatedRental);

            return new GlobalMessageResponse("Rental updated !");
        } catch (IOException e) {
            throw new RuntimeException("Error updating rental: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private void deleteImage(String fileName) throws IOException {
        if (fileName != null && !fileName.isEmpty()) {
            Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
}