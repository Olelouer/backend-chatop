package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.dto.RentalRequest;
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
        // Vérification de l'authentification
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();
        Long ownerId = user.getId();

        // Gestion de l'image
        String pictureUrl = null;
        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            // Save file
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = StringUtils.cleanPath(rentalRequest.getPicture().getOriginalFilename());
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
            Files.copy(rentalRequest.getPicture().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrl = "http://localhost:3001/uploads/" + uniqueFileName;
        }

        // Création du rental
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
     * @param picture New picture file (optional)
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

        // Gestion de la nouvelle image si fournie
        if (picture != null && !picture.isEmpty()) {
            // Suppression de l'ancienne image
            if (rental.getPicture() != null) {
                deleteImage(rental.getPicture());
            }
            // Sauvegarde de la nouvelle image
            rental.setPicture(saveImage(picture));
        }

        return rentalRepository.save(rental);
    }

    /**
     * Save an image file and return its URL
     * @param file The image file to save
     * @return The URL of the saved image
     */
    private String saveImage(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;  // On retourne uniquement le nom du fichier
    }

    /**
     * Delete an image file
     * @param fileName The name of the file to delete
     */
    private void deleteImage(String fileName) throws IOException {
        if (fileName != null && !fileName.isEmpty()) {
            Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
}