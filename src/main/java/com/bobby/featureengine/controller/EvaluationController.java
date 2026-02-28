package com.bobby.featureengine.controller;

import com.bobby.featureengine.evaluation.FeatureEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EvaluationController {

    private final FeatureEvaluator featureEvaluator;

    @GetMapping("/evaluate")
    public Map<String, Boolean> evaluate(
            @RequestParam String feature,
            @RequestParam Long userId) {

        boolean result = featureEvaluator.evaluate(feature, userId);

        return Map.of("enabled", result);
    }
}