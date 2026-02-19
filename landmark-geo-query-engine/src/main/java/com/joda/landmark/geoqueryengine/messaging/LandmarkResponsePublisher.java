package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LandmarkResponsePublisher {
  private final RabbitTemplate rabbitTemplate;
  private final String landmarkResponseQueue;

  public LandmarkResponsePublisher(
      RabbitTemplate rabbitTemplate,
      @Value("${landmark.response.queue}") String landmarkResponseQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.landmarkResponseQueue = landmarkResponseQueue;
  }

  public void sendToLandmarkResponseQueue(LandmarksResponse response) {
    log.info("sending landmark search response to queue [{}]: {}", landmarkResponseQueue, response);
    try {
      rabbitTemplate.convertAndSend(landmarkResponseQueue, response);
      log.info("landmark search response published successfully");
    } catch (Exception e) {
      log.error(
          "ERROR: sending landmark search response to queue [{}], ", landmarkResponseQueue, e);
    }
  }
}
