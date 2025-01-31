package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.dto.RentalRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Rental operations
 * Handles business logic between Controller and Repository
 */
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Create a new rental
     * @param rentalRequest The rental object to create
     * @return The created rental with generated ID
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
            // Save file
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = StringUtils.cleanPath(rentalRequest.getPicture().getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
            Files.copy(rentalRequest.getPicture().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrl = targetLocation.toString();
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
     * Retrieve all rentals from database
     * @return List of all rentals
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    /**
     * Find rental by its ID
     * @param id ID of the rental to find
     * @return The rental if found
     * @throws RuntimeException if rental not found
     */
    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    /**
     * Update an existing rental
     * @param id ID of the rental to update
     * @param rentalDetails New rental details
     * @return The updated rental
     * @throws RuntimeException if rental not found
     */
    public Rental updateRental(Long id, Rental rentalDetails, MultipartFile picture) throws IOException {
        Rental rental = getRentalById(id);

        rental.setName(rentalDetails.getName());
        rental.setSurface(rentalDetails.getSurface());
        rental.setPrice(rentalDetails.getPrice());
        rental.setDescription(rentalDetails.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        // Si une nouvelle image est fournie
        if (picture != null && !picture.isEmpty()) {
            // Supprimer l'ancienne image si elle existe
            if (rental.getPicture() != null) {
                Path oldPicture = Paths.get(rental.getPicture());
                Files.deleteIfExists(oldPicture);
            }

            // Sauvegarder la nouvelle image
            String fileName = StringUtils.cleanPath(picture.getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
            Files.copy(picture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            rental.setPicture(targetLocation.toString());
        }

        return rentalRepository.save(rental);
    }

}