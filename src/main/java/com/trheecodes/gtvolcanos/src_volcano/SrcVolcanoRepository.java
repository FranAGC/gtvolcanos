package com.trheecodes.gtvolcanos.src_volcano;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SrcVolcanoRepository extends JpaRepository<SrcVolcano, Integer> {

    List<SrcVolcano> findByVolcanoId(Integer volcanoId);

    List<SrcVolcano> findByType(String type);

    List<SrcVolcano> findAllByOrderByIdDesc();

    List<SrcVolcano> findByTypeOrderByIdDesc(String type);
}
