package com.joda.landmark.nearify.rest.dto;

public record Landmark(String name, Category category, Coordinates coordinates, double distance) {}
