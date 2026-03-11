package com.joda.landmark.geoqueryengine.messaging;

@FunctionalInterface
public interface MessageProcessor<T> {

  void process(T request);

  default T preprocess(T request) {
    return request;
  }
}
