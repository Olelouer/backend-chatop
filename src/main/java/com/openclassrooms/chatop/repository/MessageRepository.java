package com.openclassrooms.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.chatop.model.Message;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Message entity operations.
 * Extends JpaRepository to provide basic CRUD functionality.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}