package com.joda.landmark.geoqueryengine.messaging.listener;

import com.joda.landmark.geoqueryengine.messaging.MessageListener;
import com.joda.landmark.geoqueryengine.messaging.processor.CategoryRequestProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestListener {

  private final MessageListener<String> listener;

  public CategoryRequestListener(
      CategoryRequestProcessor processor,
      @Value("${message.queue.category-request}") String queue) {

    this.listener = new MessageListener<>(queue, processor);
  }

  @RabbitListener(queues = "${message.queue.category-request}")
  public void listen(String request) {
    listener.handleMessage(request);
  }
}
