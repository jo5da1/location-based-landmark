package com.jo5da1.landmark.nearify.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "location-based-landmark.service.nearby")
public record NearbyServiceProperties(String apiUrl) {}
