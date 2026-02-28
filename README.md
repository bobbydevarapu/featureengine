# Feature Flag Engine with Redis Caching

A lightweight backend system for managing feature flags with support for global toggling, controlled percentage rollout, and Redis-backed caching for high-performance evaluation.

This project demonstrates how production systems manage gradual feature releases safely and efficiently.

## Why This Project Exists

Modern applications do not deploy features blindly. They:

- Roll out features gradually
- Enable features for specific user segments
- Disable features instantly if issues occur
- Avoid database hits on every request

This project simulates a real-world feature flag engine used in scalable systems.

## What It Supports

- Create feature flags dynamically
- Enable or disable features globally
- Controlled rollout using percentage-based distribution
- Deterministic user bucketing (consistent rollout behavior)
- Redis-based caching for fast evaluation
- Automatic cache eviction on feature updates
- Global exception handling
- Layered architecture (Controller -> Service -> Repository)

## System Architecture

Client Request  
-> EvaluationController  
-> FeatureEvaluator  
-> Redis Cache (fast lookup)  
-> (on cache miss) MySQL (persistent storage)

Management Flow  
-> AdminController  
-> FeatureService  
-> FeatureRepository (JPA)  
-> MySQL

## Key Design Decisions

- Caching at evaluation layer for performance
- Cache eviction on feature update to prevent stale reads
- Deterministic hashing for controlled rollout
- Separation of concerns across layers

## Tech Stack

- Spring Boot 4
- Spring Data JPA (Hibernate)
- MySQL
- Redis (Caching)
- SLF4J Logging
- Maven

## Core Concepts Implemented

### 1. Global Toggle

Feature is fully ON or OFF for all users.

### 2. Controlled Rollout

Feature enabled for a percentage of users using deterministic hashing:

```text
bucket = hash(userId + featureKey) % 100
```

Ensures consistent behavior across requests.

### 3. Redis Caching

- @Cacheable on evaluation
- @CacheEvict on feature updates
- Reduces DB load
- Improves response time

## API Endpoints

Create Feature  
POST /admin/features  
Params: key, description

Enable Globally  
PUT /admin/features/{key}/enable

Disable Globally  
PUT /admin/features/{key}/disable

Set Controlled Rollout  
PUT /admin/features/{key}/rollout?percentage=30

Evaluate Feature  
GET /evaluate?feature=new_dashboard&userId=4

Response:

```json
{
  "enabled": true
}
```

## How To Run

1. Start MySQL  
   Ensure database exists: `feature_engine`
2. Start Redis  
   Default port: `6379`
3. Build Project  
   `mvn clean install`
4. Run Application  
   `java -jar target/featureengine-0.0.1-SNAPSHOT.jar`

Application runs on: `http://localhost:8081`

## Future Improvements

- User-specific feature overrides
- Feature grouping support
- Metrics tracking for feature usage
- Dockerized deployment
- AWS deployment configuration

## Project Status

Production-runnable JAR build verified.

Caching, controlled rollout, and persistence layers fully operational.

This version:

- Explains intent
- Shows architectural understanding
- Mentions deterministic hashing (important)
- Sounds engineering-focused
- Not bloated
- Not childish
