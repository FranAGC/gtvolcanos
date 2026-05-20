package com.trheecodes.gtvolcanos.guide.guide_mountain.dto;

public record MountainByGuideResponse(
        Integer mountainId,
        String mountainName,
        Boolean isPrimary
) {}
