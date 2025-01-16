package com.openclassrooms.chatop.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User entity implementing Spring Security UserDetails
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /** User ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** User's name */
    private String name;

    /** User's email */
    private String email;

    /** Encrypted password */
    private String password;

    /** User role */
    @Enumerated(EnumType.STRING)
    private Role role;

    /** Creation timestamp */
    private String createdAt;

    /** Last update timestamp */
    private String updatedAt;

    /**
     * @return User authorities based on role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * @return Email as username
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * @return User's encrypted password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Checks if account is not expired
     * @return true as accounts never expire in this implementation
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if account is not locked
     * @return true as accounts are never locked in this implementation
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if credentials are not expired
     * @return true as credentials never expire in this implementation
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if user is enabled
     * @return true as all users are enabled in this implementation
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}