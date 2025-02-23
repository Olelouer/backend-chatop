package com.openclassrooms.chatop.mapper;

import com.openclassrooms.chatop.dto.RentalResponse;
import com.openclassrooms.chatop.dto.RentalRequest;
import com.openclassrooms.chatop.model.Rental;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting Rental entities to DTOs and vice versa.
 * Provides methods for single entity conversion, list conversion, and entity updates.
 */
@Component
public class RentalMapper {

    /**
     * Converts a Rental entity to a RentalResponse DTO.
     * Maps all rental properties including timestamps and owner information.
     *
     * @param rental The Rental entity to convert
     * @return A RentalResponse containing the rental data
     */
    public RentalResponse toResponse(Rental rental) {
        return RentalResponse.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .ownerId(rental.getOwnerId())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
    }

    /**
     * Converts a RentalRequest DTO to a new Rental entity.
     * Sets the creation and update timestamps to current time.
     * Note: Owner association must be set separately.
     *
     * @param request The RentalRequest DTO to convert
     * @return A new Rental entity with basic fields populated
     */
    public Rental toEntity(RentalRequest request) {
        return Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Converts a list of Rental entities to a list of RentalResponse DTOs.
     * Uses stream operations for efficient bulk conversion.
     *
     * @param rentals List of Rental entities to convert
     * @return List of corresponding RentalResponse DTOs
     */
    public List<RentalResponse> toResponseList(List<Rental> rentals) {
        return rentals.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Rental entity with data from a RentalRequest DTO.
     * Only updates mutable fields, preserving entity identity and timestamps.
     *
     * @param rental The existing Rental entity to update
     * @param request The RentalRequest containing new values
     * @return The updated Rental entity
     */
    public Rental updateEntity(Rental rental, RentalRequest request) {
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        return rental;
    }
}