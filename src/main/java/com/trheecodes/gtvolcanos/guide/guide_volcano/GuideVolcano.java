package com.trheecodes.gtvolcanos.guide.guide_volcano;

import com.trheecodes.gtvolcanos.guide.Guide;
import com.trheecodes.gtvolcanos.volcano.Volcano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guide_volcano",
        uniqueConstraints = @UniqueConstraint(columnNames = {"guide_id", "volcano_id"}))
public class GuideVolcano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "volcano_id", nullable = false)
    private Volcano volcano;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;
}
