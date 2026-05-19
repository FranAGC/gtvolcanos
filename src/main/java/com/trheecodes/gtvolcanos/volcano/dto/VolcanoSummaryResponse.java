package com.trheecodes.gtvolcanos.volcano.dto;

public record VolcanoSummaryResponse(
    Integer id,
    String name,
    String country,
    String region,
    Boolean reto37,
    Boolean isvolcano,
    Boolean former37,
    String imageUrl
) {}
