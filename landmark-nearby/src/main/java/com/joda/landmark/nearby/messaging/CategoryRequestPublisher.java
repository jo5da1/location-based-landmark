package com.joda.landmark.nearby.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryRequestPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final String categoryRequestQueue;

  public CategoryRequestPublisher(
      RabbitTemplate rabbitTemplate,
      @Value("${message.queue.category-request}") String categoryRequestQueue) {
    this.rabbitTemplate = rabbitTemplate;
    this.categoryRequestQueue = categoryRequestQueue;
  }

  public void sendToCategoryRequestQueue(String request) {
    log.info("sending landmark category request to queue [{}]: {}", categoryRequestQueue, request);
    try {
      rabbitTemplate.convertAndSend(categoryRequestQueue, request);
      log.info("landmark category request sent successfully");
    } catch (Exception e) {
      log.error("ERROR: landmark category request to queue [{}], ", categoryRequestQueue, e);
    }
  }
}
