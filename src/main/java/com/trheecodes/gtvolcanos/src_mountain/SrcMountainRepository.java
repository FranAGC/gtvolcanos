package com.trheecodes.gtvolcanos.src_mountain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SrcMountainRepository extends JpaRepository<SrcMountain, Integer> {

    List<SrcMountain> findByMountainId(Integer mountainId);

    List<SrcMountain> findByType(String type);

    List<SrcMountain> findAllByOrderByIdDesc();

    List<SrcMountain> findByTypeOrderByIdDesc(String type);

    @Query(value = "SELECT * FROM src_mountains WHERE type = :type AND self_guided_tour_id IS NULL ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<SrcMountain> findTop4ByTypeAndSelfGuidedTourIsNullOrderByIdDesc(@Param("type") String type);

    List<SrcMountain> findBySelfGuidedTour_Id(Integer selfGuidedTourId);

    List<SrcMountain> findByMountainIdAndType(Integer mountainId, String type);

    List<SrcMountain> findByMountainIdAndTypeNotIn(Integer mountainId, List<String> types);

}
