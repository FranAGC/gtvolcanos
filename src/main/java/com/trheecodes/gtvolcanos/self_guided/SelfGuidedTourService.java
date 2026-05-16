package com.trheecodes.gtvolcanos.self_guided;

import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourRequest;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourResponse;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourSummaryResponse;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.Volcano;
import com.trheecodes.gtvolcanos.volcano.VolcanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SelfGuidedTourService {

    private final SelfGuidedTourRepository selfGuidedTourRepository;
    private final VolcanoRepository        volcanoRepository;

    // ── GET by volcano_id ────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<SelfGuidedTourSummaryResponse> getByVolcanoId(Integer volcanoId) {
        if (!volcanoRepository.existsById(volcanoId)) {
            throw new ResourceNotFoundException("Volcán no encontrado con id: " + volcanoId);
        }
        return selfGuidedTourRepository.findByVolcanoId(volcanoId)
                .stream().map(this::toSummaryResponse).toList();
    }

    // ── GET by id ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public SelfGuidedTourResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Transactional
    public SelfGuidedTourResponse create(SelfGuidedTourRequest request) {
        Volcano volcano = volcanoRepository.findById(request.volcanoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Volcán no encontrado con id: " + request.volcanoId()));

        SelfGuidedTour tour = new SelfGuidedTour();
        tour.setVolcano(volcano);
        applyPatch(tour, request);
        tour.setCreatedAt(OffsetDateTime.now());
        tour.setUpdatedAt(OffsetDateTime.now());
        selfGuidedTourRepository.save(tour);
        return toResponse(tour);
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @Transactional
    public SelfGuidedTourResponse patch(Integer id, SelfGuidedTourRequest request) {
        SelfGuidedTour tour = findOrThrow(id);

        if (request.volcanoId() != null
                && !request.volcanoId().equals(tour.getVolcano().getId())) {
            Volcano volcano = volcanoRepository.findById(request.volcanoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Volcán no encontrado con id: " + request.volcanoId()));
            tour.setVolcano(volcano);
        }

        applyPatch(tour, request);
        tour.setUpdatedAt(OffsetDateTime.now());
        return toResponse(tour);
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void delete(Integer id) {
        if (!selfGuidedTourRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tour auto-guiado no encontrado con id: " + id);
        }
        selfGuidedTourRepository.deleteById(id);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private SelfGuidedTour findOrThrow(Integer id) {
        return selfGuidedTourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tour auto-guiado no encontrado con id: " + id));
    }

    private SelfGuidedTourSummaryResponse toSummaryResponse(SelfGuidedTour t) {
        return new SelfGuidedTourSummaryResponse(
                t.getId(),
                t.getTitle(),
                t.getDifficulty(),
                t.getDistanceKm()
        );
    }

    private void applyPatch(SelfGuidedTour t, SelfGuidedTourRequest r) {
        if (r.title()                != null) t.setTitle(r.title());
        if (r.difficulty()           != null) t.setDifficulty(r.difficulty());
        if (r.distanceKm()           != null) t.setDistanceKm(r.distanceKm());
        if (r.estimatedDurationHrs() != null) t.setEstimatedDurationHrs(r.estimatedDurationHrs());
        if (r.startingPointName()    != null) t.setStartingPointName(r.startingPointName());
        if (r.startingPointLat()     != null) t.setStartingPointLat(r.startingPointLat());
        if (r.startingPointLng()     != null) t.setStartingPointLng(r.startingPointLng());
        if (r.instructions()         != null) t.setInstructions(r.instructions());
        if (r.recommendedGear()      != null) t.setRecommendedGear(r.recommendedGear().toArray(new String[0]));
        if (r.active()               != null) t.setActive(r.active());
    }

    private SelfGuidedTourResponse toResponse(SelfGuidedTour t) {
        return new SelfGuidedTourResponse(
                t.getId(),
                t.getTitle(),
                t.getDifficulty(),
                t.getDistanceKm(),
                t.getEstimatedDurationHrs(),
                t.getStartingPointName(),
                t.getStartingPointLat(),
                t.getStartingPointLng(),
                t.getInstructions(),
                t.getRecommendedGear() != null ? List.of(t.getRecommendedGear()) : List.of(),
                t.getActive(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }
}
