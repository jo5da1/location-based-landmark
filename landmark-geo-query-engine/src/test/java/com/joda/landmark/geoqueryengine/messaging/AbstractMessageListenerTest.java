package com.joda.landmark.geoqueryengine.messaging;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class AbstractMessageListenerTest {

  @Test
  void shouldPreprocessAndProcessMessage() {

    MessageProcessor<String> processor = mock(MessageProcessor.class);

    when(processor.preprocess("msg")).thenReturn("pre-processed-msg");

    AbstractMessageListener<String> listener =
        new AbstractMessageListener<>(processor, "test-queue") {};

    listener.handleMessage("msg");

    verify(processor).preprocess("msg");
    verify(processor).process("pre-processed-msg");
  }
}
