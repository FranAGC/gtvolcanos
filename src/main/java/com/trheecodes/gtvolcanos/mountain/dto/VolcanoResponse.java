package com.trheecodes.gtvolcanos.mountain.dto;

import java.math.BigDecimal;

public record VolcanoResponse(
    Integer id,
    String name,
    String type,
    Integer elevationM,
    String region,
    BigDecimal latitude,
    BigDecimal longitude,
    String lastEruption
) {}
