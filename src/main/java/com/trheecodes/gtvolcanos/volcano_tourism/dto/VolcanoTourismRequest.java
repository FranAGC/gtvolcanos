package com.trheecodes.gtvolcanos.volcano_tourism.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record VolcanoTourismRequest(

        @NotNull(message = "El id del volcán es obligatorio")
        Integer volcanoId,

        @Pattern(regexp = "easy|moderate|hard|expert",
                message = "accessDifficulty debe ser: easy, moderate, hard o expert")
        String accessDifficulty,

        Boolean hikingTrail,
        Boolean guidedTourRequired,

        @DecimalMin(value = "0.0", message = "La tarifa de entrada no puede ser negativa")
        BigDecimal entranceFeeUsd,

        @Size(max = 50)
        String bestSeason,

        @Size(max = 100)
        String nearestCity,

        @DecimalMin(value = "0.0", message = "La distancia no puede ser negativa")
        BigDecimal distanceToCityKm,

        @DecimalMin(value = "0.0", message = "La duración no puede ser negativa")
        BigDecimal visitDurationHrs,

        Boolean parking,
        Boolean restrooms,
        Boolean visitorCenter,
        Boolean campingAllowed,
        Boolean foodNearby,

        @Pattern(regexp = "green|yellow|orange|red",
                message = "currentAlertLevel debe ser: green, yellow, orange o red")
        String currentAlertLevel,

        @Size(max = 100)
        String emergencyContact,

        String details
) {}
