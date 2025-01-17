package com.openclassrooms.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatop.model.Message;
import org.springframework.stereotype.Repository;

/**
 * Interface to handle database operations for Messages
 * Extends JpaRepository to get basic CRUD operations
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}