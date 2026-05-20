package com.trheecodes.gtvolcanos.mountain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mountains")
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "country_id", nullable = false)
    private Integer countryId;

    @Column(nullable = false, length = 100)
    private String region;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "is_volcano")
    private Boolean isVolcano = false;

    @Column(name = "elevation_m", nullable = false)
    private Integer elevationM;

    @Column(length = 50)
    private String type;

    @Column(length = 20)
    private String status;

    @Column(name = "last_eruption", length = 50)
    private String lastEruption;

    private Short vei;

    private Integer casualties = 0;

    private Boolean monitored = false;

    private Boolean reto37 = false;

    private Boolean former37 = false;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "mountain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.trheecodes.gtvolcanos.src_mountain.SrcMountain> sources = new ArrayList<>();
}
