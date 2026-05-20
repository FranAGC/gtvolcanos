package com.trheecodes.gtvolcanos.tourism_info;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourismInfoRepository extends JpaRepository<TourismInfo, Integer> {

    Optional<TourismInfo> findByMountainId(Integer mountainId);

    boolean existsByMountainId(Integer mountainId);

    List<TourismInfo> findByMountain_IdIn(List<Integer> mountainIds);
}
