package com.joda.landmark.geoqueryengine.messaging.publisher;

import com.joda.landmark.geoqueryengine.messaging.MessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestPublisher extends MessagePublisher<String> {

  public CategoryRequestPublisher(
      RabbitTemplate rabbitTemplate, @Value("${message.queue.category-request}") String queue) {
    super(rabbitTemplate, queue);
  }
}
