package com.trheecodes.gtvolcanos.guide.guide_mountain.dto;

public record GuideMountainResponse(
        Integer id,
        Integer guideId,
        String guideName,
        Integer mountainId,
        String mountainName,
        Boolean isPrimary
) {}
