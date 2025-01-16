package com.openclassrooms.chatop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatop.model.User;

/**
 * Repository interface for User entity operations
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds user by email address
     * @param email The email to search for
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);
}