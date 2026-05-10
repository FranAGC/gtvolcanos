package com.trheecodes.gtvolcanos.src_self_guided;

import com.trheecodes.gtvolcanos.self_guided.SelfGuidedTour;
import com.trheecodes.gtvolcanos.self_guided.SelfGuidedTourRepository;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.src_self_guided.dto.SrcSelfGuidedTourRequest;
import com.trheecodes.gtvolcanos.src_self_guided.dto.SrcSelfGuidedTourResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SrcSelfGuidedTourService {

    private final SrcSelfGuidedTourRepository srcRepository;
    private final SelfGuidedTourRepository    selfGuidedTourRepository;

    // ── GET by self_guided_tour_id ────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<SrcSelfGuidedTourResponse> getBySelfGuidedTourId(Integer selfGuidedTourId) {
        if (!selfGuidedTourRepository.existsById(selfGuidedTourId)) {
            throw new ResourceNotFoundException(
                    "Tour auto-guiado no encontrado con id: " + selfGuidedTourId);
        }
        return srcRepository.findBySelfGuidedTourId(selfGuidedTourId)
                .stream().map(this::toResponse).toList();
    }

    // ── GET by id ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public SrcSelfGuidedTourResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    // ── POST ──────────────────────────────────────────────────────────────────

    @Transactional
    public SrcSelfGuidedTourResponse create(SrcSelfGuidedTourRequest request) {
        SelfGuidedTour parent = selfGuidedTourRepository.findById(request.selfGuidedTourId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tour auto-guiado no encontrado con id: " + request.selfGuidedTourId()));

        SrcSelfGuidedTour src = new SrcSelfGuidedTour();
        src.setSelfGuidedTour(parent);
        applyPatch(src, request);
        src.setCreatedAt(OffsetDateTime.now());
        srcRepository.save(src);
        return toResponse(src);
    }

    // ── PATCH ─────────────────────────────────────────────────────────────────

    @Transactional
    public SrcSelfGuidedTourResponse patch(Integer id, SrcSelfGuidedTourRequest request) {
        SrcSelfGuidedTour src = findOrThrow(id);

        if (request.selfGuidedTourId() != null
                && !request.selfGuidedTourId().equals(src.getSelfGuidedTour().getId())) {
            SelfGuidedTour parent = selfGuidedTourRepository.findById(request.selfGuidedTourId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tour auto-guiado no encontrado con id: " + request.selfGuidedTourId()));
            src.setSelfGuidedTour(parent);
        }

        applyPatch(src, request);
        return toResponse(src);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @Transactional
    public void delete(Integer id) {
        if (!srcRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso no encontrado con id: " + id);
        }
        srcRepository.deleteById(id);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private SrcSelfGuidedTour findOrThrow(Integer id) {
        return srcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recurso no encontrado con id: " + id));
    }

    private void applyPatch(SrcSelfGuidedTour s, SrcSelfGuidedTourRequest r) {
        if (r.type()        != null) s.setType(r.type());
        if (r.description() != null) s.setDescription(r.description());
        if (r.srcUrl()      != null) s.setSrcUrl(r.srcUrl());
        if (r.appPage()     != null) s.setAppPage(r.appPage());
    }

    private SrcSelfGuidedTourResponse toResponse(SrcSelfGuidedTour s) {
        return new SrcSelfGuidedTourResponse(
                s.getId(),
                s.getSelfGuidedTour().getId(),
                s.getSelfGuidedTour().getTitle(),
                s.getType(),
                s.getDescription(),
                s.getSrcUrl(),
                s.getAppPage(),
                s.getCreatedAt()
        );
    }
}
