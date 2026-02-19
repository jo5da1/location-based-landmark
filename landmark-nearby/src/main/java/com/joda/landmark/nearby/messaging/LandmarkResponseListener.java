package com.joda.landmark.nearby.messaging;

import com.joda.landmark.nearby.api.dto.LandmarksResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LandmarkResponseListener {

  private final Map<String, CompletableFuture<LandmarksResponse>> futureMap =
      new ConcurrentHashMap<>();

  private final String landmarkResponseQueue;

  public LandmarkResponseListener(
      @Value("${landmark.response.queue}") String landmarkResponseQueue) {
    this.landmarkResponseQueue = landmarkResponseQueue;
  }

  public void registerFuture(String requestId, CompletableFuture<LandmarksResponse> future) {
    futureMap.put(requestId, future);
  }

  @RabbitListener(queues = "${landmark.response.queue}")
  public void listenLandmarkResponseQueue(LandmarksResponse response) {
    log.info("received landmark response on queue [{}]: {}", landmarkResponseQueue, response);
    if (response == null || response.getRequestId() == null) {
      // do nothing
      log.warn("Received response with null requestId, ignoring");
      return;
    }
    CompletableFuture<LandmarksResponse> future = futureMap.remove(response.getRequestId());
    if (future != null) {
      future.complete(response);
    } else {
      log.warn("No future found for requestId {}", response.getRequestId());
    }
  }
}
