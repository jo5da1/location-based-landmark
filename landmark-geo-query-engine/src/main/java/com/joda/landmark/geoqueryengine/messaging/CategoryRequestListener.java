package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CategoryRequestListener {

  private final CategoryService categoryService;

  // this is just for logging purpose. can be removed
  @Value("${landmark.message.category.request}")
  private String categoryRequestQueue;

  public CategoryRequestListener(
      CategoryService categoryService,
      @Value("${landmark.message.category.request}") String categoryRequestQueue) {

    this.categoryService = categoryService;
    this.categoryRequestQueue = categoryRequestQueue;
  }

  @RabbitListener(queues = "${landmark.message.category.request}")
  public void listenCategoryRequestQueue(String request) {
    log.info("received landmark category request on queue [{}]: {}", categoryRequestQueue, request);
    categoryService.process(request);
  }
}
