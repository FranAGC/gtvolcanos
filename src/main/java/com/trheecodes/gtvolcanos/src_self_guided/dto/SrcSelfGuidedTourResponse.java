package com.trheecodes.gtvolcanos.src_self_guided.dto;

import java.time.OffsetDateTime;

public record SrcSelfGuidedTourResponse(
        Integer id,
        Integer selfGuidedTourId,
        String selfGuidedTourTitle,
        String type,
        String description,
        String srcUrl,
        String appPage,
        OffsetDateTime createdAt
) {}
