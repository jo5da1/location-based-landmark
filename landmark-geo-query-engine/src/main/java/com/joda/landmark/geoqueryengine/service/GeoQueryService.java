package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.LandmarkResponsePublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.Landmark;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPoint;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPointRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoQueryService {

  private final LandmarkResponsePublisher landmarkResultPublisher;
  private final PlanetOsmPointRepository planetOsmPointRepository;

  public LandmarksResponse searchNearby(LandmarksRequest request) {

    validateRequest(request);

    log.info(
        "Processing landmark request: requestId={}, category={}, radius={}",
        request.requestId(),
        request.categories(),
        request.radius());

    List<String> amenities = request.categories().stream().map(this::mapCategoryToAmenity).toList();

    List<PlanetOsmPoint> dbPoints =
        planetOsmPointRepository.findNearbyByAmenity(
            amenities,
            request.coordinates().latitude(),
            request.coordinates().longitude(),
            request.radius());

    log.info("Found {} PlanetOsmPoint in DB", dbPoints.size());

    List<Landmark> landmarks = dbPoints.stream().map(this::mapPointToLandmark).toList();

    return new LandmarksResponse(request.requestId(), landmarks.size(), landmarks);
  }

  public void process(LandmarksRequest request) {

    try {
      LandmarksResponse nearbyResponse = searchNearby(request);

      landmarkResultPublisher.sendToLandmarkResponseQueue(nearbyResponse);

      log.info(
          "Published response for requestId={}, count={}",
          request.requestId(),
          nearbyResponse.totalCount());
    } catch (IllegalArgumentException ex) {
      // Validation failures
      log.warn(
          "Invalid landmark request. requestId={}, error={}",
          request != null ? request.requestId() : "null",
          ex.getMessage());
      ex.printStackTrace();

    } catch (Exception ex) {
      // Unexpected failures (DB, messaging, mapping, etc.)
      log.error(
          "Failed to process landmark request. requestId={}, category={}, radius={}",
          request != null ? request.requestId() : "null",
          request != null ? request.categories() : "null",
          request != null ? request.radius() : "null",
          ex);
      ex.printStackTrace();
      throw ex;
    }
  }

  private void validateRequest(LandmarksRequest request) {
    Assert.notNull(request, "LandmarksRequest must not be null");
    Assert.notNull(request.coordinates(), "Coordinates must not be null");
    Assert.notEmpty(request.categories(), "Categories must not be empty");
  }

  private Landmark mapPointToLandmark(PlanetOsmPoint point) {
    log.info("Converting Point to Landmark: {}", point);
    return new Landmark(
        point.getName(),
        mapAmenityToCategory(point.getAmenity()),
        new Coordinates(
            point.getWay().getY(), // latitude
            point.getWay().getX() // longitude
            ),
        0 // TODO: compute real distance
        );
  }

  private Category mapAmenityToCategory(String amenity) {
    if (amenity == null) {
      return null;
    }

    try {
      return Category.valueOf(amenity.toUpperCase());
    } catch (IllegalArgumentException ex) {
      log.warn("Unknown amenity from DB: {}", amenity);
      return null;
    }
  }

  private String mapCategoryToAmenity(Category category) {
    if (category == null) {
      return "cafe"; // TODO: fallback default
    }
    return category.name().toLowerCase();
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
