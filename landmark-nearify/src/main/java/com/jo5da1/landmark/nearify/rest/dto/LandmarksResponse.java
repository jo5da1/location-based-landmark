package com.jo5da1.landmark.nearify.rest.dto;

import java.util.List;

public record LandmarksResponse(int totalCount, List<Landmark> landmarks) {}
