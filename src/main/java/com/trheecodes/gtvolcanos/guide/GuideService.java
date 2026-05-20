package com.trheecodes.gtvolcanos.guide;

import com.trheecodes.gtvolcanos.guide.dto.GuideRequest;
import com.trheecodes.gtvolcanos.guide.dto.GuideResponse;
import com.trheecodes.gtvolcanos.guide.guide_mountain.GuideMountain;
import com.trheecodes.gtvolcanos.mountain.Mountain;
import com.trheecodes.gtvolcanos.mountain.MountainRepository;
import com.trheecodes.gtvolcanos.shared.exception.ConflictException;
import com.trheecodes.gtvolcanos.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final MountainRepository mountainRepository;

    // ── GET all (paginated) ───────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<GuideResponse> getAll(Pageable pageable) {
        return guideRepository.findAll(pageable).map(this::toResponse);
    }

    // ── GET actives (public) ──────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Page<GuideResponse> getActives(Pageable pageable) {
        return guideRepository.findByActiveTrue(pageable).map(this::toResponse);
    }

    // ── GET by id ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public GuideResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Transactional
    public GuideResponse create(GuideRequest request) {
        if (request.email() != null && guideRepository.existsByEmail(request.email())) {
            throw new ConflictException("Ya existe un guía con el email: " + request.email());
        }
        if (request.licenseNumber() != null && guideRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new ConflictException("Ya existe un guía con la licencia: " + request.licenseNumber());
        }

        Guide guide = new Guide();
        applyPatch(guide, request);
        guide.setCreatedAt(OffsetDateTime.now());
        guide.setUpdatedAt(OffsetDateTime.now());
        guideRepository.save(guide);
        return toResponse(guide);
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @Transactional
    public GuideResponse patch(Integer id, GuideRequest request) {
        Guide guide = findOrThrow(id);

        // Unique-field conflict checks (only if the value is changing)
        if (request.email() != null
                && !request.email().equalsIgnoreCase(guide.getEmail())
                && guideRepository.existsByEmail(request.email())) {
            throw new ConflictException("Ya existe un guía con el email: " + request.email());
        }
        if (request.licenseNumber() != null
                && !request.licenseNumber().equals(guide.getLicenseNumber())
                && guideRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new ConflictException("Ya existe un guía con la licencia: " + request.licenseNumber());
        }

        applyPatch(guide, request);
        guide.setUpdatedAt(OffsetDateTime.now());
        return toResponse(guide);
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void delete(Integer id) {
        if (!guideRepository.existsById(id)) {
            throw new ResourceNotFoundException("Guía no encontrado con id: " + id);
        }
        guideRepository.deleteById(id);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private Guide findOrThrow(Integer id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guía no encontrado con id: " + id));
    }

    /** Applies only non-null fields — works for both POST and PATCH. */
    private void applyPatch(Guide g, GuideRequest r) {
        if (r.firstName() != null)
            g.setFirstName(r.firstName());
        if (r.lastName() != null)
            g.setLastName(r.lastName());
        if (r.phone() != null)
            g.setPhone(r.phone());
        if (r.email() != null)
            g.setEmail(r.email());
        if (r.nationality() != null)
            g.setNationality(r.nationality());
        if (r.spokenLanguages() != null)
            g.setSpokenLanguages(r.spokenLanguages().toArray(new String[0]));
        if (r.profilePhotoUrl() != null)
            g.setProfilePhotoUrl(r.profilePhotoUrl());
        if (r.bio() != null)
            g.setBio(r.bio());
        if (r.facebook() != null)
            g.setFacebook(r.facebook());
        if (r.instagram() != null)
            g.setInstagram(r.instagram());
        if (r.whatsapp() != null)
            g.setWhatsapp(r.whatsapp());
        if (r.licenseNumber() != null)
            g.setLicenseNumber(r.licenseNumber());
        if (r.certified() != null)
            g.setCertified(r.certified());
        if (r.experienceYears() != null)
            g.setExperienceYears(r.experienceYears());
        if (r.pricePerDayUsd() != null)
            g.setPricePerDayUsd(r.pricePerDayUsd());
        if (r.maxGroupSize() != null)
            g.setMaxGroupSize(r.maxGroupSize());
        if (r.active() != null)
            g.setActive(r.active());

        if (r.mountainIds() != null) {
            g.getGuideMountains().clear();
            r.mountainIds().forEach(vid -> {
                Mountain v = mountainRepository.findById(vid)
                        .orElseThrow(() -> new ResourceNotFoundException("Montaña no encontrada con id: " + vid));
                GuideMountain gv = new GuideMountain();
                gv.setGuide(g);
                gv.setMountain(v);
                gv.setIsPrimary(false);
                g.getGuideMountains().add(gv);
            });
        }
    }

    private GuideResponse toResponse(Guide g) {
        List<GuideResponse.MountainRef> refs = g.getGuideMountains().stream()
                .map(gv -> new GuideResponse.MountainRef(
                        gv.getMountain().getId(),
                        gv.getMountain().getName(),
                        gv.getMountain().getCountryId()))
                .toList();

        return new GuideResponse(
                g.getId(),
                g.getFirstName(),
                g.getLastName(),
                g.getPhone(),
                g.getEmail(),
                g.getNationality(),
                g.getSpokenLanguages() != null ? List.of(g.getSpokenLanguages()) : List.of(),
                g.getProfilePhotoUrl(),
                g.getBio(),
                g.getFacebook(),
                g.getInstagram(),
                g.getWhatsapp(),
                g.getLicenseNumber(),
                g.getCertified(),
                g.getExperienceYears(),
                g.getPricePerDayUsd(),
                g.getMaxGroupSize(),
                g.getActive(),
                refs);
    }
}
