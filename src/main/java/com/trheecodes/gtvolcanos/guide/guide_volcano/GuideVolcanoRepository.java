package com.trheecodes.gtvolcanos.guide.guide_volcano;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuideVolcanoRepository extends JpaRepository<GuideVolcano, Integer> {

    List<GuideVolcano> findByGuideId(Integer guideId);

    List<GuideVolcano> findByVolcanoId(Integer volcanoId);

    boolean existsByGuideIdAndVolcanoId(Integer guideId, Integer volcanoId);

    Optional<GuideVolcano> findByGuideIdAndVolcanoId(Integer guideId, Integer volcanoId);
}
