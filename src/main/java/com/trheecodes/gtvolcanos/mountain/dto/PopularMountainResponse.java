package com.trheecodes.gtvolcanos.mountain.dto;

import java.math.BigDecimal;

public record PopularMountainResponse(
    Integer id,
    String name,
    String region,
    BigDecimal latitude,
    BigDecimal longitude,
    String imageUrl,
    String currentAlertLevel
) {}
