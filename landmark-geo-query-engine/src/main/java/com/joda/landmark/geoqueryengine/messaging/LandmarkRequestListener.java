package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.service.GeoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LandmarkRequestListener {

  private final GeoQueryService geoQueryService;

  // this is just for logging purpose. can be removed
  @Value("${landmark.request.queue}")
  private String landmarkRequestQueue;

  public LandmarkRequestListener(
      GeoQueryService geoQueryService,
      @Value("${landmark.request.queue}") String landmarkRequestQueue) {
    this.geoQueryService = geoQueryService;

    this.landmarkRequestQueue = landmarkRequestQueue;
  }

  @RabbitListener(queues = "${landmark.request.queue}")
  public void listenLandmarkRequestQueue(LandmarksRequest request) {
    log.info("received landmark search request on queue [{}]: {}", landmarkRequestQueue, request);
    geoQueryService.process(request);
  }
}
