package com.jo5da1.landmark.geoqueryengine.messaging.dto;

import java.util.List;

public record NearbyResponse(int totalCount, List<Landmark> landmarks) {}
