package com.joda.landmark.geoqueryengine.messaging.dto;

import java.util.List;

public record LandmarkCategory(int id, String category, List<String> subCategories) {}
