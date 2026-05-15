package com.trheecodes.gtvolcanos.volcano;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Integer> {
    boolean existsByPopularity(Integer popularity);
    boolean existsByPopularityAndIdNot(Integer popularity, Integer id);
}
