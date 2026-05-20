package com.trheecodes.gtvolcanos.mountain;

import com.trheecodes.gtvolcanos.mountain.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mountains")
@RequiredArgsConstructor
public class MountainController {

    private final MountainService mountainService;

    @GetMapping("/volcanoes")
    public ResponseEntity<Page<VolcanoResponse>> getAllVolcanoes(
            @PageableDefault(size = 500, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(mountainService.getAllVolcanoes(pageable));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PopularMountainResponse>> getPopular() {
        return ResponseEntity.ok(mountainService.getPopular());
    }

    @GetMapping("/reto37")
    public ResponseEntity<List<MountainRefResponse>> getReto37() {
        return ResponseEntity.ok(mountainService.getReto37());
    }

    @GetMapping("/former37")
    public ResponseEntity<List<MountainRefResponse>> getFormer37() {
        return ResponseEntity.ok(mountainService.getFormer37());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MountainResponse> getMountainById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(mountainService.getMountainById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<MountainResponse> createMountain(@Valid @RequestBody MountainRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mountainService.createMountain(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<MountainResponse> updateMountain(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody MountainRequest request) {
        return ResponseEntity.ok(mountainService.updateMountain(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> deleteMountain(@PathVariable(name = "id") Integer id) {
        mountainService.deleteMountain(id);
        return ResponseEntity.noContent().build();
    }
}
