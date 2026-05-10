package com.trheecodes.gtvolcanos.src_self_guided;

import com.trheecodes.gtvolcanos.self_guided.SelfGuidedTour;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "src_self_guided_tours")
public class SrcSelfGuidedTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "self_guided_tour_id", nullable = false)
    private SelfGuidedTour selfGuidedTour;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(name = "src_url", nullable = false, columnDefinition = "text")
    private String srcUrl;

    @Column(name = "app_page", length = 100)
    private String appPage;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
