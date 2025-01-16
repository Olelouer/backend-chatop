package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST Controller handling rental operations (CRUD)
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    // RentalService handles business logic
    private final RentalService rentalService;

    /**
     * Create a new rental
     * @return Message confirming creation
     */
    @Operation(summary = "Create a new rental")
    @ApiResponse(responseCode = "200", description = "Rental created successfully")
    @PostMapping
    public ResponseEntity<Map<String, String>> createRental(@RequestBody Rental rental) {
        rentalService.createRental(rental);
        return ResponseEntity.ok(Collections.singletonMap("message", "Rental created !"));
    }

    /**
     * Get all available rentals
     * @return List of all rentals wrapped in a map
     */
    @Operation(summary = "Get all rentals")
    @ApiResponse(responseCode = "200", description = "List of all rentals")
    @GetMapping
    public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(Collections.singletonMap("rentals", rentals));
    }

    /**
     * Get a specific rental by its ID
     * @param id ID of the rental to find
     * @return The rental if found
     */
    @Operation(summary = "Get a rental by ID")
    @ApiResponse(responseCode = "200", description = "Rental details")
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    /**
     * Update an existing rental
     * @param id ID of the rental to update
     * @param rental New rental details
     * @return Message confirming update
     */
    @Operation(summary = "Update a rental")
    @ApiResponse(responseCode = "200", description = "Rental updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(
            @PathVariable Long id,
            @RequestBody Rental rental) {
        rentalService.updateRental(id, rental);
        return ResponseEntity.ok(Collections.singletonMap("message", "Rental updated !"));
    }
}