package com.joda.landmark.geoqueryengine.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMessageListener<T> {

  private final MessageProcessor<T> processor;

  private final String queueName;

  public void handleMessage(T message) {

    log.info("Received message on queue [{}]: {}", queueName, message);

    T processed = processor.preprocess(message);

    log.info("Pre Processed message on queue [{}]: {}", queueName, processed);

    processor.process(processed);
  }
}
