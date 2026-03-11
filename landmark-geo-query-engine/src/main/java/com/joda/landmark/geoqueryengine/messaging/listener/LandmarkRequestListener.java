package com.joda.landmark.geoqueryengine.messaging.listener;

import com.joda.landmark.geoqueryengine.messaging.MessageListener;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.processor.LandmarkRequestProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LandmarkRequestListener {

  private final MessageListener<LandmarksRequest> listener;

  public LandmarkRequestListener(
      LandmarkRequestProcessor processor,
      @Value("${message.queue.landmark-request}") String queue) {

    this.listener = new MessageListener<>(queue, processor);
  }

  @RabbitListener(queues = "${message.queue.landmark-request}")
  public void listen(LandmarksRequest request) {
    listener.handleMessage(request);
  }
}
