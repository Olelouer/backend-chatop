package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.dto.GlobalMessageResponse;
import com.openclassrooms.chatop.dto.RentalListResponse;
import com.openclassrooms.chatop.dto.RentalRequest;
import com.openclassrooms.chatop.dto.RentalResponse;
import com.openclassrooms.chatop.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller handling rental operations (CRUD).
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "Endpoints for managing rental properties")
public class RentalController {

    private final RentalService rentalService;

    /**
     * Create a new rental.
     *
     * @param name Name of the rental.
     * @param surface Surface area of the rental.
     * @param price Price of the rental.
     * @param description Description of the rental.
     * @param picture Optional picture of the rental.
     * @return Message confirming creation.
     */
    @Operation(summary = "Create a new rental", description = "Adds a new rental property to the system")
    @ApiResponse(responseCode = "200", description = "Rental created !")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters !", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized !", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<GlobalMessageResponse> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") Integer price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {

        RentalRequest rentalRequest = RentalRequest.builder()
                .name(name)
                .surface(surface)
                .price(price)
                .description(description)
                .picture(picture)
                .build();

        return ResponseEntity.ok(rentalService.createRental(rentalRequest));
    }

    /**
     * Get all available rentals.
     *
     * @return List of all rentals wrapped in a map.
     */
    @Operation(summary = "Get all rentals", description = "Retrieves a list of all available rental properties")
    @ApiResponse(responseCode = "200", description = "List of all rentals")
    @ApiResponse(responseCode = "401", description = "Unauthorized !", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping
    public ResponseEntity<RentalListResponse> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    /**
     * Get a specific rental by its ID.
     *
     * @param id ID of the rental to find.
     * @return The rental if found.
     */
    @Operation(summary = "Get a rental by ID", description = "Retrieves details of a specific rental property by ID")
    @ApiResponse(responseCode = "200", description = "Rental details retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized !", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Rental not found !", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    /**
     * Update an existing rental.
     *
     * @param id ID of the rental to update.
     * @param name Updated name of the rental.
     * @param surface Updated surface area of the rental.
     * @param price Updated price of the rental.
     * @param description Updated description of the rental.
     * @param picture Optional updated picture of the rental.
     * @return Message confirming update.
     */
    @Operation(summary = "Update a rental", description = "Updates details of an existing rental property")
    @ApiResponse(responseCode = "200", description = "Rental updated !")
    @ApiResponse(responseCode = "404", description = "Rental not found !", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "401", description = "Unauthorized !", content = @Content(schema = @Schema(hidden = true)))
    @PutMapping("/{id}")
    public ResponseEntity<GlobalMessageResponse> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") Integer price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {

        RentalRequest rentalRequest = RentalRequest.builder()
                .name(name)
                .surface(surface)
                .price(price)
                .description(description)
                .build();

        return ResponseEntity.ok(rentalService.updateRental(id, rentalRequest, picture));
    }
}