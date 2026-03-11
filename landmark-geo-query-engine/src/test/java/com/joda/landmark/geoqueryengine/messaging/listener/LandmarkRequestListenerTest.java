package com.joda.landmark.geoqueryengine.messaging.listener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.processor.LandmarkRequestProcessor;
import java.util.List;
import org.junit.jupiter.api.Test;

class LandmarkRequestListenerTest {

  private LandmarksRequest createValidRequest() {
    return new LandmarksRequest(
        "req-1",
        List.of("FOOD_AND_DRINK"),
        List.of("RESTAURANT"),
        new Coordinates(57.7, 11.9),
        1,
        10,
        500);
  }

  @Test
  void shouldHandleIncomingMessage() {

    LandmarkRequestProcessor processor = mock(LandmarkRequestProcessor.class);

    LandmarksRequest request = createValidRequest();

    when(processor.preprocess(request)).thenReturn(request);

    LandmarkRequestListener listener = new LandmarkRequestListener(processor, "queue");

    listener.listen(request);

    verify(processor).preprocess(request);
    verify(processor).process(request);
  }
}
