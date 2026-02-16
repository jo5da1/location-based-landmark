package com.jo5da1.landmark.geoqueryengine.messaging.dto;

import java.util.List;

public record LandmarksResponse(String requestId, int totalCount, List<Landmark> landmarks) {}
