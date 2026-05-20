package com.trheecodes.gtvolcanos.tourism_info;

import com.trheecodes.gtvolcanos.tourism_info.dto.TourismInfoRequest;
import com.trheecodes.gtvolcanos.tourism_info.dto.TourismInfoResponse;
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
@RequestMapping("/tourism-info")
@RequiredArgsConstructor
public class TourismInfoController {

    private final TourismInfoService tourismService;

    @GetMapping
    public ResponseEntity<Page<TourismInfoResponse>> getAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(tourismService.getAll(pageable));
    }

    @GetMapping("/{mountainId}")
    public ResponseEntity<TourismInfoResponse> getByMountainId(@PathVariable(name = "mountainId") Integer mountainId) {
        return ResponseEntity.ok(tourismService.getByMountainId(mountainId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<TourismInfoResponse> create(
            @Valid @RequestBody TourismInfoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourismService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<TourismInfoResponse> patch(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody TourismInfoRequest request) {
        return ResponseEntity.ok(tourismService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        tourismService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
