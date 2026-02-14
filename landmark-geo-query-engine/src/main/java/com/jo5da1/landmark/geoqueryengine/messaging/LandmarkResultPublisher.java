package com.jo5da1.landmark.geoqueryengine.messaging;

import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LandmarkResultPublisher {
  private final RabbitTemplate rabbitTemplate;
  private final String landmarkResponseQueue;

  public LandmarkResultPublisher(
      RabbitTemplate rabbitTemplate,
      @Value("${landmark.response.queue}") String landmarkResponseQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.landmarkResponseQueue = landmarkResponseQueue;
  }

  public void sendToLandmarkResponseQueue(NearbyResponse message) {
    log.info("sending landmark search response to queue [{}]: {}", landmarkResponseQueue, message);
    try {
      rabbitTemplate.convertAndSend(landmarkResponseQueue, message);
      log.info("landmark search response published successfully");
    } catch (Exception e) {
      log.error(
          "ERROR: sending landmark search response to queue [{}], ", landmarkResponseQueue, e);
    }
  }
}
