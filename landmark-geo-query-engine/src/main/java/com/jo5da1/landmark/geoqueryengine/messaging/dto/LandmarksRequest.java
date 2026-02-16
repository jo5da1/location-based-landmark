package com.jo5da1.landmark.geoqueryengine.messaging.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LandmarksRequest(
    String requestId,
    @NotEmpty List<Category> categories,
    @NotNull @Valid Coordinates coordinates,
    @Min(0) int page,
    @Min(1) int pageSize,
    @Min(1) int radius) {}
