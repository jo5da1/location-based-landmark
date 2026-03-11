package com.joda.landmark.geoqueryengine.messaging.publisher;

import com.joda.landmark.geoqueryengine.messaging.MessagePublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryResponsePublisher extends MessagePublisher<LandmarkCategoryResponse> {

  public CategoryResponsePublisher(
      RabbitTemplate rabbitTemplate, @Value("${message.queue.category-response}") String queue) {
    super(rabbitTemplate, queue);
  }
}
