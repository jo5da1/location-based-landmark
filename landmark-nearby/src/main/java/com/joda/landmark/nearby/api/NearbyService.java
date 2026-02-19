package com.joda.landmark.nearby.api;

import com.joda.landmark.nearby.api.dto.LandmarksRequest;
import com.joda.landmark.nearby.api.dto.LandmarksResponse;
import com.joda.landmark.nearby.messaging.LandmarkResponseListener;
import com.joda.landmark.nearby.messaging.LandmarkSearchPublisher;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NearbyService {

  private final LandmarkSearchPublisher landmarkSearchPublisher;
  private final LandmarkResponseListener landmarkResponseListener;

  public NearbyService(
      LandmarkSearchPublisher landmarkSearchPublisher,
      LandmarkResponseListener landmarkResponseListener) {
    this.landmarkSearchPublisher = landmarkSearchPublisher;
    this.landmarkResponseListener = landmarkResponseListener;
  }

  public void publishNearbyRequest(
      CompletableFuture<LandmarksResponse> future, LandmarksRequest request) {
    landmarkResponseListener.registerFuture(request.getRequestId(), future);
    log.info("nearby landmarks request - {}", request);
    landmarkSearchPublisher.sendToLandmarkRequestQueue(request);
  }

  public void publishNearbyRequest1(LandmarksRequest request) {
    log.info("nearby landmarks request - {}", request);
    landmarkSearchPublisher.sendToLandmarkRequestQueue(request);
  }
}
