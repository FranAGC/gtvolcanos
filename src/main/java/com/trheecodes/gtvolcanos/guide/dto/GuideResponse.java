package com.trheecodes.gtvolcanos.guide.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record GuideResponse(
        Integer id,
        // Personal info
        String firstName,
        String lastName,
        String phone,
        String email,
        String nationality,
        List<String> spokenLanguages,
        String profilePhotoUrl,
        String bio,
        // Social media
        String facebook,
        String instagram,
        String whatsapp,
        // Professional info
        String licenseNumber,
        Boolean certified,
        Short experienceYears,
        BigDecimal pricePerDayUsd,
        Short maxGroupSize,
        // Status
        Boolean active,
        // Volcanoes
        List<VolcanoRef> volcanoes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    /** Lightweight volcano reference embedded in the guide response. */
    public record VolcanoRef(Integer id, String name, String country) {}
}
