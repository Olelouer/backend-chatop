package com.openclassrooms.chatop.model;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a User in the system, implementing Spring Security's UserDetails interface.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Schema(description = "Entity representing a registered user in the system")
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    /**
     * User's name.
     */
    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    /**
     * User's email (must be unique).
     */
    @Column(unique = true)
    @Schema(description = "User's unique email address", example = "john.doe@example.com")
    private String email;

    /**
     * Encrypted password for authentication.
     */

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Encrypted user password", example = "$2a$10$7QX... (hashed password)")
    private String password;

    /**
     * Role assigned to the user.
     */
    @Enumerated(EnumType.STRING)
    @Schema(description = "User's role in the system", example = "USER")
    private Role role;

    /**
     * Timestamp of when the user was created.
     */
    @Column(name= "created_at", nullable = false)
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Timestamp when the rental was created", example = "2024/02/01 12:00:00")
    private String createdAt;

    /**
     * Timestamp of the last update to the user record.
     */
    @Column(name= "updated_at", nullable = false)
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @Schema(description = "Timestamp when the rental was created", example = "2024/02/01 12:00:00")
    private String updatedAt;

    /**
     * Returns the authorities granted to the user based on their role.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Returns the email as the username for authentication purposes.
     *
     * @return The user's email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Returns the encrypted password of the user.
     *
     * @return The encrypted password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true, as accounts never expire in this implementation.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true, as accounts are never locked in this implementation.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return true, as credentials never expire in this implementation.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true, as all users are enabled in this implementation.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
