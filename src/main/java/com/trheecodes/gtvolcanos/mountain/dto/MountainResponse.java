package com.trheecodes.gtvolcanos.mountain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record MountainResponse(
    Integer id,
    String name,
    Integer countryId,
    String region,
    BigDecimal latitude,
    BigDecimal longitude,
    Boolean isVolcano,
    Integer elevationM,
    String type,
    String status,
    String lastEruption,
    Short vei,
    Integer casualties,
    Boolean monitored,
    Boolean reto37,
    Boolean former37,
    String imageUrl,
    String description,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
