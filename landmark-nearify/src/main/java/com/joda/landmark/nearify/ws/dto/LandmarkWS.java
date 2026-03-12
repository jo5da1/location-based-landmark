package com.joda.landmark.nearify.ws.dto;

public record LandmarkWS(
    String name, String category, String subCategory, double latitude, double longitude) {}
