package com.joda.landmark.geoqueryengine.messaging.publisher;

import com.joda.landmark.geoqueryengine.messaging.AbstractMessagePublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LandmarkRequestPublisher extends AbstractMessagePublisher<LandmarksRequest> {

  public LandmarkRequestPublisher(
      RabbitTemplate rabbitTemplate, @Value("${message.queue.landmark-request}") String queue) {
    super(rabbitTemplate, queue);
  }
}
