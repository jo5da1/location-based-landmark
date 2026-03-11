package com.joda.landmark.geoqueryengine.messaging.publisher;

import com.joda.landmark.geoqueryengine.messaging.AbstractMessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestPublisher extends AbstractMessagePublisher<String> {

  public CategoryRequestPublisher(
      RabbitTemplate rabbitTemplate, @Value("${message.queue.category-request}") String queue) {
    super(rabbitTemplate, queue);
  }
}
