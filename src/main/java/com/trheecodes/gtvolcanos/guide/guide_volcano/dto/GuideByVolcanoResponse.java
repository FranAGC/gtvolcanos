package com.trheecodes.gtvolcanos.guide.guide_volcano.dto;

/** GET /guide-volcano/volcanoes/{volcanoId} — guides assigned to a volcano. */
public record GuideByVolcanoResponse(
        Integer guideId,
        String guideName,
        String guideWhatsapp,
        Boolean isPrimary
) {}
