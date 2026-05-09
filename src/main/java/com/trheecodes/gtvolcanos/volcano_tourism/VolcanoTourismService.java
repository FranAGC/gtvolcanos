package com.trheecodes.gtvolcanos.volcano_tourism;

import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.Volcano;
import com.trheecodes.gtvolcanos.volcano.VolcanoRepository;
import com.trheecodes.gtvolcanos.volcano_tourism.dto.VolcanoTourismRequest;
import com.trheecodes.gtvolcanos.volcano_tourism.dto.VolcanoTourismResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class VolcanoTourismService {

    private final VolcanoTourismRepository tourismRepository;
    private final VolcanoRepository volcanoRepository;

    // ── GET all ──────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<VolcanoTourismResponse> getAll(Pageable pageable) {
        return tourismRepository.findAll(pageable).map(this::toResponse);
    }

    // ── GET by id ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public VolcanoTourismResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    // ── GET by volcano_id ────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public VolcanoTourismResponse getByVolcanoId(Integer volcanoId) {
        VolcanoTourism entity = tourismRepository.findByVolcanoId(volcanoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró información turística para el volcán con id: " + volcanoId));
        return toResponse(entity);
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Transactional
    public VolcanoTourismResponse create(VolcanoTourismRequest request) {
        Volcano volcano = volcanoRepository.findById(request.volcanoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Volcán no encontrado con id: " + request.volcanoId()));

        VolcanoTourism entity = new VolcanoTourism();
        entity.setVolcano(volcano);
        applyPatch(entity, request);
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setUpdatedAt(OffsetDateTime.now());
        tourismRepository.save(entity);
        return toResponse(entity);
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @Transactional
    public VolcanoTourismResponse patch(Integer id, VolcanoTourismRequest request) {
        VolcanoTourism entity = findOrThrow(id);

        // Allow reassigning to a different volcano if volcanoId is provided
        if (request.volcanoId() != null) {
            Volcano volcano = volcanoRepository.findById(request.volcanoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Volcán no encontrado con id: " + request.volcanoId()));
            entity.setVolcano(volcano);
        }

        applyPatch(entity, request);
        entity.setUpdatedAt(OffsetDateTime.now());
        return toResponse(entity);
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void delete(Integer id) {
        if (!tourismRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turismo volcánico no encontrado con id: " + id);
        }
        tourismRepository.deleteById(id);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private VolcanoTourism findOrThrow(Integer id) {
        return tourismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turismo volcánico no encontrado con id: " + id));
    }

    /**
     * Applies only the non-null fields from the request (partial-update semantics).
     * Works for both POST (all fields start null/default) and PATCH.
     */
    private void applyPatch(VolcanoTourism e, VolcanoTourismRequest r) {
        if (r.accessDifficulty()   != null) e.setAccessDifficulty(r.accessDifficulty());
        if (r.hikingTrail()        != null) e.setHikingTrail(r.hikingTrail());
        if (r.guidedTourRequired() != null) e.setGuidedTourRequired(r.guidedTourRequired());
        if (r.entranceFeeUsd()     != null) e.setEntranceFeeUsd(r.entranceFeeUsd());
        if (r.bestSeason()         != null) e.setBestSeason(r.bestSeason());
        if (r.nearestCity()        != null) e.setNearestCity(r.nearestCity());
        if (r.distanceToCityKm()   != null) e.setDistanceToCityKm(r.distanceToCityKm());
        if (r.visitDurationHrs()   != null) e.setVisitDurationHrs(r.visitDurationHrs());
        if (r.parking()            != null) e.setParking(r.parking());
        if (r.restrooms()          != null) e.setRestrooms(r.restrooms());
        if (r.visitorCenter()      != null) e.setVisitorCenter(r.visitorCenter());
        if (r.campingAllowed()     != null) e.setCampingAllowed(r.campingAllowed());
        if (r.foodNearby()         != null) e.setFoodNearby(r.foodNearby());
        if (r.currentAlertLevel()  != null) e.setCurrentAlertLevel(r.currentAlertLevel());
        if (r.emergencyContact()   != null) e.setEmergencyContact(r.emergencyContact());
        if (r.details()            != null) e.setDetails(r.details());
    }

    private VolcanoTourismResponse toResponse(VolcanoTourism e) {
        return new VolcanoTourismResponse(
                e.getId(),
                e.getVolcano().getId(),
                e.getVolcano().getName(),
                e.getAccessDifficulty(),
                e.getHikingTrail(),
                e.getGuidedTourRequired(),
                e.getEntranceFeeUsd(),
                e.getBestSeason(),
                e.getNearestCity(),
                e.getDistanceToCityKm(),
                e.getVisitDurationHrs(),
                e.getParking(),
                e.getRestrooms(),
                e.getVisitorCenter(),
                e.getCampingAllowed(),
                e.getFoodNearby(),
                e.getCurrentAlertLevel(),
                e.getEmergencyContact(),
                e.getDetails(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
