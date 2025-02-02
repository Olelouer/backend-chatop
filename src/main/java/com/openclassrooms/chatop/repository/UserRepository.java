package com.openclassrooms.chatop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatop.model.User;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * @param email The email to search for.
     * @return Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);
}
