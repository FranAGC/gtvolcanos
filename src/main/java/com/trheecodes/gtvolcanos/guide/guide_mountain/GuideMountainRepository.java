package com.trheecodes.gtvolcanos.guide.guide_mountain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuideMountainRepository extends JpaRepository<GuideMountain, Integer> {

    List<GuideMountain> findByGuideId(Integer guideId);

    List<GuideMountain> findByMountainId(Integer mountainId);

    boolean existsByGuideIdAndMountainId(Integer guideId, Integer mountainId);

    Optional<GuideMountain> findByGuideIdAndMountainId(Integer guideId, Integer mountainId);
}
