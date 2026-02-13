package com.jo5da1.landmark.nearby.api;

import com.jo5da1.landmark.nearby.api.models.NearByLandmarksRequest;
import com.jo5da1.landmark.nearby.api.models.NearByLandmarksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/landmark")
@Slf4j
public class NearbyController {
  @Autowired NearbyService nearbyService;

  @PostMapping(
      value = "/nearby",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public NearByLandmarksResponse getNearByLandmarks(@RequestBody NearByLandmarksRequest request) {

    log.info("nearby landmarks request - {}", request);

    var response = nearbyService.getNearByLandmarks(request);

    log.info("nearby landmarks response - {}", response);
    return response;
  }
}
