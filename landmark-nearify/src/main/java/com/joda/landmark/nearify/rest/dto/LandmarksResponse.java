package com.joda.landmark.nearify.rest.dto;

import java.util.List;

public record LandmarksResponse(String requestId, int totalCount, List<Landmark> landmarks) {}
