package com.trheecodes.gtvolcanos.mountain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MountainRepository extends JpaRepository<Mountain, Integer> {
    Page<Mountain> findByIsVolcanoTrue(Pageable pageable);
    List<Mountain> findByReto37True();
    List<Mountain> findByFormer37True();
}
