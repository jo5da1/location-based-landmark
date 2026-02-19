package com.joda.landmark.geoqueryengine.messaging.dto;

public record Landmark(String name, Category category, Coordinates coordinates, int distance) {}
