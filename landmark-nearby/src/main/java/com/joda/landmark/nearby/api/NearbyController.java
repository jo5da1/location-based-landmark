package com.joda.landmark.nearby.api;

import com.joda.landmark.nearby.api.dto.LandmarkCategoryResponse;
import com.joda.landmark.nearby.api.dto.LandmarksRequest;
import com.joda.landmark.nearby.api.dto.LandmarksResponse;
import com.joda.landmark.nearby.service.CategoryService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NearbyController {
  private final NearbyService nearbyService;
  private final CategoryService categoryService;

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
  @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<LandmarkCategoryResponse> category() {
    log.info("nearby landmark options request ");

    // CompletableFuture to hold the result
    CompletableFuture<LandmarkCategoryResponse> future = new CompletableFuture<>();
    categoryService.publishCategoryRequest(future, "category-request");

    return future;
  }
}
