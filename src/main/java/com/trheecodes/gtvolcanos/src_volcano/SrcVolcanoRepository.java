package com.trheecodes.gtvolcanos.src_volcano;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SrcVolcanoRepository extends JpaRepository<SrcVolcano, Integer> {

    List<SrcVolcano> findByVolcanoId(Integer volcanoId);

    List<SrcVolcano> findByType(String type);

    List<SrcVolcano> findAllByOrderByIdDesc();

    List<SrcVolcano> findByTypeOrderByIdDesc(String type);

    @Query(value = "SELECT * FROM src_volcanoes WHERE type = :type AND self_guided_tour_id IS NULL ORDER BY id DESC LIMIT 4", nativeQuery = true)
    List<SrcVolcano> findTop4ByTypeAndSelfGuidedTourIsNullOrderByIdDesc(@Param("type") String type);

    List<SrcVolcano> findBySelfGuidedTour_Id(Integer selfGuidedTourId);

}
