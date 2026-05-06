package com.trheecodes.gtvolcanos.volcano;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Integer getElevationM() { return elevationM; }
    public void setElevationM(Integer elevationM) { this.elevationM = elevationM; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Short getLastEruption() { return lastEruption; }
    public void setLastEruption(Short lastEruption) { this.lastEruption = lastEruption; }

    public Short getVei() { return vei; }
    public void setVei(Short vei) { this.vei = vei; }

    public Integer getCasualties() { return casualties; }
    public void setCasualties(Integer casualties) { this.casualties = casualties; }

    public Boolean getMonitored() { return monitored; }
    public void setMonitored(Boolean monitored) { this.monitored = monitored; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
