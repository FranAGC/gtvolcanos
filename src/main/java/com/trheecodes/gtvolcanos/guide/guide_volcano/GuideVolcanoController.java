package com.trheecodes.gtvolcanos.guide.guide_volcano;

import com.trheecodes.gtvolcanos.guide.guide_volcano.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guide-volcano")
@RequiredArgsConstructor
public class GuideVolcanoController {

    private final GuideVolcanoService guideVolcanoService;

    /** Público — volcanes asignados a un guía. */
    @GetMapping("/guides/{guideId}")
    public ResponseEntity<List<VolcanoByGuideResponse>> getVolcanoesByGuide(
            @PathVariable Integer guideId) {
        return ResponseEntity.ok(guideVolcanoService.getVolcanoesByGuide(guideId));
    }

    /** Público — guías asignados a un volcán. */
    @GetMapping("/volcanoes/{volcanoId}")
    public ResponseEntity<List<GuideByVolcanoResponse>> getGuidesByVolcano(
            @PathVariable Integer volcanoId) {
        return ResponseEntity.ok(guideVolcanoService.getGuidesByVolcano(volcanoId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideVolcanoResponse> create(
            @Valid @RequestBody GuideVolcanoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guideVolcanoService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideVolcanoResponse> patch(
            @PathVariable Integer id,
            @Valid @RequestBody GuideVolcanoRequest request) {
        return ResponseEntity.ok(guideVolcanoService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        guideVolcanoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
