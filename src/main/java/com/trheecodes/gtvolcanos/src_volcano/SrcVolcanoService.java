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
    public List<SrcVolcanoResponse> getAll(String type) {
        List<SrcVolcano> result;
        if (type != null && !type.isBlank()) {
            result = srcRepository.findByTypeOrderByIdDesc(type);
        } else {
            result = srcRepository.findAllByOrderByIdDesc();
        }
        // Limitar a los últimos 5 registros
        return result.stream()
                .limit(5)
                .map(this::toResponse)
                .toList();
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
                s.getVolcano().getId(),
                s.getVolcano().getName(),
                s.getType(),
                s.getDescription(),
                s.getSrcUrl(),
                s.getAppPage(),
                s.getCreatedAt()
        );
    }
}
