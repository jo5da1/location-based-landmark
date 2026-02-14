package com.jo5da1.landmark.nearby.api;

import com.jo5da1.landmark.nearby.api.dto.NearByLandmarksRequest;
import com.jo5da1.landmark.nearby.rabbit.LandmarkSearchPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NearbyService {

  private final LandmarkSearchPublisher rabbitProducer;

  public NearbyService(LandmarkSearchPublisher rabbitProducer) {
    this.rabbitProducer = rabbitProducer;
  }

  public void publishNearbyRequest(NearByLandmarksRequest request) {
    log.info("nearby landmarks request - {}", request);
    rabbitProducer.sendToLandmarkRequestQueue(request);
  }
}
