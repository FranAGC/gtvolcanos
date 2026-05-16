package com.trheecodes.gtvolcanos.src_volcano;

import com.trheecodes.gtvolcanos.src_volcano.dto.SrcVolcanoRequest;
import com.trheecodes.gtvolcanos.src_volcano.dto.SrcVolcanoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/src-volcanoes")
@RequiredArgsConstructor
public class SrcVolcanoController {

    private final SrcVolcanoService srcVolcanoService;

    /** Público — todos los recursos de volcanes con filtro opcional por type. */
    @GetMapping
    public ResponseEntity<List<SrcVolcanoResponse>> getAll(
            @RequestParam(name = "selfguidedId", required = false) Integer selfguidedId) {
        if (selfguidedId != null) {
            return ResponseEntity.ok(srcVolcanoService.getBySelfGuidedId(selfguidedId));
        }
        return ResponseEntity.ok(srcVolcanoService.getAll());
    }

    /** Público — últimos 4 posts de home (type=post y sin self_guided_tour_id) */
    @GetMapping("/homeposts")
    public ResponseEntity<List<SrcVolcanoResponse>> getHomePosts() {
        return ResponseEntity.ok(srcVolcanoService.getHomePosts());
    }

    /** Público — todos los recursos de un volcán. */
    @GetMapping("/volcanoes/{volcanoId}")
    public ResponseEntity<List<SrcVolcanoResponse>> getByVolcanoId(
            @PathVariable(name = "volcanoId") Integer volcanoId) {
        return ResponseEntity.ok(srcVolcanoService.getByVolcanoId(volcanoId));
    }

    /** Público — detalle de un recurso por su id. */
    @GetMapping("/{id}")
    public ResponseEntity<SrcVolcanoResponse> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(srcVolcanoService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcVolcanoResponse> create(
            @Valid @RequestBody SrcVolcanoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(srcVolcanoService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcVolcanoResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody SrcVolcanoRequest request) {
        return ResponseEntity.ok(srcVolcanoService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        srcVolcanoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
