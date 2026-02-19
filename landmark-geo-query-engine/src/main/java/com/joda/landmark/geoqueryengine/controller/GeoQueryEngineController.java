package com.joda.landmark.geoqueryengine.controller;

import com.joda.landmark.geoqueryengine.messaging.LandmarkRequestPublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
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

  private final LandmarkRequestPublisher landmarkSearchPublisher;

  public GeoQueryEngineController(LandmarkRequestPublisher landmarkSearchPublisher) {
    this.landmarkSearchPublisher = landmarkSearchPublisher;
  }

  @GetMapping(value = "/")
  public String healthCheck() {
    log.info("Endpoint: [/]");
    return "Welcome to Landmark GeoQueryEngine App";
  }

  @PostMapping(value = "/nearby")
  public ResponseEntity<String> nearby(@RequestBody(required = true) LandmarksRequest request) {
    log.info("Endpoint: [/nearby], request: {}", request);

    landmarkSearchPublisher.sendToLandmarkRequestQueue(request);
    return ResponseEntity.ok("Message sent successfully");
  }
}
