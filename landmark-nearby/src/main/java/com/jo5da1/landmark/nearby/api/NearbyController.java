package com.jo5da1.landmark.nearby.api;

import com.jo5da1.landmark.nearby.api.dto.NearByLandmarksRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<String> getNearByLandmarks(@RequestBody NearByLandmarksRequest request) {
    log.info("nearby landmarks request - {}", request);

    nearbyService.publishNearbyRequest(request);

    return ResponseEntity.ok("Message sent successfully");
  }
}
