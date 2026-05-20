package com.trheecodes.gtvolcanos.mountain;

import com.trheecodes.gtvolcanos.mountain.dto.*;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.tourism_info.TourismInfo;
import com.trheecodes.gtvolcanos.tourism_info.TourismInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;
    private final TourismInfoRepository tourismInfoRepository;

    @Transactional(readOnly = true)
    public Page<VolcanoResponse> getAllVolcanoes(Pageable pageable) {
        return mountainRepository.findByIsVolcanoTrue(pageable)
                .map(v -> new VolcanoResponse(v.getId(), v.getName(), v.getType(), v.getElevationM(), v.getRegion(), v.getLatitude(), v.getLongitude(), v.getLastEruption()));
    }

    @Transactional(readOnly = true)
    public List<MountainRefResponse> getReto37() {
        return mountainRepository.findByReto37True().stream()
                .map(v -> new MountainRefResponse(v.getId(), v.getName(), v.getRegion()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MountainRefResponse> getFormer37() {
        return mountainRepository.findByFormer37True().stream()
                .map(v -> new MountainRefResponse(v.getId(), v.getName(), v.getRegion()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PopularMountainResponse> getPopular() {
        List<Mountain> mountains = mountainRepository.findByReto37TrueOrFormer37True();
        Map<Integer, String> alertLevels = tourismInfoRepository
                .findByMountain_IdIn(mountains.stream().map(Mountain::getId).toList())
                .stream()
                .collect(Collectors.toMap(t -> t.getMountain().getId(), TourismInfo::getCurrentAlertLevel));
        return mountains.stream()
                .map(v -> new PopularMountainResponse(v.getId(), v.getName(), v.getRegion(), v.getLatitude(), v.getLongitude(), v.getImageUrl(), alertLevels.get(v.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public MountainResponse getMountainById(Integer id) {
        Mountain v = mountainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Montaña no encontrada con id: " + id));

        return new MountainResponse(
                v.getId(), v.getName(), v.getCountryId(), v.getRegion(),
                v.getLatitude(), v.getLongitude(), v.getIsVolcano(), v.getElevationM(),
                v.getType(), v.getStatus(), v.getLastEruption(),
                v.getVei(), v.getCasualties(), v.getMonitored(), v.getReto37(), v.getFormer37(), v.getImageUrl(),
                v.getDescription());
    }

    @Transactional
    public MountainResponse createMountain(MountainRequest request) {
        Mountain v = new Mountain();
        applyRequest(v, request);
        v.setCreatedAt(OffsetDateTime.now());
        v.setUpdatedAt(OffsetDateTime.now());
        mountainRepository.save(v);
        return toResponse(v);
    }

    @Transactional
    public MountainResponse updateMountain(Integer id, MountainRequest request) {
        Mountain v = mountainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Montaña no encontrada con id: " + id));

        applyRequest(v, request);
        v.setUpdatedAt(OffsetDateTime.now());
        return toResponse(v);
    }

    @Transactional
    public void deleteMountain(Integer id) {
        if (!mountainRepository.existsById(id)) {
            throw new ResourceNotFoundException("Montaña no encontrada con id: " + id);
        }
        mountainRepository.deleteById(id);
    }

    private void applyRequest(Mountain v, MountainRequest r) {
        v.setName(r.name());
        v.setCountryId(r.countryId());
        v.setRegion(r.region());
        v.setLatitude(r.latitude());
        v.setLongitude(r.longitude());
        v.setIsVolcano(r.isVolcano() != null ? r.isVolcano() : false);
        v.setElevationM(r.elevationM());
        v.setType(r.type());
        v.setStatus(r.status());
        v.setLastEruption(r.lastEruption());
        v.setVei(r.vei());
        v.setCasualties(r.casualties() != null ? r.casualties() : 0);
        v.setMonitored(r.monitored() != null ? r.monitored() : false);
        v.setReto37(r.reto37() != null ? r.reto37() : false);
        v.setFormer37(r.former37() != null ? r.former37() : false);
        v.setImageUrl(r.imageUrl());
        v.setDescription(r.description());
    }

    private MountainResponse toResponse(Mountain v) {
        return new MountainResponse(
                v.getId(), v.getName(), v.getCountryId(), v.getRegion(),
                v.getLatitude(), v.getLongitude(), v.getIsVolcano(), v.getElevationM(),
                v.getType(), v.getStatus(), v.getLastEruption(),
                v.getVei(), v.getCasualties(), v.getMonitored(), v.getReto37(), v.getFormer37(), v.getImageUrl(),
                v.getDescription());
    }
}
