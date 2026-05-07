package com.trheecodes.gtvolcanos.volcano;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "volcanoes")
public class Volcano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(length = 100)
    private String region;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "elevation_m", nullable = false)
    private Integer elevationM;

    @Column(length = 50)
    private String type;

    @Column(length = 20)
    private String status;

    @Column(name = "last_eruption")
    private Short lastEruption;

    private Short vei;

    @Column(columnDefinition = "integer default 0")
    private Integer casualties;

    @Column(columnDefinition = "boolean default false")
    private Boolean monitored;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()")
    private OffsetDateTime updatedAt;
}
