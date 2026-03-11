package com.joda.landmark.geoqueryengine.messaging.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.preprocessor.LandmarksRequestNormalizer;
import com.joda.landmark.geoqueryengine.service.LandmarkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LandmarkRequestProcessorTest {

  @Mock private LandmarkService landmarkService;

  @Mock private LandmarksRequestNormalizer normalizer;

  @InjectMocks private LandmarkRequestProcessor processor;

  private LandmarksRequest createRequest() {
    return new LandmarksRequest(
        "req-1", List.of(), List.of("RESTAURANT"), new Coordinates(57.7, 11.9), 1, 10, 500);
  }

  private LandmarksRequest createNormalisedRequest() {
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
  void shouldNormalizeRequestInPreprocess() {
    // given
    LandmarksRequest request = createRequest();
    LandmarksRequest normalized = createNormalisedRequest();

    when(normalizer.normalize(request)).thenReturn(normalized);

    // when
    LandmarksRequest result = processor.preprocess(request);

    // then
    assertEquals(normalized, result);
    verify(normalizer).normalize(request);
  }

  @Test
  void shouldDelegateProcessingToLandmarkService() {
    // given
    LandmarksRequest request = createRequest();

    // when
    processor.process(request);

    // then
    verify(landmarkService).process(request);
  }

  @Test
  void shouldNormalizeThenProcess() {

    LandmarksRequest request = createRequest();
    LandmarksRequest normalized = createNormalisedRequest();

    when(normalizer.normalize(request)).thenReturn(normalized);

    LandmarksRequest result = processor.preprocess(request);
    processor.process(result);

    verify(normalizer).normalize(request);
    verify(landmarkService).process(normalized);
  }
}
