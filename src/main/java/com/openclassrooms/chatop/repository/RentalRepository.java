package com.openclassrooms.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.chatop.model.Rental;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Rental entity operations.
 * Extends JpaRepository to provide basic CRUD functionality.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}