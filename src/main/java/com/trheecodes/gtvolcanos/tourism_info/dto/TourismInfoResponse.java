package com.trheecodes.gtvolcanos.tourism_info.dto;

import java.math.BigDecimal;

public record TourismInfoResponse(
        Integer id,
        Integer mountainId,
        String mountainName,
        String difficulty,
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
        String details
) {}
