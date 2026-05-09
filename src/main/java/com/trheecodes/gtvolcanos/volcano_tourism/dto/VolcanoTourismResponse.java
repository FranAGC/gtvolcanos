package com.trheecodes.gtvolcanos.volcano_tourism.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record VolcanoTourismResponse(
        Integer id,
        Integer volcanoId,
        String volcanoName,
        String accessDifficulty,
        Boolean hikingTrail,
        Boolean guidedTourRequired,
        BigDecimal entranceFeeUsd,
        String bestSeason,
        String nearestCity,
        BigDecimal distanceToCityKm,
        BigDecimal visitDurationHrs,
        Boolean parking,
        Boolean restrooms,
        Boolean visitorCenter,
        Boolean campingAllowed,
        Boolean foodNearby,
        String currentAlertLevel,
        String emergencyContact,
        String details,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
