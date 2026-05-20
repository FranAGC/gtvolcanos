package com.trheecodes.gtvolcanos.tourism_info;

import com.trheecodes.gtvolcanos.mountain.Mountain;
import com.trheecodes.gtvolcanos.mountain.MountainRepository;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.tourism_info.dto.TourismInfoRequest;
import com.trheecodes.gtvolcanos.tourism_info.dto.TourismInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TourismInfoService {

    private final TourismInfoRepository tourismRepository;
    private final MountainRepository mountainRepository;

    @Transactional(readOnly = true)
    public Page<TourismInfoResponse> getAll(Pageable pageable) {
        return tourismRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TourismInfoResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public TourismInfoResponse getByMountainId(Integer mountainId) {
        TourismInfo entity = tourismRepository.findByMountainId(mountainId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró información turística para la montaña con id: " + mountainId));
        return toResponse(entity);
    }

    @Transactional
    public TourismInfoResponse create(TourismInfoRequest request) {
        Mountain mountain = mountainRepository.findById(request.mountainId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Montaña no encontrada con id: " + request.mountainId()));

        TourismInfo entity = new TourismInfo();
        entity.setMountain(mountain);
        applyPatch(entity, request);
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setUpdatedAt(OffsetDateTime.now());
        tourismRepository.save(entity);
        return toResponse(entity);
    }

    @Transactional
    public TourismInfoResponse patch(Integer id, TourismInfoRequest request) {
        TourismInfo entity = findOrThrow(id);

        if (request.mountainId() != null) {
            Mountain mountain = mountainRepository.findById(request.mountainId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Montaña no encontrada con id: " + request.mountainId()));
            entity.setMountain(mountain);
        }

        applyPatch(entity, request);
        entity.setUpdatedAt(OffsetDateTime.now());
        return toResponse(entity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!tourismRepository.existsById(id)) {
            throw new ResourceNotFoundException("Información turística no encontrada con id: " + id);
        }
        tourismRepository.deleteById(id);
    }

    private TourismInfo findOrThrow(Integer id) {
        return tourismRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Información turística no encontrada con id: " + id));
    }

    private void applyPatch(TourismInfo e, TourismInfoRequest r) {
        if (r.difficulty()   != null) e.setDifficulty(r.difficulty());
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

    private TourismInfoResponse toResponse(TourismInfo e) {
        return new TourismInfoResponse(
                e.getId(),
                e.getMountain().getId(),
                e.getMountain().getName(),
                e.getDifficulty(),
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
                e.getDetails()
        );
    }
}
