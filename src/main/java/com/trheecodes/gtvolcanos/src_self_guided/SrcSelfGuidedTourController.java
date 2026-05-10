package com.trheecodes.gtvolcanos.src_self_guided;

import com.trheecodes.gtvolcanos.src_self_guided.dto.SrcSelfGuidedTourRequest;
import com.trheecodes.gtvolcanos.src_self_guided.dto.SrcSelfGuidedTourResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/src-self-guided")
@RequiredArgsConstructor
public class SrcSelfGuidedTourController {

    private final SrcSelfGuidedTourService srcSelfGuidedTourService;

    /** Público — todos los recursos de un tour auto-guiado. */
    @GetMapping("/self-guided/{selfGuidedId}")
    public ResponseEntity<List<SrcSelfGuidedTourResponse>> getBySelfGuidedTourId(
            @PathVariable Integer selfGuidedId) {
        return ResponseEntity.ok(srcSelfGuidedTourService.getBySelfGuidedTourId(selfGuidedId));
    }

    /** Público — detalle de un recurso por su id. */
    @GetMapping("/{id}")
    public ResponseEntity<SrcSelfGuidedTourResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(srcSelfGuidedTourService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcSelfGuidedTourResponse> create(
            @Valid @RequestBody SrcSelfGuidedTourRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(srcSelfGuidedTourService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcSelfGuidedTourResponse> patch(
            @PathVariable Integer id,
            @Valid @RequestBody SrcSelfGuidedTourRequest request) {
        return ResponseEntity.ok(srcSelfGuidedTourService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        srcSelfGuidedTourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
