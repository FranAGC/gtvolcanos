package com.trheecodes.gtvolcanos.src_volcano.dto;

import java.time.OffsetDateTime;

public record SrcVolcanoResponse(
        Integer id,
        Integer volcanoId,
        String volcanoName,
        String type,
        String description,
        String srcUrl,
        String appPage,
        OffsetDateTime createdAt
) {}
