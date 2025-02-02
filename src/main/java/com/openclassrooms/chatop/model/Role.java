package com.openclassrooms.chatop.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing user roles in the system.
 */
@Schema(description = "Enumeration representing different user roles")
public enum Role {

    /**
     * Regular user with limited permissions.
     */
    @Schema(description = "Regular user with limited permissions")
    USER,

    /**
     * Administrator with elevated privileges.
     */
    @Schema(description = "Administrator with elevated privileges")
    ADMIN
}
