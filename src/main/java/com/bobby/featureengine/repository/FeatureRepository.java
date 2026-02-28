package com.bobby.featureengine.repository;

import com.bobby.featureengine.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByFeatureKey(String featureKey);

}