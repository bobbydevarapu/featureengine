package com.bobby.featureengine.controller;

import com.bobby.featureengine.model.Feature;
import com.bobby.featureengine.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FeatureService featureService;

    @PostMapping("/features")
    public Feature createFeature(
            @RequestParam String key,
            @RequestParam String description) {

        return featureService.createFeature(key, description);
    }
    @PutMapping("/features/{key}/enable")
    public Feature enable(@PathVariable String key) {
        return featureService.enableGlobally(key);
    }

    @PutMapping("/features/{key}/disable")
    public Feature disable(@PathVariable String key) {
        return featureService.disableGlobally(key);
    }

    @PutMapping("/features/{key}/rollout")
    public Feature rollout(@PathVariable String key,
                           @RequestParam Integer percentage) {
        return featureService.setRollout(key, percentage);
    }
}