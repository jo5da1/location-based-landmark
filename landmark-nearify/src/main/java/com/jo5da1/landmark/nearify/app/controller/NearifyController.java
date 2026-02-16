package com.jo5da1.landmark.nearify.app.controller;

import com.jo5da1.landmark.nearify.app.service.LandmarkPublisher;
import com.jo5da1.landmark.nearify.ws.dto.BoundingBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class NearifyController {

  private final LandmarkPublisher landmarkPublisher;

  public NearifyController(LandmarkPublisher landmarkPublisher) {
    this.landmarkPublisher = landmarkPublisher;
  }

  @GetMapping("/")
  public String home(Model model) {
    return "index";
  }

  @MessageMapping("/topic/landmarks")
  public void getLandmakrs() {
    System.out.println("--");
  }

  @MessageMapping("/bbox")
  public void receiveBoundingBox(BoundingBox bbox) {
    log.info("Received bounding box from UI: {}", bbox);
    // TODO: Call service to fetch landmarks inside this bbox
    landmarkPublisher.findAndPublishLandmark(bbox);
  }
}
