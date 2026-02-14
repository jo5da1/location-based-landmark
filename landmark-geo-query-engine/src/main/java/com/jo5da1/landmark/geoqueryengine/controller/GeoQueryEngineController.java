package com.jo5da1.landmark.geoqueryengine.controller;

import com.jo5da1.landmark.geoqueryengine.messaging.LandmarkSearchPublisher;
import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/landmark/geoquery")
@Slf4j
public class GeoQueryEngineController {

  private final LandmarkSearchPublisher landmarkSearchPublisher;

  public GeoQueryEngineController(LandmarkSearchPublisher landmarkSearchPublisher) {
    this.landmarkSearchPublisher = landmarkSearchPublisher;
  }

  @GetMapping(value = "/")
  public String healthCheck() {
    log.info("Endpoint: [/]");
    return "Welcome to Landmark GeoQueryEngine App";
  }

  @PostMapping(value = "/nearby")
  public ResponseEntity<String> nearby(@RequestBody(required = true) NearbyRequest message) {
    log.info("Endpoint: [/nearby], message: {}", message);

    landmarkSearchPublisher.sendToLandmarkRequestQueue(message);
    return ResponseEntity.ok("Message sent successfully");
  }
}
