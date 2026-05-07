package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
}
