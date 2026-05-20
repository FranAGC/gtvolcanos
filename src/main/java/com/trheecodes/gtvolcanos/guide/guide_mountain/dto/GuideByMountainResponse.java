package com.trheecodes.gtvolcanos.guide.guide_mountain.dto;

public record GuideByMountainResponse(
        Integer guideId,
        String guideName,
        String guideWhatsapp,
        Boolean isPrimary
) {}
