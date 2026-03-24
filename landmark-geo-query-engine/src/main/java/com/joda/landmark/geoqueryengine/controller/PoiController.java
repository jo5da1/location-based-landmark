package com.joda.landmark.geoqueryengine.controller;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.PointOfInterest;
import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestLog;
import com.joda.landmark.geoqueryengine.service.PointOfInterestService;
import com.joda.landmark.geoqueryengine.service.PointOfInterestUpdateService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/landmark/geoquery/poi")
@Slf4j
@RequiredArgsConstructor
public class PoiController {

  private final PointOfInterestService pointOfInterestService;
  private final PointOfInterestUpdateService pointOfInterestUpdateService;

  @GetMapping(value = "/")
  public ResponseEntity<ApiResponse> tableInUse() {

    log.info("Endpoint: [/api/landmark/geoquery/poi/]");
    PointOfInterestLog pointOfInterestLog = pointOfInterestService.getPointOfInterestInUse();

    return ResponseEntity.ok(
        new ApiResponse("Welcome to Landmark POI Controller, Active POI: ", pointOfInterestLog));
  }

  @GetMapping(value = "/log")
  public ResponseEntity<ApiResponse> getPointOfInterestLog() {

    log.info("Endpoint: [/api/landmark/geoquery/poi/log]");
    List<PointOfInterestLog> listPoiLog = pointOfInterestService.getPointOfInterestLog();

    return ResponseEntity.ok(new ApiResponse("PointOfInterest Log", listPoiLog));
  }

  @PostMapping(value = "/swap")
  public ResponseEntity<ApiResponse> swapPointOfInterest(
      @RequestBody(required = true) String request) {

    log.info("Endpoint: [/api/landmark/geoquery/poi/swap]: {}", request);
    pointOfInterestUpdateService.swapActiveTable(request);
    PointOfInterestLog pointOfInterestLog = pointOfInterestService.getPointOfInterestInUse();

    return ResponseEntity.ok(
        new ApiResponse("PointOfInterest Swap Done! Active POI", pointOfInterestLog));
  }

  @PostMapping(value = "/get")
  public ResponseEntity<ApiResponse> getPointOfInterest(
      @RequestBody(required = true) LandmarksRequest request) {

    log.info("Endpoint: [/api/landmark/geoquery/poi/get] : {}", request);
    List<PointOfInterest> listPointOfInterest =
        pointOfInterestService.findPOIWithinDistance(
            request.coordinates().longitude(), request.coordinates().latitude(), request.radius());

    return ResponseEntity.ok(
        new ApiResponse("PointOfInterest with in radius: ", listPointOfInterest));
  }

  @PostMapping(value = "/initiate-import")
  public ResponseEntity<ApiResponse> initiateImport() {

    log.info("Endpoint: [/api/landmark/geoquery/poi/initiate-import]");
    var osmImportLog = pointOfInterestUpdateService.initiateImport();

    return ResponseEntity.ok(new ApiResponse("Initiate Import: ", osmImportLog));
  }

  @PostMapping(value = "/trigger-import")
  public ResponseEntity<ApiResponse> triggerImport(@RequestBody(required = true) String importId) {

    log.info("Endpoint: [/api/landmark/geoquery/poi/trigger-import]: {}", importId);
    pointOfInterestUpdateService.triggerImport(UUID.fromString(importId));

    return ResponseEntity.ok(new ApiResponse("Trigger Import", "Done!"));
  }
}
