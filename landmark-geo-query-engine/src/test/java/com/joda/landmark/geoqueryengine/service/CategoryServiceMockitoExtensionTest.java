package com.joda.landmark.geoqueryengine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import com.joda.landmark.geoqueryengine.messaging.publisher.CategoryResponsePublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Modern Mockito Test
@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceMockitoExtensionTest {

  @Mock private CategoryResponsePublisher categoryResponsePublisher;

  @InjectMocks private CategoryService categoryService;

  @Test
  void getCategory_shouldReturnLandmarkCategoryResponse() {
    String request = "category-request";
    LandmarkCategoryResponse response = categoryService.getCategory(request);

    assertNotNull(response);
    assertEquals(request, response.getRequestId());
    assertNotNull(response.getCategories());
    assertFalse(response.getCategories().isEmpty());
  }

  @Test
  void process_shouldPublishResponse() {

    String request = "category-request";

    categoryService.process(request);

    ArgumentCaptor<LandmarkCategoryResponse> captor =
        ArgumentCaptor.forClass(LandmarkCategoryResponse.class);

    verify(categoryResponsePublisher).sendToQueue(captor.capture());

    LandmarkCategoryResponse response = captor.getValue();

    assertEquals(request, response.getRequestId());
    assertNotNull(response.getCategories());
    assertFalse(response.getCategories().isEmpty());
  }
}
