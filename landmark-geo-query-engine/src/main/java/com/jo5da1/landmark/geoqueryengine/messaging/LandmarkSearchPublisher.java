package com.jo5da1.landmark.geoqueryengine.messaging;

import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LandmarkSearchPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final String landmarkRequestQueue;

  public LandmarkSearchPublisher(
      RabbitTemplate rabbitTemplate,
      @Value("${landmark.request.queue}") String landmarkRequestQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.landmarkRequestQueue = landmarkRequestQueue;
  }

  public void sendToLandmarkRequestQueue(NearbyRequest message) {
    log.info("sending landmark search request to queue [{}]: {}", landmarkRequestQueue, message);
    try {
      rabbitTemplate.convertAndSend(landmarkRequestQueue, message);
      log.info("landmark search request sent successfully");
    } catch (Exception e) {
      log.error("ERROR: landmark search request to queue [{}], ", landmarkRequestQueue, e);
    }
  }
}
