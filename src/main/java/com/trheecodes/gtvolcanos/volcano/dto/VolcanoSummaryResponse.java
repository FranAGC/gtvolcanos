package com.trheecodes.gtvolcanos.volcano.dto;

public record VolcanoSummaryResponse(
    Integer id,
    String name,
    String country,
    String region,
    Integer popularity,
    String imageUrl
) {}
