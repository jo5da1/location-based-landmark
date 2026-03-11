package com.joda.landmark.geoqueryengine.messaging.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.joda.landmark.geoqueryengine.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryRequestProcessorTest {

  @Mock private CategoryService categoryService;

  @InjectMocks private CategoryRequestProcessor processor;

  @Test
  void shouldDelegateProcessingToCategoryService() {

    String request = "category";

    processor.process(request);

    verify(categoryService).process(request);
  }

  @Test
  void shouldCallCategoryService() {

    CategoryService service = mock(CategoryService.class);

    CategoryRequestProcessor processor = new CategoryRequestProcessor(service);

    processor.process("request");

    verify(service).process("request");
  }
}
