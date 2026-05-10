package com.trheecodes.gtvolcanos.self_guided.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record SelfGuidedTourResponse(
        Integer id,
        Integer volcanoId,
        String volcanoName,
        String title,
        String difficulty,
        BigDecimal distanceKm,
        BigDecimal estimatedDurationHrs,
        String startingPointName,
        BigDecimal startingPointLat,
        BigDecimal startingPointLng,
        String instructions,
        List<String> recommendedGear,
        Boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
