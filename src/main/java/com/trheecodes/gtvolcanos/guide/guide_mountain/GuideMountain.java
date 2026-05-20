package com.trheecodes.gtvolcanos.guide.guide_mountain;

import com.trheecodes.gtvolcanos.guide.Guide;
import com.trheecodes.gtvolcanos.mountain.Mountain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guide_mountain",
        uniqueConstraints = @UniqueConstraint(columnNames = {"guide_id", "mountain_id"}))
public class GuideMountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;
}
