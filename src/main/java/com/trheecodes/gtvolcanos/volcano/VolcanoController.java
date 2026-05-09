package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.volcano.dto.VolcanoRequest;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
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
@RequestMapping("/volcanoes")
@RequiredArgsConstructor
public class VolcanoController {

    private final VolcanoService volcanoService;

    @GetMapping
    public ResponseEntity<Page<VolcanoSummaryResponse>> getAllVolcanoes(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(volcanoService.getAllVolcanoes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolcanoResponse> getVolcanoById(@PathVariable Integer id) {
        return ResponseEntity.ok(volcanoService.getVolcanoById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<VolcanoResponse> createVolcano(@Valid @RequestBody VolcanoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(volcanoService.createVolcano(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<VolcanoResponse> updateVolcano(
            @PathVariable Integer id,
            @Valid @RequestBody VolcanoRequest request) {
        return ResponseEntity.ok(volcanoService.updateVolcano(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> deleteVolcano(@PathVariable Integer id) {
        volcanoService.deleteVolcano(id);
        return ResponseEntity.noContent().build();
    }
}
