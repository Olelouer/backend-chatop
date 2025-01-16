package com.openclassrooms.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatop.model.Rental;

/**
 * Interface to handle database operations for Rentals
 * Extends JpaRepository to get basic CRUD operations
 */
public interface RentalRepository extends JpaRepository<Rental, Long> {
}