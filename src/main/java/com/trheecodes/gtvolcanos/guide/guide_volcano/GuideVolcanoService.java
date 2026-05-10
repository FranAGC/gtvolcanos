package com.trheecodes.gtvolcanos.guide.guide_volcano;

import com.trheecodes.gtvolcanos.guide.Guide;
import com.trheecodes.gtvolcanos.guide.GuideRepository;
import com.trheecodes.gtvolcanos.guide.guide_volcano.dto.*;
import com.trheecodes.gtvolcanos.shared.exception.ConflictException;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.volcano.Volcano;
import com.trheecodes.gtvolcanos.volcano.VolcanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideVolcanoService {

    private final GuideVolcanoRepository guideVolcanoRepository;
    private final GuideRepository        guideRepository;
    private final VolcanoRepository      volcanoRepository;

    // ── GET /guide-volcano/guides/{guideId} ───────────────────────────────────

    @Transactional(readOnly = true)
    public List<VolcanoByGuideResponse> getVolcanoesByGuide(Integer guideId) {
        if (!guideRepository.existsById(guideId)) {
            throw new ResourceNotFoundException("Guía no encontrado con id: " + guideId);
        }
        return guideVolcanoRepository.findByGuideId(guideId).stream()
                .map(gv -> new VolcanoByGuideResponse(
                        gv.getVolcano().getId(),
                        gv.getVolcano().getName(),
                        gv.getIsPrimary()))
                .toList();
    }

    // ── GET /guide-volcano/volcanoes/{volcanoId} ──────────────────────────────

    @Transactional(readOnly = true)
    public List<GuideByVolcanoResponse> getGuidesByVolcano(Integer volcanoId) {
        if (!volcanoRepository.existsById(volcanoId)) {
            throw new ResourceNotFoundException("Volcán no encontrado con id: " + volcanoId);
        }
        return guideVolcanoRepository.findByVolcanoId(volcanoId).stream()
                .map(gv -> new GuideByVolcanoResponse(
                        gv.getGuide().getId(),
                        gv.getGuide().getFirstName() + " " + gv.getGuide().getLastName(),
                        gv.getGuide().getWhatsapp(),
                        gv.getIsPrimary()))
                .toList();
    }

    // ── POST ──────────────────────────────────────────────────────────────────

    @Transactional
    public GuideVolcanoResponse create(GuideVolcanoRequest request) {
        if (guideVolcanoRepository.existsByGuideIdAndVolcanoId(request.guideId(), request.volcanoId())) {
            throw new ConflictException("El guía " + request.guideId()
                    + " ya está asignado al volcán " + request.volcanoId());
        }

        Guide   guide   = guideRepository.findById(request.guideId())
                .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrado con id: " + request.guideId()));
        Volcano volcano = volcanoRepository.findById(request.volcanoId())
                .orElseThrow(() -> new ResourceNotFoundException("Volcán no encontrado con id: " + request.volcanoId()));

        GuideVolcano gv = new GuideVolcano();
        gv.setGuide(guide);
        gv.setVolcano(volcano);
        gv.setIsPrimary(request.isPrimary() != null ? request.isPrimary() : false);
        guideVolcanoRepository.save(gv);
        return toResponse(gv);
    }

    // ── PATCH ─────────────────────────────────────────────────────────────────

    @Transactional
    public GuideVolcanoResponse patch(Integer id, GuideVolcanoRequest request) {
        GuideVolcano gv = findOrThrow(id);

        if (request.isPrimary() != null) {
            gv.setIsPrimary(request.isPrimary());
        }

        // Allow reassigning guide or volcano if the new combination doesn't already exist
        if (request.guideId() != null && !request.guideId().equals(gv.getGuide().getId())) {
            Guide guide = guideRepository.findById(request.guideId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrado con id: " + request.guideId()));
            checkDuplicate(request.guideId(), gv.getVolcano().getId(), id);
            gv.setGuide(guide);
        }
        if (request.volcanoId() != null && !request.volcanoId().equals(gv.getVolcano().getId())) {
            Volcano volcano = volcanoRepository.findById(request.volcanoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Volcán no encontrado con id: " + request.volcanoId()));
            checkDuplicate(gv.getGuide().getId(), request.volcanoId(), id);
            gv.setVolcano(volcano);
        }

        return toResponse(gv);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @Transactional
    public void delete(Integer id) {
        if (!guideVolcanoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asignación no encontrada con id: " + id);
        }
        guideVolcanoRepository.deleteById(id);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private GuideVolcano findOrThrow(Integer id) {
        return guideVolcanoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + id));
    }

    private void checkDuplicate(Integer guideId, Integer volcanoId, Integer excludeId) {
        guideVolcanoRepository.findByGuideIdAndVolcanoId(guideId, volcanoId)
                .filter(existing -> !existing.getId().equals(excludeId))
                .ifPresent(existing -> {
                    throw new ConflictException("El guía " + guideId
                            + " ya está asignado al volcán " + volcanoId);
                });
    }

    private GuideVolcanoResponse toResponse(GuideVolcano gv) {
        return new GuideVolcanoResponse(
                gv.getId(),
                gv.getGuide().getId(),
                gv.getGuide().getFirstName() + " " + gv.getGuide().getLastName(),
                gv.getVolcano().getId(),
                gv.getVolcano().getName(),
                gv.getIsPrimary()
        );
    }
}
