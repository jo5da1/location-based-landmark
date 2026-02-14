package com.jo5da1.landmark.geoqueryengine.service;

import com.jo5da1.landmark.geoqueryengine.messaging.LandmarkResultPublisher;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.Category;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.Landmark;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyRequest;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoQueryService {

  private final LandmarkResultPublisher landmarkResultPublisher;

  public GeoQueryService(LandmarkResultPublisher landmarkResultPublisher) {
    this.landmarkResultPublisher = landmarkResultPublisher;
  }

  public void process(NearbyRequest message) {
    List<Landmark> landmarks = List.of(getCentralPark(), getJoeCoffee(), getFancyRestaurant());

    NearbyResponse nearbyResponse = new NearbyResponse(landmarks.size(), landmarks);
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
}
