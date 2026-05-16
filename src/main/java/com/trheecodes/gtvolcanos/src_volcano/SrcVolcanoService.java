package com.trheecodes.gtvolcanos.src_volcano;

import com.trheecodes.gtvolcanos.volcano.Volcano;
import com.trheecodes.gtvolcanos.volcano.VolcanoRepository;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.src_volcano.dto.SrcVolcanoRequest;
import com.trheecodes.gtvolcanos.src_volcano.dto.SrcVolcanoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SrcVolcanoService {

    private final SrcVolcanoRepository srcRepository;
    private final VolcanoRepository    volcanoRepository;
    private final com.trheecodes.gtvolcanos.self_guided.SelfGuidedTourRepository selfGuidedRepository;

    // ── GET by volcano_id ────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<SrcVolcanoResponse> getByVolcanoId(Integer volcanoId) {
        if (!volcanoRepository.existsById(volcanoId)) {
            throw new ResourceNotFoundException(
                    "Tour auto-guiado no encontrado con id: " + volcanoId);
        }
        return srcRepository.findByVolcanoId(volcanoId)
                .stream().map(this::toResponse).toList();
    }

    // ── GET all (with optional type filter) ───────────────────────────────────

    @Transactional(readOnly = true)
    public List<SrcVolcanoResponse> getAll() {
        List<SrcVolcano> result = srcRepository.findAllByOrderByIdDesc();
        // Limitar a los últimos 5 registros
        return result.stream()
                .limit(5)
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SrcVolcanoResponse> getBySelfGuidedId(Integer selfguidedId) {
        List<SrcVolcano> result = srcRepository.findBySelfGuidedTour_Id(selfguidedId);
        return result.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SrcVolcanoResponse> getHomePosts() {
        List<SrcVolcano> result = srcRepository.findTop4ByTypeAndSelfGuidedTourIsNullOrderByIdDesc("post");
        return result.stream().map(this::toResponse).toList();
    }

    // ── GET by id ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public SrcVolcanoResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    // ── POST ──────────────────────────────────────────────────────────────────

    @Transactional
    public SrcVolcanoResponse create(SrcVolcanoRequest request) {
        Volcano parent = volcanoRepository.findById(request.volcanoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tour auto-guiado no encontrado con id: " + request.volcanoId()));

        SrcVolcano src = new SrcVolcano();
        src.setVolcano(parent);
        applyPatch(src, request);
        // set self guided tour if provided
        if (request.selfGuidedTourId() != null) {
            var s = selfGuidedRepository.findById(request.selfGuidedTourId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tour auto-guiado no encontrado con id: " + request.selfGuidedTourId()));
            src.setSelfGuidedTour(s);
        }
        src.setCreatedAt(OffsetDateTime.now());
        srcRepository.save(src);
        return toResponse(src);
    }

    // ── PATCH ─────────────────────────────────────────────────────────────────

    @Transactional
    public SrcVolcanoResponse patch(Integer id, SrcVolcanoRequest request) {
        SrcVolcano src = findOrThrow(id);

        if (request.volcanoId() != null
                && !request.volcanoId().equals(src.getVolcano().getId())) {
            Volcano parent = volcanoRepository.findById(request.volcanoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tour auto-guiado no encontrado con id: " + request.volcanoId()));
            src.setVolcano(parent);
        }

        applyPatch(src, request);
        if (request.selfGuidedTourId() != null) {
            var s = selfGuidedRepository.findById(request.selfGuidedTourId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tour auto-guiado no encontrado con id: " + request.selfGuidedTourId()));
            src.setSelfGuidedTour(s);
        } else {
            src.setSelfGuidedTour(null);
        }
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

    private SrcVolcano findOrThrow(Integer id) {
        return srcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recurso no encontrado con id: " + id));
    }

    private void applyPatch(SrcVolcano s, SrcVolcanoRequest r) {
        if (r.type()        != null) s.setType(r.type());
        if (r.description() != null) s.setDescription(r.description());
        if (r.srcUrl()      != null) s.setSrcUrl(r.srcUrl());
        if (r.appPage()     != null) s.setAppPage(r.appPage());
    }

    private SrcVolcanoResponse toResponse(SrcVolcano s) {
        return new SrcVolcanoResponse(
                s.getId(),
                s.getType(),
                s.getDescription(),
                s.getSrcUrl(),
                s.getAppPage(),
                s.getCreatedAt(),
                s.getSelfGuidedTour() != null ? s.getSelfGuidedTour().getId() : null
        );
    }
}
