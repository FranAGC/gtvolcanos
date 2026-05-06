package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolcanoService {

    private final VolcanoRepository volcanoRepository;

    public VolcanoService(VolcanoRepository volcanoRepository) {
        this.volcanoRepository = volcanoRepository;
    }

    public List<VolcanoSummaryResponse> getAllVolcanoes() {
        return volcanoRepository.findAll().stream()
                .map(v -> new VolcanoSummaryResponse(
                        v.getId(),
                        v.getName(),
                        v.getCountry(),
                        v.getRegion()
                ))
                .collect(Collectors.toList());
    }

    public VolcanoResponse getVolcanoById(Integer id) {
        Volcano v = volcanoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volcano not found with id: " + id));
        
        return new VolcanoResponse(
                v.getId(),
                v.getName(),
                v.getCountry(),
                v.getRegion(),
                v.getLatitude(),
                v.getLongitude(),
                v.getElevationM(),
                v.getType(),
                v.getStatus(),
                v.getLastEruption(),
                v.getVei(),
                v.getCasualties(),
                v.getMonitored(),
                v.getDescription(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}
