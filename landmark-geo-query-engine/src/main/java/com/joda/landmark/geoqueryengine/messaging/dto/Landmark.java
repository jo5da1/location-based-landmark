package com.joda.landmark.geoqueryengine.messaging.dto;

public record Landmark(
    String name,
    Category category,
    SubCategory subCategory,
    Coordinates coordinates,
    int distance) {}
