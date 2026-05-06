package com.trheecodes.gtvolcanos.volcano.dto;

import java.math.BigDecimal;

public record VolcanoRequest(
    String name,
    String country,
    String region,
    BigDecimal latitude,
    BigDecimal longitude,
    Integer elevationM,
    String type,
    String status,
    Short lastEruption,
    Short vei,
    Integer casualties,
    Boolean monitored,
    String description
) {}
