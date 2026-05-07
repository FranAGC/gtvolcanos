package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VolcanoService {

    private final VolcanoRepository volcanoRepository;

    @Transactional(readOnly = true)
    public Page<VolcanoSummaryResponse> getAllVolcanoes(Pageable pageable) {
        return volcanoRepository.findAll(pageable)
                .map(v -> new VolcanoSummaryResponse(v.getId(), v.getName(), v.getCountry(), v.getRegion()));
    }

    @Transactional(readOnly = true)
    public VolcanoResponse getVolcanoById(Integer id) {
        Volcano v = volcanoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volcán no encontrado con id: " + id));

        return new VolcanoResponse(
                v.getId(), v.getName(), v.getCountry(), v.getRegion(),
                v.getLatitude(), v.getLongitude(), v.getElevationM(),
                v.getType(), v.getStatus(), v.getLastEruption(),
                v.getVei(), v.getCasualties(), v.getMonitored(),
                v.getDescription(), v.getCreatedAt(), v.getUpdatedAt()
        );
    }
}
