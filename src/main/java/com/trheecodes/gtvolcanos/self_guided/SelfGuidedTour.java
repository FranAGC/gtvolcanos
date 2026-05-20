package com.trheecodes.gtvolcanos.self_guided;

import com.trheecodes.gtvolcanos.mountain.Mountain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "self_guided_tours")
public class SelfGuidedTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 20)
    private String difficulty;

    @Column(name = "distance_km", precision = 6, scale = 2)
    private BigDecimal distanceKm;

    @Column(name = "estimated_duration_hrs", precision = 4, scale = 1)
    private BigDecimal estimatedDurationHrs;

    @Column(name = "starting_point_name", length = 150)
    private String startingPointName;

    @Column(name = "starting_point_lat", precision = 9, scale = 6)
    private BigDecimal startingPointLat;

    @Column(name = "starting_point_lng", precision = 9, scale = 6)
    private BigDecimal startingPointLng;

    @Column(nullable = false, columnDefinition = "text")
    private String instructions;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Array(length = 30)
    @Column(name = "recommended_gear", columnDefinition = "text[]")
    private String[] recommendedGear;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}
