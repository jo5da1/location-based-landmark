package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import com.joda.landmark.geoqueryengine.messaging.dto.SubCategory;
import com.joda.landmark.geoqueryengine.messaging.publisher.CategoryResponsePublisher;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryResponsePublisher categoryResponsePublisher;

  public LandmarkCategoryResponse getCategory(String request) {
    List<LandmarkCategory> categories =
        Arrays.stream(Category.values())
            .map(
                cat ->
                    new LandmarkCategory(
                        cat.ordinal() + 1,
                        cat.toString(),
                        Arrays.stream(SubCategory.values())
                            .filter(subCat -> subCat.getParentCategory() == cat)
                            .map(Enum::name)
                            .toList()))
            .toList();

    LandmarkCategoryResponse landmarkCategoryResponse = new LandmarkCategoryResponse();
    landmarkCategoryResponse.setRequestId(request);
    landmarkCategoryResponse.setCategories(categories);

    return landmarkCategoryResponse;
  }

  public void process(String request) {
    categoryResponsePublisher.sendToQueue(getCategory(request));
  }
}
