package com.joda.landmark.geoqueryengine.messaging.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record Coordinates(
    @DecimalMin("-90.0") @DecimalMax("90.0") double latitude,
    @DecimalMin("-180.0") @DecimalMax("180.0") double longitude) {}
