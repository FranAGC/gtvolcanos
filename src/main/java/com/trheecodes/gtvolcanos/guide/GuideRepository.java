package com.trheecodes.gtvolcanos.guide;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Integer> {

    Page<Guide> findByActiveTrue(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);
}
