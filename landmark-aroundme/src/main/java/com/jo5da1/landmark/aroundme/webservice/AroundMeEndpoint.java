package com.jo5da1.landmark.aroundme.webservice;

import com.jo5da1.landmark.soap.gen.Coordinates;
import com.jo5da1.landmark.soap.gen.GetAroundMeLandmarksRequest;
import com.jo5da1.landmark.soap.gen.GetAroundMeLandmarksResponse;
import com.jo5da1.landmark.soap.gen.Landmark;
import com.jo5da1.landmark.soap.gen.LandmarkCategory;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Slf4j
public class AroundMeEndpoint {

  private static final String NAMESPACE_URI = "http://jo5da1.com/location-based-landmark-aroundme";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAroundMeLandmarksRequest")
  @ResponsePayload
  public GetAroundMeLandmarksResponse getAroundMeLandmarks(
      @RequestPayload GetAroundMeLandmarksRequest request) {

    log.info("soap request- {}", request);

    GetAroundMeLandmarksResponse response = new GetAroundMeLandmarksResponse();

    // TODO
    response.setTotalCount(3);
    response.getLandmark().add(getCentralPark());
    response.getLandmark().add(getJoeCoffee());
    response.getLandmark().add(getFancyRestaurant());

    log.info("soap response- {}", response);

    return response;
  }

  private Landmark getCentralPark() {

    Landmark landmark = new Landmark();
    landmark.setName("Central Park");
    landmark.setCategory(LandmarkCategory.PARK);

    Coordinates coordinates = new Coordinates();
    coordinates.setLatitude(new BigDecimal(40.785091));
    coordinates.setLongitude(new BigDecimal(-73.968285));

    landmark.setCoordinates(coordinates);
    landmark.setDistance(new BigDecimal(320));

    return landmark;
  }

  private Landmark getJoeCoffee() {

    Landmark landmark = new Landmark();
    landmark.setName("Joe's Coffee");
    landmark.setCategory(LandmarkCategory.CAFE);

    Coordinates coordinates = new Coordinates();
    coordinates.setLatitude(new BigDecimal(40.730610));
    coordinates.setLongitude(new BigDecimal(-73.935242));

    landmark.setCoordinates(coordinates);
    landmark.setDistance(new BigDecimal(1200));

    return landmark;
  }

  private Landmark getFancyRestaurant() {

    Landmark landmark = new Landmark();
    landmark.setName("Fancy Restaurant");
    landmark.setCategory(LandmarkCategory.RESTAURANT);

    Coordinates coordinates = new Coordinates();
    coordinates.setLatitude(new BigDecimal(40.718267));
    coordinates.setLongitude(new BigDecimal(-74.002242));

    landmark.setCoordinates(coordinates);
    landmark.setDistance(new BigDecimal(500));

    return landmark;
  }
}
