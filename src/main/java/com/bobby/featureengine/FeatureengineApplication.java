package com.bobby.featureengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FeatureengineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureengineApplication.class, args);
    }

}