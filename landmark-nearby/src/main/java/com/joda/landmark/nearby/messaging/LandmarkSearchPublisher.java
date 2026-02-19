package com.joda.landmark.nearby.messaging;

import com.joda.landmark.nearby.api.dto.LandmarksRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

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

  public void sendToLandmarkRequestQueue(LandmarksRequest request) {
    log.info("sending landmark search request to queue [{}]: {}", landmarkRequestQueue, request);

    ObjectMapper mapper = new ObjectMapper();
    String jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
    log.info("MQ Message: [{}]", jsonMessage);

    try {
      rabbitTemplate.convertAndSend(landmarkRequestQueue, request);
      log.info("landmark search request published successfully");
    } catch (Exception e) {
      log.error("ERROR: sending landmark search request to queue [{}], ", landmarkRequestQueue, e);
    }
  }
}
