package com.openclassrooms.chatop.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.model.Rental;

import java.util.List;

/**
 * Service layer for Rental operations
 * Handles business logic between Controller and Repository
 */
@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    /**
     * Create a new rental
     * @param rental The rental object to create
     * @return The created rental with generated ID
     */
    public Rental createRental(Rental rental) {
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
    public Rental updateRental(Long id, Rental rentalDetails) {
        Rental rental = getRentalById(id);

        // Update rental properties
        rental.setName(rentalDetails.getName());
        rental.setSurface(rentalDetails.getSurface());
        rental.setPrice(rentalDetails.getPrice());
        rental.setDescription(rentalDetails.getDescription());

        return rentalRepository.save(rental);
    }
}