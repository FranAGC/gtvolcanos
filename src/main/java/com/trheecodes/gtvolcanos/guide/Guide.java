package com.trheecodes.gtvolcanos.guide;

import com.trheecodes.gtvolcanos.guide.guide_mountain.GuideMountain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ── Personal info ──────────────────────────────────────────────────────

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 150, unique = true)
    private String email;

    @Column(length = 100)
    private String nationality;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Array(length = 20)
    @Column(name = "spoken_languages", columnDefinition = "text[]")
    private String[] spokenLanguages;

    @Column(name = "profile_photo_url", columnDefinition = "text")
    private String profilePhotoUrl;

    @Column(columnDefinition = "text")
    private String bio;

    // ── Social media ───────────────────────────────────────────────────────

    @Column(length = 150)
    private String facebook;

    @Column(length = 150)
    private String instagram;

    @Column(length = 20)
    private String whatsapp;

    // ── Professional info ──────────────────────────────────────────────────

    @Column(name = "license_number", length = 50, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private Boolean certified = false;

    @Column(name = "experience_years")
    private Short experienceYears;

    @Column(name = "price_per_day_usd", precision = 8, scale = 2)
    private BigDecimal pricePerDayUsd;

    @Column(name = "max_group_size")
    private Short maxGroupSize;

    // ── Status ─────────────────────────────────────────────────────────────

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // ── Relationships ──────────────────────────────────────────────────────

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuideMountain> guideMountains = new ArrayList<>();
}
