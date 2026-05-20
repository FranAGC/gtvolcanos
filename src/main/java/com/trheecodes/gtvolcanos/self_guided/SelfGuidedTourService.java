package com.trheecodes.gtvolcanos.self_guided;

import com.trheecodes.gtvolcanos.mountain.Mountain;
import com.trheecodes.gtvolcanos.mountain.MountainRepository;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourRequest;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourResponse;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourSummaryResponse;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SelfGuidedTourService {

    private final SelfGuidedTourRepository selfGuidedTourRepository;
    private final MountainRepository        mountainRepository;

    @Transactional(readOnly = true)
    public List<SelfGuidedTourSummaryResponse> getByMountainId(Integer mountainId) {
        if (!mountainRepository.existsById(mountainId)) {
            throw new ResourceNotFoundException("Montaña no encontrada con id: " + mountainId);
        }
        return selfGuidedTourRepository.findByMountainId(mountainId)
                .stream().map(this::toSummaryResponse).toList();
    }

    @Transactional(readOnly = true)
    public SelfGuidedTourResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public SelfGuidedTourResponse create(SelfGuidedTourRequest request) {
        Mountain mountain = mountainRepository.findById(request.mountainId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Montaña no encontrada con id: " + request.mountainId()));

        SelfGuidedTour tour = new SelfGuidedTour();
        tour.setMountain(mountain);
        applyPatch(tour, request);
        tour.setCreatedAt(OffsetDateTime.now());
        tour.setUpdatedAt(OffsetDateTime.now());
        selfGuidedTourRepository.save(tour);
        return toResponse(tour);
    }

    @Transactional
    public SelfGuidedTourResponse patch(Integer id, SelfGuidedTourRequest request) {
        SelfGuidedTour tour = findOrThrow(id);

        if (request.mountainId() != null
                && !request.mountainId().equals(tour.getMountain().getId())) {
            Mountain mountain = mountainRepository.findById(request.mountainId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Montaña no encontrada con id: " + request.mountainId()));
            tour.setMountain(mountain);
        }

        applyPatch(tour, request);
        tour.setUpdatedAt(OffsetDateTime.now());
        return toResponse(tour);
    }

    @Transactional
    public void delete(Integer id) {
        if (!selfGuidedTourRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tour auto-guiado no encontrado con id: " + id);
        }
        selfGuidedTourRepository.deleteById(id);
    }

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
