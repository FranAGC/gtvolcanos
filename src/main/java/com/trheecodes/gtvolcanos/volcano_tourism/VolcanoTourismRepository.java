package com.trheecodes.gtvolcanos.volcano_tourism;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolcanoTourismRepository extends JpaRepository<VolcanoTourism, Integer> {

    Optional<VolcanoTourism> findByVolcanoId(Integer volcanoId);

    boolean existsByVolcanoId(Integer volcanoId);
}
