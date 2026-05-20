package com.trheecodes.gtvolcanos.guide.guide_mountain;

import com.trheecodes.gtvolcanos.guide.guide_mountain.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guide-mountain")
@RequiredArgsConstructor
public class GuideMountainController {

    private final GuideMountainService guideMountainService;

    @GetMapping("/guides/{guideId}")
    public ResponseEntity<List<MountainByGuideResponse>> getMountainsByGuide(
            @PathVariable(name = "guideId") Integer guideId) {
        return ResponseEntity.ok(guideMountainService.getMountainsByGuide(guideId));
    }

    @GetMapping("/mountains/{mountainId}")
    public ResponseEntity<List<GuideByMountainResponse>> getGuidesByMountain(
            @PathVariable(name = "mountainId") Integer mountainId) {
        return ResponseEntity.ok(guideMountainService.getGuidesByMountain(mountainId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideMountainResponse> create(
            @Valid @RequestBody GuideMountainRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guideMountainService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideMountainResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody GuideMountainRequest request) {
        return ResponseEntity.ok(guideMountainService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        guideMountainService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
