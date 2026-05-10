package com.trheecodes.gtvolcanos.src_self_guided;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SrcSelfGuidedTourRepository extends JpaRepository<SrcSelfGuidedTour, Integer> {

    List<SrcSelfGuidedTour> findBySelfGuidedTourId(Integer selfGuidedTourId);
}
