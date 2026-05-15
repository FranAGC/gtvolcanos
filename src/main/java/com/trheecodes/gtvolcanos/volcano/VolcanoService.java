package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.shared.exception.ConflictException;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoRequest;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class VolcanoService {

    private final VolcanoRepository volcanoRepository;

    @Transactional(readOnly = true)
    public Page<VolcanoSummaryResponse> getAllVolcanoes(Pageable pageable) {
        return volcanoRepository.findAll(pageable)
                .map(v -> new VolcanoSummaryResponse(v.getId(), v.getName(), v.getCountry(), v.getRegion(), v.getPopularity(), v.getImageUrl()));
    }

    @Transactional(readOnly = true)
    public VolcanoResponse getVolcanoById(Integer id) {
        Volcano v = volcanoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volcán no encontrado con id: " + id));

        return new VolcanoResponse(
                v.getId(), v.getName(), v.getCountry(), v.getRegion(),
                v.getLatitude(), v.getLongitude(), v.getElevationM(),
                v.getType(), v.getStatus(), v.getLastEruption(),
                v.getVei(), v.getCasualties(), v.getMonitored(), v.getPopularity(), v.getImageUrl(),
                v.getDescription(), v.getCreatedAt(), v.getUpdatedAt());
    }

    @Transactional
    public VolcanoResponse createVolcano(VolcanoRequest request) {
        if (request.popularity() != null && volcanoRepository.existsByPopularity(request.popularity())) {
            throw new ConflictException("Ya existe un volcán con popularity: " + request.popularity());
        }

        Volcano v = new Volcano();
        applyRequest(v, request);
        v.setCreatedAt(OffsetDateTime.now());
        v.setUpdatedAt(OffsetDateTime.now());
        volcanoRepository.save(v);
        return toResponse(v);
    }

    @Transactional
    public VolcanoResponse updateVolcano(Integer id, VolcanoRequest request) {
        Volcano v = volcanoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volcán no encontrado con id: " + id));

        if (request.popularity() != null 
                && !request.popularity().equals(v.getPopularity()) 
                && volcanoRepository.existsByPopularityAndIdNot(request.popularity(), id)) {
            throw new ConflictException("Ya existe un volcán con popularity: " + request.popularity());
        }

        applyRequest(v, request);
        v.setUpdatedAt(OffsetDateTime.now());
        return toResponse(v);
    }

    @Transactional
    public void deleteVolcano(Integer id) {
        if (!volcanoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Volcán no encontrado con id: " + id);
        }
        volcanoRepository.deleteById(id);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void applyRequest(Volcano v, VolcanoRequest r) {
        v.setName(r.name());
        v.setCountry(r.country());
        v.setRegion(r.region());
        v.setLatitude(r.latitude());
        v.setLongitude(r.longitude());
        v.setElevationM(r.elevationM());
        v.setType(r.type());
        v.setStatus(r.status());
        v.setLastEruption(r.lastEruption());
        v.setVei(r.vei());
        v.setCasualties(r.casualties() != null ? r.casualties() : 0);
        v.setMonitored(r.monitored() != null ? r.monitored() : false);
        v.setPopularity(r.popularity());
        v.setImageUrl(r.imageUrl());
        v.setDescription(r.description());
    }

    private VolcanoResponse toResponse(Volcano v) {
        return new VolcanoResponse(
                v.getId(), v.getName(), v.getCountry(), v.getRegion(),
                v.getLatitude(), v.getLongitude(), v.getElevationM(),
                v.getType(), v.getStatus(), v.getLastEruption(),
                v.getVei(), v.getCasualties(), v.getMonitored(), v.getPopularity(), v.getImageUrl(),
                v.getDescription(), v.getCreatedAt(), v.getUpdatedAt());
    }
}
