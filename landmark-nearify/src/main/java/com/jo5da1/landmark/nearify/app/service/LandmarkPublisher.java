package com.jo5da1.landmark.nearify.app.service;

import com.jo5da1.landmark.nearify.app.config.NearbyServiceProperties;
import com.jo5da1.landmark.nearify.rest.dto.Category;
import com.jo5da1.landmark.nearify.rest.dto.Coordinates;
import com.jo5da1.landmark.nearify.rest.dto.Landmark;
import com.jo5da1.landmark.nearify.rest.dto.LandmarksRequest;
import com.jo5da1.landmark.nearify.rest.dto.LandmarksResponse;
import com.jo5da1.landmark.nearify.ws.dto.BoundingBox;
import com.jo5da1.landmark.nearify.ws.dto.LandmarkWS;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class LandmarkPublisher {

  private final SimpMessagingTemplate messagingTemplate;
  private final RestTemplate restTemplate;
  private final NearbyServiceProperties nearbyServiceProperties;

  public void publishLandmark(LandmarkWS landmark) {
    System.out.println("publishing landmark: " + landmark);
    messagingTemplate.convertAndSend("/topic/landmarks", landmark);
  }

  public void findAndPublishLandmark(BoundingBox boundingBox) {
    log.info("finding landmark in boundingBox: {}", boundingBox);

    // TODO, radius, category user input
    Coordinates center = getBoundingBoxCenter(boundingBox);

    LandmarksRequest request =
        new LandmarksRequest(
            UUID.randomUUID().toString(),
            center,
            1000,
            List.of(Category.CAFE, Category.PARK),
            0,
            10);
    log.info("landmark request: {}", request);

    try {
      LandmarksResponse response =
          restTemplate.postForObject(
              nearbyServiceProperties.apiUrl(), request, LandmarksResponse.class);

      if (response != null && response.landmarks() != null) {
        response.landmarks().stream().map(this::restToWs).forEach(this::publishLandmark);
      }
    } catch (Exception ex) {
      log.error("Error calling Nearby Api: ", ex);
    }
  }

  private Coordinates getBoundingBoxCenter(BoundingBox bbox) {
    double centerLat = (bbox.getNorthEast().getLat() + bbox.getSouthWest().getLat()) / 2.0;
    double centerLng = (bbox.getNorthEast().getLng() + bbox.getSouthWest().getLng()) / 2.0;
    return new Coordinates(centerLat, centerLng);
  }

  private LandmarkWS restToWs(Landmark restLandmark) {
    return new LandmarkWS(
        restLandmark.name(),
        restLandmark.coordinates().latitude(),
        restLandmark.coordinates().longitude());
  }

  public void sendSampleLandmarks() {
    publishLandmark(new LandmarkWS("Lilla Istanbul", 57.72495531608793, 11.949546931031295));
    publishLandmark(new LandmarkWS("Shahana Grill & Kök", 57.72307433992, 11.929296026880738));
  }
}
