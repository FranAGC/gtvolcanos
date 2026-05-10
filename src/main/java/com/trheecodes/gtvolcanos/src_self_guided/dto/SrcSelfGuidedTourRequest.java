package com.trheecodes.gtvolcanos.src_self_guided.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SrcSelfGuidedTourRequest(

        @NotNull(message = "El id del tour auto-guiado es obligatorio")
        Integer selfGuidedTourId,

        @NotBlank(message = "El tipo es obligatorio")
        @Pattern(regexp = "video|route|guide|other",
                message = "type debe ser: video, route, guide u other")
        String type,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 200)
        String description,

        @NotBlank(message = "La URL es obligatoria")
        String srcUrl,

        @Size(max = 100)
        String appPage
) {}
