package com.joda.landmark.nearby.service;

import com.joda.landmark.nearby.api.dto.LandmarkCategoryResponse;
import com.joda.landmark.nearby.messaging.CategoryRequestPublisher;
import com.joda.landmark.nearby.messaging.CategoryResponseListener;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRequestPublisher categoryRequestPublisher;
  private final CategoryResponseListener categoryResponseListener;

  public void publishCategoryRequest(
      CompletableFuture<LandmarkCategoryResponse> future, String request) {
    categoryResponseListener.registerFuture(request, future);
    log.info("Category request - {}", request);
    categoryRequestPublisher.sendToCategoryRequestQueue(request);
  }
}
