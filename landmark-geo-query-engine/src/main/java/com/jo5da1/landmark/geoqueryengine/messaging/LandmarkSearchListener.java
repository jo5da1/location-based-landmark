package com.jo5da1.landmark.geoqueryengine.messaging;

import com.jo5da1.landmark.geoqueryengine.messaging.dto.NearbyRequest;
import com.jo5da1.landmark.geoqueryengine.service.GeoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LandmarkSearchListener {

  private final GeoQueryService geoQueryService;

  // this is just for logging purpose. can be removed
  @Value("${landmark.request.queue}")
  private String landmarkRequestQueue;

  public LandmarkSearchListener(
      GeoQueryService geoQueryService,
      @Value("${landmark.request.queue}") String landmarkRequestQueue) {
    this.geoQueryService = geoQueryService;

    this.landmarkRequestQueue = landmarkRequestQueue;
  }

  @RabbitListener(queues = "${landmark.request.queue}")
  public void listenLandmarkRequestQueue(NearbyRequest message) {
    log.info("received landmark search request on queue [{}]: {}", landmarkRequestQueue, message);
    geoQueryService.process(message);
  }
}
