package com.joda.landmark.geoqueryengine.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public abstract class AbstractMessagePublisher<T> {

  private final RabbitTemplate rabbitTemplate;
  private final String queueName;

  public AbstractMessagePublisher(RabbitTemplate rabbitTemplate, String queueName) {
    this.rabbitTemplate = rabbitTemplate;
    this.queueName = queueName;
  }

  public void sendToQueue(T message) {
    log.info("Sending message to queue [{}]: {}", queueName, message);
    try {
      rabbitTemplate.convertAndSend(queueName, message);
    } catch (Exception e) {
      log.error("ERROR: Sending message to queue [{}], ", queueName, e);
    }
  }
}
