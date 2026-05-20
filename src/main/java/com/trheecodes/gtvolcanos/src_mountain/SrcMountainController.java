package com.trheecodes.gtvolcanos.src_mountain;

import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainRequest;
import com.trheecodes.gtvolcanos.src_mountain.dto.SrcMountainResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/src-mountains")
@RequiredArgsConstructor
public class SrcMountainController {

    private final SrcMountainService srcMountainService;

    @GetMapping
    public ResponseEntity<List<SrcMountainResponse>> getAll(
            @RequestParam(name = "selfguidedId", required = false) Integer selfguidedId) {
        if (selfguidedId != null) {
            return ResponseEntity.ok(srcMountainService.getBySelfGuidedId(selfguidedId));
        }
        return ResponseEntity.ok(srcMountainService.getAll());
    }

    @GetMapping("/homeposts")
    public ResponseEntity<List<SrcMountainResponse>> getHomePosts() {
        return ResponseEntity.ok(srcMountainService.getHomePosts());
    }

    @GetMapping("/mountains/{mountainId}")
    public ResponseEntity<List<SrcMountainResponse>> getByMountainId(
            @PathVariable(name = "mountainId") Integer mountainId) {
        return ResponseEntity.ok(srcMountainService.getByMountainId(mountainId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SrcMountainResponse> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(srcMountainService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcMountainResponse> create(
            @Valid @RequestBody SrcMountainRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(srcMountainService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<SrcMountainResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody SrcMountainRequest request) {
        return ResponseEntity.ok(srcMountainService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        srcMountainService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
