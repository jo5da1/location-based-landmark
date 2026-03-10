package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryResponsePublisher {

  private final RabbitTemplate rabbitTemplate;
  private final String categoryResponseQueue;

  public CategoryResponsePublisher(
      RabbitTemplate rabbitTemplate,
      @Value("${landmark.message.category.response}") String categoryResponseQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.categoryResponseQueue = categoryResponseQueue;
  }

  public void sendToCategoryResponseQueue(LandmarkCategoryResponse response) {
    log.info(
        "sending landmark category response to queue [{}]: {}", categoryResponseQueue, response);
    try {
      rabbitTemplate.convertAndSend(categoryResponseQueue, response);
      log.info("landmark category response published successfully");
    } catch (Exception e) {
      log.error(
          "ERROR: sending landmark category response to queue [{}], ", categoryResponseQueue, e);
    }
  }
}
