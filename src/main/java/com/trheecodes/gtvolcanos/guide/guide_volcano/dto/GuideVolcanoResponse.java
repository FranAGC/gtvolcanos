package com.trheecodes.gtvolcanos.guide.guide_volcano.dto;

/** Full record response (used for POST / PATCH). */
public record GuideVolcanoResponse(
        Integer id,
        Integer guideId,
        String guideName,
        Integer volcanoId,
        String volcanoName,
        Boolean isPrimary
) {}
