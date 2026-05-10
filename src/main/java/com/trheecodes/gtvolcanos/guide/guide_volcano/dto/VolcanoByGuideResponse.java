package com.trheecodes.gtvolcanos.guide.guide_volcano.dto;

/** GET /guide-volcano/guides/{guideId} — volcanoes assigned to a guide. */
public record VolcanoByGuideResponse(
        Integer volcanoId,
        String volcanoName,
        Boolean isPrimary
) {}
