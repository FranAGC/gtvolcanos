package com.trheecodes.gtvolcanos.tourism_info;

import com.trheecodes.gtvolcanos.mountain.Mountain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "tourism_info")
public class TourismInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;

    @Column(name = "difficulty", length = 20)
    private String difficulty;

    @Column(name = "hiking_trail")
    private Boolean hikingTrail = false;

    @Column(name = "guided_tour_required")
    private Boolean guidedTourRequired = false;

    @Column(name = "entrance_fee_usd", precision = 6, scale = 2)
    private BigDecimal entranceFeeUsd;

    @Column(name = "best_season", length = 50)
    private String bestSeason;

    @Column(name = "nearest_city", length = 100)
    private String nearestCity;

    @Column(name = "distance_to_city_km", precision = 6, scale = 2)
    private BigDecimal distanceToCityKm;

    @Column(name = "visit_duration_hrs", precision = 4, scale = 1)
    private BigDecimal visitDurationHrs;

    @Column
    private Boolean parking = false;

    @Column
    private Boolean restrooms = false;

    @Column(name = "visitor_center")
    private Boolean visitorCenter = false;

    @Column(name = "camping_allowed")
    private Boolean campingAllowed = false;

    @Column(name = "food_nearby")
    private Boolean foodNearby = false;

    @Column(name = "current_alert_level", length = 20)
    private String currentAlertLevel;

    @Column(name = "emergency_contact", length = 100)
    private String emergencyContact;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
