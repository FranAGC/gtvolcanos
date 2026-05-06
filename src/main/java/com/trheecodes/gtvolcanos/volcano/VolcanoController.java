package com.trheecodes.gtvolcanos.volcano;

import com.trheecodes.gtvolcanos.volcano.dto.VolcanoResponse;
import com.trheecodes.gtvolcanos.volcano.dto.VolcanoSummaryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volcanoes")
public class VolcanoController {

    private final VolcanoService volcanoService;

    public VolcanoController(VolcanoService volcanoService) {
        this.volcanoService = volcanoService;
    }

    @GetMapping
    public List<VolcanoSummaryResponse> getAllVolcanoes() {
        return volcanoService.getAllVolcanoes();
    }

    @GetMapping("/{id}")
    public VolcanoResponse getVolcanoById(@PathVariable Integer id) {
        return volcanoService.getVolcanoById(id);
    }
}
