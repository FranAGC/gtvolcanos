package com.trheecodes.gtvolcanos.guide.guide_mountain;

import com.trheecodes.gtvolcanos.guide.Guide;
import com.trheecodes.gtvolcanos.guide.GuideRepository;
import com.trheecodes.gtvolcanos.guide.guide_mountain.dto.*;
import com.trheecodes.gtvolcanos.mountain.Mountain;
import com.trheecodes.gtvolcanos.mountain.MountainRepository;
import com.trheecodes.gtvolcanos.shared.exception.ConflictException;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideMountainService {

    private final GuideMountainRepository guideMountainRepository;
    private final GuideRepository        guideRepository;
    private final MountainRepository     mountainRepository;

    @Transactional(readOnly = true)
    public List<MountainByGuideResponse> getMountainsByGuide(Integer guideId) {
        if (!guideRepository.existsById(guideId)) {
            throw new ResourceNotFoundException("Guía no encontrado con id: " + guideId);
        }
        return guideMountainRepository.findByGuideId(guideId).stream()
                .map(gv -> new MountainByGuideResponse(
                        gv.getMountain().getId(),
                        gv.getMountain().getName(),
                        gv.getIsPrimary()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GuideByMountainResponse> getGuidesByMountain(Integer mountainId) {
        if (!mountainRepository.existsById(mountainId)) {
            throw new ResourceNotFoundException("Montaña no encontrada con id: " + mountainId);
        }
        return guideMountainRepository.findByMountainId(mountainId).stream()
                .map(gv -> new GuideByMountainResponse(
                        gv.getGuide().getId(),
                        gv.getGuide().getFirstName() + " " + gv.getGuide().getLastName(),
                        gv.getGuide().getWhatsapp(),
                        gv.getIsPrimary()))
                .toList();
    }

    @Transactional
    public GuideMountainResponse create(GuideMountainRequest request) {
        if (guideMountainRepository.existsByGuideIdAndMountainId(request.guideId(), request.mountainId())) {
            throw new ConflictException("El guía " + request.guideId()
                    + " ya está asignado a la montaña " + request.mountainId());
        }

        Guide   guide   = guideRepository.findById(request.guideId())
                .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrado con id: " + request.guideId()));
        Mountain mountain = mountainRepository.findById(request.mountainId())
                .orElseThrow(() -> new ResourceNotFoundException("Montaña no encontrada con id: " + request.mountainId()));

        GuideMountain gv = new GuideMountain();
        gv.setGuide(guide);
        gv.setMountain(mountain);
        gv.setIsPrimary(request.isPrimary() != null ? request.isPrimary() : false);
        guideMountainRepository.save(gv);
        return toResponse(gv);
    }

    @Transactional
    public GuideMountainResponse patch(Integer id, GuideMountainRequest request) {
        GuideMountain gv = findOrThrow(id);

        if (request.isPrimary() != null) {
            gv.setIsPrimary(request.isPrimary());
        }

        if (request.guideId() != null && !request.guideId().equals(gv.getGuide().getId())) {
            Guide guide = guideRepository.findById(request.guideId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrado con id: " + request.guideId()));
            checkDuplicate(request.guideId(), gv.getMountain().getId(), id);
            gv.setGuide(guide);
        }
        if (request.mountainId() != null && !request.mountainId().equals(gv.getMountain().getId())) {
            Mountain mountain = mountainRepository.findById(request.mountainId())
                    .orElseThrow(() -> new ResourceNotFoundException("Montaña no encontrada con id: " + request.mountainId()));
            checkDuplicate(gv.getGuide().getId(), request.mountainId(), id);
            gv.setMountain(mountain);
        }

        return toResponse(gv);
    }

    @Transactional
    public void delete(Integer id) {
        if (!guideMountainRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asignación no encontrada con id: " + id);
        }
        guideMountainRepository.deleteById(id);
    }

    private GuideMountain findOrThrow(Integer id) {
        return guideMountainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + id));
    }

    private void checkDuplicate(Integer guideId, Integer mountainId, Integer excludeId) {
        guideMountainRepository.findByGuideIdAndMountainId(guideId, mountainId)
                .filter(existing -> !existing.getId().equals(excludeId))
                .ifPresent(existing -> {
                    throw new ConflictException("El guía " + guideId
                            + " ya está asignado a la montaña " + mountainId);
                });
    }

    private GuideMountainResponse toResponse(GuideMountain gv) {
        return new GuideMountainResponse(
                gv.getId(),
                gv.getGuide().getId(),
                gv.getGuide().getFirstName() + " " + gv.getGuide().getLastName(),
                gv.getMountain().getId(),
                gv.getMountain().getName(),
                gv.getIsPrimary()
        );
    }
}
