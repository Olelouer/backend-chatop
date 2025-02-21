package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.dto.RentalRequest;
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
import java.util.List;

/**
 * Service class for managing rental operations.
 */
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Creates a new rental listing.
     *
     * @param rentalRequest The request containing rental details.
     * @return The created Rental object.
     * @throws IOException If there is an error saving the rental image.
     */
    public Rental createRental(RentalRequest rentalRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();
        Long ownerId = user.getId();

        String pictureUrl = null;
        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            String uniqueFileName = saveImage(rentalRequest.getPicture());
            pictureUrl = "http://localhost:3001/uploads/" + uniqueFileName;
        }

        Rental rental = Rental.builder()
                .name(rentalRequest.getName())
                .surface(rentalRequest.getSurface())
                .price(rentalRequest.getPrice())
                .picture(pictureUrl)
                .description(rentalRequest.getDescription())
                .ownerId(ownerId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return rentalRepository.save(rental);
    }

    /**
     * Retrieves all rental listings.
     *
     * @return A list of all available rentals.
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id The ID of the rental.
     * @return The rental object if found.
     * @throws EntityNotFoundException if the rental is not found.
     */
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
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
    public Rental updateRental(Long id, RentalRequest rentalRequest, MultipartFile picture) throws IOException {
        Rental rental = getRentalById(id);

        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        if (picture != null && !picture.isEmpty()) {
            if (rental.getPicture() != null) {
                deleteImage(rental.getPicture());
            }
            rental.setPicture(saveImage(picture));
        }

        return rentalRepository.save(rental);
    }

    /**
     * Saves an uploaded image to the server.
     *
     * @param file The image file to save.
     * @return The file name of the saved image.
     * @throws IOException If there is an error saving the image.
     */
    private String saveImage(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    /**
     * Deletes an image file from the server.
     *
     * @param fileName The name of the file to delete.
     * @throws IOException If there is an error deleting the file.
     */
    private void deleteImage(String fileName) throws IOException {
        if (fileName != null && !fileName.isEmpty()) {
            Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
}