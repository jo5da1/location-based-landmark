package com.joda.landmark.nearby.messaging;

import com.joda.landmark.nearby.api.dto.LandmarkCategoryResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryResponseListener {

  private final String categoryResponseQueue;
  private final Map<String, CompletableFuture<LandmarkCategoryResponse>> futureMap =
      new ConcurrentHashMap<>();

  public CategoryResponseListener(
      @Value("${message.queue.category-response}") String categoryResponseQueue) {

    this.categoryResponseQueue = categoryResponseQueue;
  }

  public void registerFuture(String requestId, CompletableFuture<LandmarkCategoryResponse> future) {
    futureMap.put(requestId, future);
  }

  @RabbitListener(queues = "${message.queue.category-response}")
  public void listenCategoryResponseQueue(LandmarkCategoryResponse response) {
    log.info("received category response on queue [{}]: {}", categoryResponseQueue, response);
    if (response == null || response.getRequestId() == null) {
      // do nothing
      log.warn("Received response with null requestId, ignoring");
      return;
    }
    CompletableFuture<LandmarkCategoryResponse> future = futureMap.remove(response.getRequestId());
    if (future != null) {
      future.complete(response);
    } else {
      log.warn("No future found for requestId {}", response.getRequestId());
    }
  }
}
