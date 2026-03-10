package com.joda.landmark.nearby.api.dto;

import java.util.List;

public record LandmarkCategory(int id, String category, List<String> subCategories) {}
