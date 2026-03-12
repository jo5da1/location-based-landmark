package com.joda.landmark.nearify.rest.dto;

public record Landmark(
    String name, String category, String subCategory, Coordinates coordinates, double distance) {}
