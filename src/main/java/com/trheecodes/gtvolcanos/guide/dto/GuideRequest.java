package com.trheecodes.gtvolcanos.guide.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record GuideRequest(

        // ── Personal info ──────────────────────────────────────────────────

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 100)
        String lastName,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 20)
        String phone,

        @Email(message = "Email inválido")
        @Size(max = 150)
        String email,

        @Size(max = 100)
        String nationality,

        List<String> spokenLanguages,

        String profilePhotoUrl,

        String bio,

        // ── Social media ───────────────────────────────────────────────────

        @Size(max = 150)
        String facebook,

        @Size(max = 150)
        String instagram,

        @Size(max = 20)
        String whatsapp,

        // ── Professional info ──────────────────────────────────────────────

        @Size(max = 50)
        String licenseNumber,

        Boolean certified,

        @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
        Short experienceYears,

        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal pricePerDayUsd,

        @Min(value = 1, message = "El tamaño máximo de grupo debe ser al menos 1")
        Short maxGroupSize,

        // ── Status ─────────────────────────────────────────────────────────

        Boolean active,

        // ── Associated volcanoes ───────────────────────────────────────────

        List<Integer> volcanoIds
) {}
