package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.service.GeoQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LandmarkRequestListener {

  private final GeoQueryService geoQueryService;
  private final LandmarksRequestNormalizer normalizer;

  // this is just for logging purpose. can be removed
  @Value("${landmark.request.queue}")
  private String landmarkRequestQueue;

  @RabbitListener(queues = "${landmark.request.queue}")
  public void listenLandmarkRequestQueue(LandmarksRequest request) {
    log.info("received LandmarksRequest on queue [{}]: {}", landmarkRequestQueue, request);
    LandmarksRequest normalizedRequest = normalizer.normalize(request);
    log.info(
        "received LandmarksRequest on queue [{}]: {}", landmarkRequestQueue, normalizedRequest);

    geoQueryService.process(normalizedRequest);
  }
}
