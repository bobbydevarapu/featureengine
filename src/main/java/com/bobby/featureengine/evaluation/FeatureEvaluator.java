package com.bobby.featureengine.evaluation;

import com.bobby.featureengine.model.Feature;
import com.bobby.featureengine.model.FeatureStatus;
import com.bobby.featureengine.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeatureEvaluator {

    private static final Logger log =
            LoggerFactory.getLogger(FeatureEvaluator.class);

    private static final int ROLLOUT_BUCKET_SIZE = 100;

    private final FeatureRepository featureRepository;

    @Cacheable(
            value = "featureFlags",
            key = "#userId + '_' + #featureKey"
    )
    public boolean evaluate(String featureKey, Long userId) {

        log.info("Evaluating feature {} for user {}", featureKey, userId);

        Feature feature = featureRepository.findByFeatureKey(featureKey)
                .orElseThrow(() ->
                        new IllegalArgumentException("Feature not found: " + featureKey)
                );

        FeatureStatus status = feature.getStatus();

        if (status == FeatureStatus.GLOBAL_ON) {
            return true;
        }

        if (status == FeatureStatus.GLOBAL_OFF) {
            return false;
        }

        // CONTROLLED rollout
        int bucket = Math.abs((userId + featureKey).hashCode()) % ROLLOUT_BUCKET_SIZE;

        return bucket < feature.getRolloutPercentage();
    }
}