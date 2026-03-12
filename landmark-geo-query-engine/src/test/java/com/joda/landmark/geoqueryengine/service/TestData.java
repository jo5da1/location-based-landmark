package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.dto.AmenityCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.AmenitySubCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.Landmark;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import java.util.List;

public class TestData {
  private LandmarksResponse testSearchNearby(LandmarksRequest request) {
    List<Landmark> landmarks = testLandmarks();
    return new LandmarksResponse(request.requestId(), landmarks.size(), landmarks);
  }

  private List<Landmark> testLandmarks() {
    return List.of(
        getCentralPark(),
        getJoeCoffee(),
        getFancyRestaurant(),
        getLillaIstanbul(),
        getShahanaGrillAndKok());
  }

  private Landmark getCentralPark() {
    Coordinates coordinates = new Coordinates(40.785091, -73.968285);
    return new Landmark(
        "Central Park",
        AmenityCategory.PUBLIC_FACILITIES,
        AmenitySubCategory.BENCH,
        coordinates,
        320);
  }

  private Landmark getJoeCoffee() {
    Coordinates coordinates = new Coordinates(40.730610, -73.935242);
    return new Landmark(
        "Joe's Coffee", AmenityCategory.FOOD_AND_DRINK, AmenitySubCategory.CAFE, coordinates, 1200);
  }

  private Landmark getFancyRestaurant() {
    Coordinates coordinates = new Coordinates(40.718267, -74.002242);
    return new Landmark(
        "Fancy Restaurant",
        AmenityCategory.FOOD_AND_DRINK,
        AmenitySubCategory.RESTAURANT,
        coordinates,
        500);
  }

  private Landmark getLillaIstanbul() {
    Coordinates coordinates = new Coordinates(57.72495531608793, 11.949546931031295);
    return new Landmark(
        "Lilla Istanbul",
        AmenityCategory.FOOD_AND_DRINK,
        AmenitySubCategory.RESTAURANT,
        coordinates,
        500);
  }

  private Landmark getShahanaGrillAndKok() {
    Coordinates coordinates = new Coordinates(57.72307433992, 11.929296026880738);
    return new Landmark(
        "Shahana Grill & Kök",
        AmenityCategory.FOOD_AND_DRINK,
        AmenitySubCategory.RESTAURANT,
        coordinates,
        500);
  }
}
