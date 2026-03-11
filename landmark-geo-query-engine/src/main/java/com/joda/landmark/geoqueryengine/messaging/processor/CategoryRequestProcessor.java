package com.joda.landmark.geoqueryengine.messaging.processor;

import com.joda.landmark.geoqueryengine.messaging.MessageProcessor;
import com.joda.landmark.geoqueryengine.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRequestProcessor implements MessageProcessor<String> {

  private final CategoryService categoryService;

  @Override
  public void process(String request) {
    categoryService.process(request);
  }
}
