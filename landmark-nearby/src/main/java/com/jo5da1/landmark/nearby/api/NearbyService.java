package com.jo5da1.landmark.nearby.api;

import com.jo5da1.landmark.nearby.api.models.Coordinates;
import com.jo5da1.landmark.nearby.api.models.Landmark;
import com.jo5da1.landmark.nearby.api.models.LandmarkCategory;
import com.jo5da1.landmark.nearby.api.models.NearByLandmarksRequest;
import com.jo5da1.landmark.nearby.api.models.NearByLandmarksResponse;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class NearbyService {
  public NearByLandmarksResponse getNearByLandmarks(NearByLandmarksRequest request) {

    log.info("nearby landmarks request - {}", request);

    // TODO
    NearByLandmarksResponse response =
        new NearByLandmarksResponse(
            3, Arrays.asList(getCentralPark(), getJoeCoffee(), getFancyRestaurant()));

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);

    log.info("nearby landmarks response - {}", json);

    return response;
  }

  private Landmark getCentralPark() {
    Coordinates coordinates = new Coordinates(40.785091, -73.968285);
    return new Landmark("Central Park", LandmarkCategory.PARK, coordinates, 320);
  }

  private Landmark getJoeCoffee() {

    Landmark landmark = new Landmark();
    landmark.setName("Joe's Coffee");
    landmark.setCategory(LandmarkCategory.CAFE);

    Coordinates coordinates = new Coordinates();
    coordinates.setLatitude(40.730610);
    coordinates.setLongitude(-73.935242);

    landmark.setCoordinates(coordinates);
    landmark.setDistance(1200);

    return landmark;
  }

  private Landmark getFancyRestaurant() {

    Landmark landmark = new Landmark();
    landmark.setName("Fancy Restaurant");
    landmark.setCategory(LandmarkCategory.RESTAURANT);

    Coordinates coordinates = new Coordinates();
    coordinates.setLatitude(40.718267);
    coordinates.setLongitude(-74.002242);

    landmark.setCoordinates(coordinates);
    landmark.setDistance(500);

    return landmark;
  }

  private Landmark getDowntownCafe() {
    Coordinates coordinates = new Coordinates(31.9539, 35.9106);
    return new Landmark("Downtown Cafe", LandmarkCategory.CAFE, coordinates, 250.5);
  }
}
