package com.openclassrooms.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatop.model.Rental;
import org.springframework.stereotype.Repository;

/**
 * Interface to handle database operations for Rentals
 * Extends JpaRepository to get basic CRUD operations
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}