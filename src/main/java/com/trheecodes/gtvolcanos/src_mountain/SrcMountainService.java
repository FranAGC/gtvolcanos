package com.trheecodes.gtvolcanos.src_mountain;

import com.trheecodes.gtvolcanos.mountain.Mountain;
import com.trheecodes.gtvolcanos.mountain.MountainRepository;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainImageResponse;
import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainRequest;
import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainResponse;
import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SrcMountainService {

    private final SrcMountainRepository srcRepository;
    private final MountainRepository    mountainRepository;
    private final com.trheecodes.gtvolcanos.self_guided.SelfGuidedTourRepository selfGuidedRepository;

    @Transactional(readOnly = true)
    public List<SrcMountainResponse> getByMountainId(Integer mountainId) {
        if (!mountainRepository.existsById(mountainId)) {
            throw new ResourceNotFoundException(
                    "Montaña no encontrada con id: " + mountainId);
        }
        return srcRepository.findByMountainId(mountainId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SrcMountainResponse> getAll() {
        List<SrcMountain> result = srcRepository.findAllByOrderByIdDesc();
        return result.stream()
                .limit(5)
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SrcMountainResponse> getBySelfGuidedId(Integer selfguidedId) {
        List<SrcMountain> result = srcRepository.findBySelfGuidedTour_Id(selfguidedId);
        return result.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SrcMountainResponse> getHomePosts() {
        List<SrcMountain> result = srcRepository.findTop4ByTypeAndSelfGuidedTourIsNullOrderByIdDesc("post");
        return result.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SrcMountainImageResponse> getImagesByMountainId(Integer mountainId) {
        if (!mountainRepository.existsById(mountainId)) {
            throw new ResourceNotFoundException("Montaña no encontrada con id: " + mountainId);
        }
        return srcRepository.findByMountainIdAndType(mountainId, "imagen")
                .stream().map(this::toImageResponse).toList();
    }

    @Transactional(readOnly = true)
    public SrcMountainResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<SrcMountainSummaryResponse> getByMountainIdWithoutPostAndImagen(Integer mountainId) {
        if (!mountainRepository.existsById(mountainId)) {
            throw new ResourceNotFoundException("MontaÃ±a no encontrada con id: " + mountainId);
        }
        return srcRepository.findByMountainIdAndTypeNotIn(mountainId, List.of("post", "imagen"))
                .stream().map(this::toSummaryResponse).toList();
    }

    @Transactional
    public SrcMountainResponse create(SrcMountainRequest request) {
        Mountain parent = mountainRepository.findById(request.mountainId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Montaña no encontrada con id: " + request.mountainId()));

        SrcMountain src = new SrcMountain();
        src.setMountain(parent);
        applyPatch(src, request);
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

    @Transactional
    public SrcMountainResponse patch(Integer id, SrcMountainRequest request) {
        SrcMountain src = findOrThrow(id);

        if (request.mountainId() != null
                && !request.mountainId().equals(src.getMountain().getId())) {
            Mountain parent = mountainRepository.findById(request.mountainId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Montaña no encontrada con id: " + request.mountainId()));
            src.setMountain(parent);
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

    @Transactional
    public void delete(Integer id) {
        if (!srcRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso no encontrado con id: " + id);
        }
        srcRepository.deleteById(id);
    }

    private SrcMountain findOrThrow(Integer id) {
        return srcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recurso no encontrado con id: " + id));
    }

    private void applyPatch(SrcMountain s, SrcMountainRequest r) {
        if (r.type()           != null) s.setType(r.type());
        if (r.description()    != null) s.setDescription(r.description());
        if (r.srcUrl()         != null) s.setSrcUrl(r.srcUrl());
        if (r.appPage()        != null) s.setAppPage(r.appPage());
        if (r.additionalInfo() != null) s.setAdditionalInfo(r.additionalInfo());
    }

    private SrcMountainResponse toResponse(SrcMountain s) {
        return new SrcMountainResponse(
                s.getId(),
                s.getType(),
                s.getDescription(),
                s.getSrcUrl(),
                s.getAppPage(),
                s.getSelfGuidedTour() != null ? s.getSelfGuidedTour().getId() : null,
                s.getAdditionalInfo()
        );
    }

    private SrcMountainImageResponse toImageResponse(SrcMountain s) {
        return new SrcMountainImageResponse(
                s.getDescription(),
                s.getSrcUrl(),
                s.getAdditionalInfo()
        );
    }

    private SrcMountainSummaryResponse toSummaryResponse(SrcMountain s) {
        return new SrcMountainSummaryResponse(
                s.getId(),
                s.getDescription(),
                s.getSrcUrl(),
                s.getAdditionalInfo()
        );
    }
}
