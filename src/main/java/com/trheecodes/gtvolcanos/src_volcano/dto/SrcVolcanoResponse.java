package com.trheecodes.gtvolcanos.src_volcano.dto;

import java.time.OffsetDateTime;

public record SrcVolcanoResponse(
        Integer id,
        String type,
        String description,
        String srcUrl,
        String appPage,
        OffsetDateTime createdAt,
        Integer selfGuidedTourId
) {}
