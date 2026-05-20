package com.trheecodes.gtvolcanos.mountain.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MountainRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String name,

        @NotNull(message = "El id del país es obligatorio")
        Integer countryId,

        @NotNull(message = "La latitud es obligatoria")
        @DecimalMin(value = "-90.0", message = "La latitud mínima es -90")
        @DecimalMax(value = "90.0", message = "La latitud máxima es 90")
        BigDecimal latitude,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-180.0", message = "La longitud mínima es -180")
        @DecimalMax(value = "180.0", message = "La longitud máxima es 180")
        BigDecimal longitude,

        @NotNull(message = "La elevación es obligatoria")
        Integer elevationM,

        @Size(max = 100)
        String region,

        Boolean isVolcano,

        @Size(max = 50)
        String type,

        @Size(max = 20)
        String status,

        @Size(max = 50)
        String lastEruption,
        Short vei,
        Integer casualties,
        Boolean monitored,
        Boolean reto37,
        Boolean former37,
        String imageUrl,
        String description
) {}
