package com.trheecodes.gtvolcanos.src_volcano;

import com.trheecodes.gtvolcanos.volcano.Volcano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import com.trheecodes.gtvolcanos.self_guided.SelfGuidedTour;

@Getter
@Setter
@Entity
@Table(name = "src_volcanoes")
public class SrcVolcano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "volcano_id", nullable = true)
    private Volcano volcano;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(name = "src_url", nullable = false, columnDefinition = "text")
    private String srcUrl;

    @Column(name = "app_page", length = 100)
    private String appPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_guided_tour_id")
    private SelfGuidedTour selfGuidedTour;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
