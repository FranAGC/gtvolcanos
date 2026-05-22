package com.trheecodes.gtvolcanos.src_mountain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SrcMountainRequest(

        @NotNull(message = "El id de la montaña es obligatorio")
        Integer mountainId,

        @NotBlank(message = "El tipo es obligatorio")
        @Pattern(regexp = "video|ruta|guia|post|otro|imagen",
                message = "type debe ser: video, ruta, guia, post, otro o imagen")
        String type,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 200)
        String description,

        @NotBlank(message = "La URL es obligatoria")
        String srcUrl,

        @Size(max = 100)
        String appPage
        ,
        Integer selfGuidedTourId,

        @Size(max = 500)
        String additionalInfo
) {}
