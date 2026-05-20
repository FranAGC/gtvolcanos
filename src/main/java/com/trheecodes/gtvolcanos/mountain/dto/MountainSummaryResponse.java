package com.trheecodes.gtvolcanos.mountain.dto;

public record MountainSummaryResponse(
    Integer id,
    String name,
    Integer countryId,
    String region,
    Boolean reto37,
    Boolean former37,
    String imageUrl
) {}
