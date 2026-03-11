package com.joda.landmark.geoqueryengine.messaging.listener;

import static org.mockito.Mockito.*;

import com.joda.landmark.geoqueryengine.messaging.processor.CategoryRequestProcessor;
import org.junit.jupiter.api.Test;

class CategoryRequestListenerTest {

  @Test
  void shouldHandleIncomingMessage() {

    CategoryRequestProcessor processor = mock(CategoryRequestProcessor.class);

    when(processor.preprocess("msg")).thenReturn("pre-processed-msg");

    CategoryRequestListener listener = new CategoryRequestListener(processor, "queue");

    listener.listen("msg");

    verify(processor).preprocess("msg");
    verify(processor).process("pre-processed-msg");
  }
}
