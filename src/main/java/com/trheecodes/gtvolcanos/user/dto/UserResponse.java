package com.trheecodes.gtvolcanos.user.dto;

import com.trheecodes.gtvolcanos.user.UserRole;

import java.time.OffsetDateTime;

public record UserResponse(
        Integer id,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        Boolean active,
        OffsetDateTime createdAt
) {}
