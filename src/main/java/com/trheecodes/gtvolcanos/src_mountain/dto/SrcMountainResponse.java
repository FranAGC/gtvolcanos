package com.trheecodes.gtvolcanos.src_mountain.dto;

import java.time.OffsetDateTime;

public record SrcMountainResponse(
        Integer id,
        String type,
        String description,
        String srcUrl,
        String appPage,
        OffsetDateTime createdAt,
        Integer selfGuidedTourId
) {}
