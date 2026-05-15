package com.trheecodes.gtvolcanos.guide;

import com.trheecodes.gtvolcanos.guide.dto.GuideRequest;
import com.trheecodes.gtvolcanos.guide.dto.GuideResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guides")
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    /** Público — lista paginada de todos los guías. */
    @GetMapping
    public ResponseEntity<Page<GuideResponse>> getAll(
            @PageableDefault(size = 20, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(guideService.getAll(pageable));
    }

    /** Público — solo guías con active = true. */
    @GetMapping("/actives")
    public ResponseEntity<Page<GuideResponse>> getActives(
            @PageableDefault(size = 20, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(guideService.getActives(pageable));
    }

    /** Público — detalle de un guía por id. */
    @GetMapping("/{id}")
    public ResponseEntity<GuideResponse> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(guideService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideResponse> create(@Valid @RequestBody GuideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guideService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<GuideResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody GuideRequest request) {
        return ResponseEntity.ok(guideService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        guideService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
