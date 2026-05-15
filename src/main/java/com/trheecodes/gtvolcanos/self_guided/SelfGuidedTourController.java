package com.trheecodes.gtvolcanos.self_guided;

import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourRequest;
import com.trheecodes.gtvolcanos.self_guided.dto.SelfGuidedTourResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/self-guided")
@RequiredArgsConstructor
public class SelfGuidedTourController {

    private final SelfGuidedTourService selfGuidedTourService;

    /** Público — todos los tours de un volcán. */
    @GetMapping("/volcano/{volcanoId}")
    public ResponseEntity<List<SelfGuidedTourResponse>> getByVolcanoId(
            @PathVariable(name = "volcanoId") Integer volcanoId) {
        return ResponseEntity.ok(selfGuidedTourService.getByVolcanoId(volcanoId));
    }

    /** Público — detalle de un tour por su id. */
    @GetMapping("/{id}")
    public ResponseEntity<SelfGuidedTourResponse> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(selfGuidedTourService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SelfGuidedTourResponse> create(
            @Valid @RequestBody SelfGuidedTourRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(selfGuidedTourService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SelfGuidedTourResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody SelfGuidedTourRequest request) {
        return ResponseEntity.ok(selfGuidedTourService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        selfGuidedTourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
