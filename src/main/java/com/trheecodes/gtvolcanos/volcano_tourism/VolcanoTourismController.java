package com.trheecodes.gtvolcanos.volcano_tourism;

import com.trheecodes.gtvolcanos.volcano_tourism.dto.VolcanoTourismRequest;
import com.trheecodes.gtvolcanos.volcano_tourism.dto.VolcanoTourismResponse;
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
@RequestMapping("/volcano-tourism")
@RequiredArgsConstructor
public class VolcanoTourismController {

    private final VolcanoTourismService tourismService;

    @GetMapping
    public ResponseEntity<Page<VolcanoTourismResponse>> getAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(tourismService.getAll(pageable));
    }

    @GetMapping("/{volcanoId}")
    public ResponseEntity<VolcanoTourismResponse> getByVolcanoId(@PathVariable(name = "volcanoId") Integer volcanoId) {
        return ResponseEntity.ok(tourismService.getByVolcanoId(volcanoId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<VolcanoTourismResponse> create(
            @Valid @RequestBody VolcanoTourismRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourismService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<VolcanoTourismResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody VolcanoTourismRequest request) {
        return ResponseEntity.ok(tourismService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        tourismService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
