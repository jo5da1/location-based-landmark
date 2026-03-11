package com.joda.landmark.geoqueryengine.messaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageListener<T> {

  private final String queueName;
  private final MessageProcessor<T> processor;

  public MessageListener(String queueName, MessageProcessor<T> processor) {
    this.queueName = queueName;
    this.processor = processor;
  }

  public void handleMessage(T message) {

    log.info("Received message on queue [{}]: {}", queueName, message);

    T processed = processor.preprocess(message);

    log.info("Processed message on queue [{}]: {}", queueName, processed);

    processor.process(processed);
  }
}
