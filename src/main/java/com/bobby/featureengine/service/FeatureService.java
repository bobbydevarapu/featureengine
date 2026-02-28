package com.bobby.featureengine.service;

import com.bobby.featureengine.model.Feature;
import com.bobby.featureengine.model.FeatureStatus;
import com.bobby.featureengine.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public Feature createFeature(String key, String description) {

        Feature feature = Feature.builder()
                .featureKey(key)
                .description(description)
                .status(FeatureStatus.GLOBAL_OFF)
                .rolloutPercentage(0)
                .build();

        return featureRepository.save(feature);
    }

    @CacheEvict(value = "featureFlags", allEntries = true)
    public Feature enableGlobally(String key) {

        Feature feature = featureRepository.findByFeatureKey(key)
                .orElseThrow(() ->
                        new IllegalArgumentException("Feature not found: " + key)
                );

        feature.setStatus(FeatureStatus.GLOBAL_ON);
        return featureRepository.save(feature);
    }

    @CacheEvict(value = "featureFlags", allEntries = true)
    public Feature disableGlobally(String key) {

        Feature feature = featureRepository.findByFeatureKey(key)
                .orElseThrow(() ->
                        new IllegalArgumentException("Feature not found: " + key)
                );

        feature.setStatus(FeatureStatus.GLOBAL_OFF);
        return featureRepository.save(feature);
    }

    @CacheEvict(value = "featureFlags", allEntries = true)
    public Feature setRollout(String key, Integer percentage) {

        if (percentage == null || percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }

        Feature feature = featureRepository.findByFeatureKey(key)
                .orElseThrow(() ->
                        new IllegalArgumentException("Feature not found: " + key)
                );

        feature.setStatus(FeatureStatus.CONTROLLED);
        feature.setRolloutPercentage(percentage);

        return featureRepository.save(feature);
    }
}