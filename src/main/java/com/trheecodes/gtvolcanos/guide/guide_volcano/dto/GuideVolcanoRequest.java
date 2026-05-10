package com.trheecodes.gtvolcanos.guide.guide_volcano.dto;

import jakarta.validation.constraints.NotNull;

public record GuideVolcanoRequest(

        @NotNull(message = "El id del guía es obligatorio")
        Integer guideId,

        @NotNull(message = "El id del volcán es obligatorio")
        Integer volcanoId,

        Boolean isPrimary
) {}
