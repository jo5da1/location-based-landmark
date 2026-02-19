package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.LandmarkResponsePublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.Landmark;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoQueryService {

  private final LandmarkResponsePublisher landmarkResultPublisher;

  public GeoQueryService(LandmarkResponsePublisher landmarkResultPublisher) {
    this.landmarkResultPublisher = landmarkResultPublisher;
  }

  public void process(LandmarksRequest request) {
    List<Landmark> landmarks =
        List.of(
            getCentralPark(),
            getJoeCoffee(),
            getFancyRestaurant(),
            getLillaIstanbul(),
            getShahanaGrillAndKok());

    // Todo from DBms

    LandmarksResponse nearbyResponse =
        new LandmarksResponse(request.requestId(), landmarks.size(), landmarks);
    landmarkResultPublisher.sendToLandmarkResponseQueue(nearbyResponse);
  }

  private Landmark getCentralPark() {
    Coordinates coordinates = new Coordinates(40.785091, -73.968285);
    return new Landmark("Central Park", Category.PARK, coordinates, 320);
  }

  private Landmark getJoeCoffee() {
    Coordinates coordinates = new Coordinates(40.730610, -73.935242);
    return new Landmark("Joe's Coffee", Category.CAFE, coordinates, 1200);
  }

  private Landmark getFancyRestaurant() {
    Coordinates coordinates = new Coordinates(40.718267, -74.002242);
    return new Landmark("Fancy Restaurant", Category.RESTAURANT, coordinates, 500);
  }

  private Landmark getLillaIstanbul() {
    Coordinates coordinates = new Coordinates(57.72495531608793, 11.949546931031295);
    return new Landmark("Lilla Istanbul", Category.RESTAURANT, coordinates, 500);
  }

  private Landmark getShahanaGrillAndKok() {
    Coordinates coordinates = new Coordinates(57.72307433992, 11.929296026880738);
    return new Landmark("Shahana Grill & Kök", Category.RESTAURANT, coordinates, 500);
  }
}
