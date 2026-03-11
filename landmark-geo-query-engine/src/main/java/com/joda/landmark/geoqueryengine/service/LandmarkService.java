package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.dto.AmenityCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.AmenitySubCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.Landmark;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.messaging.preprocessor.LandmarksRequestNormalizer;
import com.joda.landmark.geoqueryengine.messaging.publisher.LandmarkResponsePublisher;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPoint;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPointRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class LandmarkService {

  private final LandmarkResponsePublisher landmarkResponsePublisher;
  private final PlanetOsmPointRepository planetOsmPointRepository;
  private final LandmarksRequestNormalizer normalizer;

  public LandmarksResponse getLandmarks(LandmarksRequest request) {
    request = normalizer.normalize(request);
    return searchNearby(request);
  }

  private LandmarksResponse searchNearby(LandmarksRequest request) {

    validateRequest(request);

    log.info(
        "Processing landmark request: requestId={}, category={}, subCategory={}, radius={}",
        request.requestId(),
        request.categories(),
        request.subCategories(),
        request.radius());

    List<String> amenities = new ArrayList<>();
    amenities.addAll(request.categories().stream().map(String::toLowerCase).toList());
    amenities.addAll(request.subCategories().stream().map(String::toLowerCase).toList());

    log.info(
        "Processing landmark request: requestId={}, amenities={},  radius={}",
        request.requestId(),
        amenities,
        request.radius());

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
      LandmarksResponse landmarksResponse = searchNearby(request);

      landmarkResponsePublisher.sendToQueue(landmarksResponse);

      log.info(
          "Published response for requestId={}, count={}",
          request.requestId(),
          landmarksResponse.totalCount());

    } catch (IllegalArgumentException ex) {
      // Validation failures
      log.error(
          "Invalid landmark request. requestId={}, error={}",
          request != null ? request.requestId() : "null",
          ex.getMessage());
      ex.printStackTrace();

    } catch (InvalidDataAccessResourceUsageException ex) {
      log.error(
          "GeoQuery database error: DB table missing. requestId={}",
          request != null ? request.requestId() : "null");
      ex.printStackTrace();

      throw new AmqpRejectAndDontRequeueException("OSM schema not initialized", ex);

    } catch (Exception ex) {
      // Unexpected failures (DB, messaging, mapping, etc.)
      log.error(
          "Failed to process landmark request. requestId={}",
          request != null ? request.requestId() : "null",
          ex);
      ex.printStackTrace();

      throw ex; // let retry happen for transient failures
    }
  }

  private void validateRequest(LandmarksRequest request) {
    Assert.notNull(request, "LandmarksRequest must not be null");
    Assert.notNull(request.coordinates(), "Coordinates must not be null");
    Assert.notEmpty(request.categories(), "Categories must not be empty");
    Assert.notEmpty(request.subCategories(), "Sub Categories must not be empty");
  }

  private Landmark mapPointToLandmark(PlanetOsmPoint point) {
    log.debug("Converting Point to Landmark: {}", point);
    return new Landmark(
        point.getName(),
        mapStringToAmenityCategory(point.getAmenity()),
        mapStringToAmenitySubCategory(point.getAmenity()),
        new Coordinates(
            point.getWay().getY(), // latitude
            point.getWay().getX() // longitude
            ),
        0 // TODO: compute real distance
        );
  }

  private AmenityCategory mapStringToAmenityCategory(String amenity) {
    if (amenity == null) {
      return null;
    }
    try {
      AmenitySubCategory subCategory = AmenitySubCategory.valueOf(amenity.toUpperCase());
      return subCategory.getParentCategory();
    } catch (IllegalArgumentException ex) {
      log.warn("Unknown amenity from DB: {}", amenity);
      return null;
    }
  }

  private AmenitySubCategory mapStringToAmenitySubCategory(String amenity) {
    if (amenity == null) {
      return null;
    }
    try {
      return AmenitySubCategory.valueOf(amenity.toUpperCase());
    } catch (IllegalArgumentException ex) {
      log.warn("Unknown amenity from DB: {}", amenity);
      return null;
    }
  }

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
