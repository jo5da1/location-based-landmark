package com.joda.landmark.geoqueryengine.messaging.listener;

import com.joda.landmark.geoqueryengine.messaging.AbstractMessageListener;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.processor.LandmarkRequestProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LandmarkRequestListener extends AbstractMessageListener<LandmarksRequest> {

  public LandmarkRequestListener(
      LandmarkRequestProcessor processor,
      @Value("${message.queue.landmark-request}") String queue) {

    super(processor, queue);
  }

  @RabbitListener(queues = "${message.queue.landmark-request}")
  public void listen(LandmarksRequest message) {
    handleMessage(message);
  }
}
