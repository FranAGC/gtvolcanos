package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolcanoService {

    private final VolcanoRepository volcanoRepository;

    @Transactional(readOnly = true)
    public List<VolcanoSummaryResponse> getAllVolcanoes() {
        return volcanoRepository.findAll().stream()
                .map(v -> new VolcanoSummaryResponse(v.getId(), v.getName(), v.getCountry(), v.getRegion()))
                .toList();
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
