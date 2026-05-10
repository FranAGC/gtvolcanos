package com.trheecodes.gtvolcanos.self_guided.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record SelfGuidedTourRequest(

        @NotNull(message = "El id del volcán es obligatorio")
        Integer volcanoId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150)
        String title,

        @NotBlank(message = "La dificultad es obligatoria")
        @Pattern(regexp = "easy|moderate|hard|expert",
                message = "difficulty debe ser: easy, moderate, hard o expert")
        String difficulty,

        @DecimalMin(value = "0.0", message = "La distancia no puede ser negativa")
        BigDecimal distanceKm,

        @DecimalMin(value = "0.0", message = "La duración estimada no puede ser negativa")
        BigDecimal estimatedDurationHrs,

        @Size(max = 150)
        String startingPointName,

        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0")
        BigDecimal startingPointLat,

        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0")
        BigDecimal startingPointLng,

        @NotBlank(message = "Las instrucciones son obligatorias")
        String instructions,

        List<String> recommendedGear,

        Boolean active
) {}
