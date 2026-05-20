package com.trheecodes.gtvolcanos.guide.guide_mountain.dto;

import jakarta.validation.constraints.NotNull;

public record GuideMountainRequest(

        @NotNull(message = "El id del guía es obligatorio")
        Integer guideId,

        @NotNull(message = "El id de la montaña es obligatorio")
        Integer mountainId,

        Boolean isPrimary
) {}
