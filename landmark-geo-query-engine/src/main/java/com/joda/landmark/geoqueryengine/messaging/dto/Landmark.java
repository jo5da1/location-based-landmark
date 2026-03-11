package com.joda.landmark.geoqueryengine.messaging.dto;

public record Landmark(
    String name,
    AmenityCategory category,
    AmenitySubCategory subCategory,
    Coordinates coordinates,
    int distance) {}
