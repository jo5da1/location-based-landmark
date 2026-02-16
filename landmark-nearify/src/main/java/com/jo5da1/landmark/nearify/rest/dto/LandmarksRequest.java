package com.jo5da1.landmark.nearify.rest.dto;

import java.util.List;

public record LandmarksRequest(
    String requestId,
    Coordinates coordinates,
    int radius,
    List<Category> categories,
    Integer page,
    Integer pageSize) {

  public LandmarksRequest {
    if (radius < 1 || radius > 50000) {
      throw new IllegalArgumentException("Radius must be between 1 and 50000 meters");
    }
  }
}
