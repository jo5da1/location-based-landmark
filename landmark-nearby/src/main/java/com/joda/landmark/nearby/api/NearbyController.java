package com.joda.landmark.nearby.api;

import com.joda.landmark.nearby.api.dto.Category;
import com.joda.landmark.nearby.api.dto.LandmarkOption;
import com.joda.landmark.nearby.api.dto.LandmarkOptionsResponse;
import com.joda.landmark.nearby.api.dto.LandmarksRequest;
import com.joda.landmark.nearby.api.dto.LandmarksResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/landmark")
@Slf4j
public class NearbyController {
  private final NearbyService nearbyService;

  public NearbyController(NearbyService nearbyService) {
    this.nearbyService = nearbyService;
  }

  @GetMapping(value = "/")
  public String healthCheck() {
    log.info("Endpoint: [/]");
    return "Welcome to Landmark NearBy App";
  }

  @PostMapping(
      value = "/nearby",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<LandmarksResponse> nearby(@RequestBody LandmarksRequest request) {
    log.info("nearby landmarks request - {}", request);

    // CompletableFuture to hold the result
    CompletableFuture<LandmarksResponse> future = new CompletableFuture<>();
    nearbyService.publishNearbyRequest(future, request);

    return future;
  }

  @CrossOrigin(origins = "http://localhost:3001")
  @GetMapping(value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<LandmarkOptionsResponse> options() {
    log.info("nearby landmark options request ");

    List<LandmarkOption> optionList =
        List.of(
            new LandmarkOption(1, Category.RESTAURANT.toString()),
            new LandmarkOption(2, Category.CAFE.toString()),
            new LandmarkOption(3, Category.MUSEUM.toString()));
    // CompletableFuture to hold the result
    CompletableFuture<LandmarkOptionsResponse> future = new CompletableFuture<>();
    LandmarkOptionsResponse landmarkOptionsResponse = new LandmarkOptionsResponse();
    landmarkOptionsResponse.setOptions(optionList);

    future.complete(landmarkOptionsResponse);

    log.info("nearby landmark options response: {}", optionList);

    return future;
  }
}
