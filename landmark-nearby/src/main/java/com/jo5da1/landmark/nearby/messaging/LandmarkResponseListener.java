package com.jo5da1.landmark.nearby.messaging;

import com.jo5da1.landmark.nearby.api.dto.NearByLandmarksResponse;
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

  private final Map<String, CompletableFuture<NearByLandmarksResponse>> futureMap =
      new ConcurrentHashMap<>();

  private final String landmarkResponseQueue;

  public LandmarkResponseListener(
      @Value("${landmark.response.queue}") String landmarkResponseQueue) {
    this.landmarkResponseQueue = landmarkResponseQueue;
  }

  public void registerFuture(String requestId, CompletableFuture<NearByLandmarksResponse> future) {
    futureMap.put(requestId, future);
  }

  @RabbitListener(queues = "${landmark.response.queue}")
  public void listenLandmarkResponseQueue(NearByLandmarksResponse response) {
    log.info("received landmark response on queue [{}]: {}", landmarkResponseQueue, response);
    CompletableFuture<NearByLandmarksResponse> future = futureMap.remove(response.getRequestId());
    if (future != null) {
      future.complete(response);
    }
  }
}
