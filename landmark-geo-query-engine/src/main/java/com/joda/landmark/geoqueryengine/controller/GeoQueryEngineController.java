package com.joda.landmark.geoqueryengine.controller;

import com.joda.landmark.geoqueryengine.messaging.LandmarkRequestPublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.service.GeoQueryService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GeoQueryEngineController {

  private final LandmarkRequestPublisher landmarkSearchPublisher;
  private final GeoQueryService geoQueryService;

  @GetMapping(value = "/")
  public String healthCheck() {
    log.info("Endpoint: [/]");
    return "Welcome to Landmark GeoQueryEngine App";
  }

  /** Async endpoint (via messaging) */
  @PostMapping(value = "/nearby")
  public ResponseEntity<String> nearby(@RequestBody(required = true) LandmarksRequest request) {
    log.info("Endpoint: Async [/nearby], request: {}", request);

    landmarkSearchPublisher.sendToLandmarkRequestQueue(request);
    return ResponseEntity.accepted().body("Request sent for processing");
  }

  /** Sync endpoint (direct DB call) */
  @PostMapping(value = "/nearby-sync")
  public ResponseEntity<LandmarksResponse> nearby1(
      @RequestBody(required = true) LandmarksRequest request) {
    log.info("Endpoint: Sync [/nearby-sync], request: {}", request);

    LandmarksResponse response = geoQueryService.searchNearby(request);
    return ResponseEntity.ok(response);
  }
}
