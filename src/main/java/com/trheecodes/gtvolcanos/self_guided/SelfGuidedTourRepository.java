package com.trheecodes.gtvolcanos.self_guided;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelfGuidedTourRepository extends JpaRepository<SelfGuidedTour, Integer> {

    List<SelfGuidedTour> findByVolcanoId(Integer volcanoId);
}
