package com.joda.landmark.geoqueryengine.controller;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.messaging.publisher.CategoryRequestPublisher;
import com.joda.landmark.geoqueryengine.messaging.publisher.LandmarkRequestPublisher;
import com.joda.landmark.geoqueryengine.service.CategoryService;
import com.joda.landmark.geoqueryengine.service.LandmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

  private final LandmarkRequestPublisher landmarkRequestPublisher;
  private final LandmarkService landmarkService;

  private final CategoryRequestPublisher categoryRequestPublisher;
  private final CategoryService categoryService;

  @GetMapping(value = "/")
  public String healthCheck() {
    log.info("Endpoint: [/]");
    return "Welcome to Landmark GeoQueryEngine App";
  }

  /** Async endpoint (via messaging) */
  @PostMapping(value = "/nearby")
  public ResponseEntity<String> nearby(@RequestBody(required = true) LandmarksRequest request) {
    log.info("Endpoint: Async [/nearby], request: {}", request);

    landmarkRequestPublisher.sendToQueue(request);
    return ResponseEntity.accepted().body("Request sent for processing");
  }

  /** Sync endpoint (direct DB call) */
  @PostMapping(value = "/nearby-sync")
  public ResponseEntity<LandmarksResponse> nearbySync(
      @RequestBody(required = true) LandmarksRequest request) {
    log.info("Endpoint: Sync [/nearby-sync], request: {}", request);

    LandmarksResponse response = landmarkService.getLandmarks(request);
    return ResponseEntity.ok(response);
  }

  /** Async endpoint (via messaging) */
  @GetMapping(value = "/category")
  public ResponseEntity<String> category() {
    log.info("Endpoint: Async [/category]");

    categoryRequestPublisher.sendToQueue("category");
    return ResponseEntity.accepted().body("Request sent for processing");
  }

  @GetMapping(value = "/category-sync", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LandmarkCategoryResponse> categorySync() {

    log.info("Endpoint: Sync [/category-sync] ");

    LandmarkCategoryResponse landmarkCategoryResponse =
        categoryService.getCategory("category-sync");

    log.info("landmark category response: {}", landmarkCategoryResponse);

    return ResponseEntity.ok(landmarkCategoryResponse);
  }
}
