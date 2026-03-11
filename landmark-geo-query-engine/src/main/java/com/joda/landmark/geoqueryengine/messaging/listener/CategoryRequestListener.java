package com.joda.landmark.geoqueryengine.messaging.listener;

import com.joda.landmark.geoqueryengine.messaging.AbstractMessageListener;
import com.joda.landmark.geoqueryengine.messaging.processor.CategoryRequestProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestListener extends AbstractMessageListener<String> {

  public CategoryRequestListener(
      CategoryRequestProcessor processor,
      @Value("${message.queue.category-request}") String queue) {

    super(processor, queue);
  }

  @RabbitListener(queues = "${message.queue.category-request}")
  public void listen(String message) {
    handleMessage(message);
  }
}
