package com.joda.landmark.geoqueryengine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import com.joda.landmark.geoqueryengine.messaging.publisher.CategoryResponsePublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// Old Mockito Test
@Slf4j
class CategoryServiceOpenMocksTest {
  @Mock private CategoryResponsePublisher categoryResponsePublisher;

  @InjectMocks private CategoryService categoryService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCategory_shouldReturnLandmarkCategoryResponse() {
    String request = "category-request";
    LandmarkCategoryResponse response = categoryService.getCategory(request);

    log.info("LandmarkCategoryResponse : {}", response);

    assertNotNull(response);
    assertEquals(request, response.getRequestId());
    assertNotNull(response.getCategories());
    assertFalse(response.getCategories().isEmpty());
  }

  @Test
  void process_shouldPublishResponse() {

    String request = "category-request";

    categoryService.process(request);

    verify(categoryResponsePublisher, times(1)).sendToQueue(any(LandmarkCategoryResponse.class));
  }
}
