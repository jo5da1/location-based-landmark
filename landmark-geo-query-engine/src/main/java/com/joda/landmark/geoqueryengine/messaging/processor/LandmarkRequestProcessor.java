package com.joda.landmark.geoqueryengine.messaging.processor;

import com.joda.landmark.geoqueryengine.messaging.MessageProcessor;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.preprocessor.LandmarksRequestNormalizer;
import com.joda.landmark.geoqueryengine.service.GeoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LandmarkRequestProcessor implements MessageProcessor<LandmarksRequest> {

  private final GeoQueryService geoQueryService;
  private final LandmarksRequestNormalizer normalizer;

  @Override
  public LandmarksRequest preprocess(LandmarksRequest request) {
    return normalizer.normalize(request);
  }

  @Override
  public void process(LandmarksRequest request) {
    geoQueryService.process(request);
  }
}
