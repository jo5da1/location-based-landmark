package com.joda.landmark.geoqueryengine.messaging.publisher;

import com.joda.landmark.geoqueryengine.messaging.MessagePublisher;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LandmarkResponsePublisher extends MessagePublisher<LandmarksResponse> {

  public LandmarkResponsePublisher(
      RabbitTemplate rabbitTemplate, @Value("${message.queue.landmark-response}") String queue) {
    super(rabbitTemplate, queue);
  }
}
