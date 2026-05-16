package com.trheecodes.gtvolcanos.self_guided.dto;

import java.math.BigDecimal;

public record SelfGuidedTourSummaryResponse(
        Integer id,
        String title,
        String difficulty,
        BigDecimal distanceKm
) {}
