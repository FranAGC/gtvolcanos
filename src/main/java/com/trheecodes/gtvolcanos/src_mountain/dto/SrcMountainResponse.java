package com.trheecodes.gtvolcanos.src_mountain.dto;

public record SrcMountainResponse(
        Integer id,
        String type,
        String description,
        String srcUrl,
        String appPage,
        Integer selfGuidedTourId
) {}
