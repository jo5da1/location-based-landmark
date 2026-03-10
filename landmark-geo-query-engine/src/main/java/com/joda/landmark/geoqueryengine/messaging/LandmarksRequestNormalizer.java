package com.joda.landmark.geoqueryengine.messaging;

import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.SubCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LandmarksRequestNormalizer {

  private static final List<Category> ALL_CATEGORIES = List.of(Category.values());
  private static final List<SubCategory> ALL_SUBCATEGORIES = List.of(SubCategory.values());

  public LandmarksRequest normalize(LandmarksRequest request) {

    List<String> categories =
        request.categories() != null ? new ArrayList<>(request.categories()) : new ArrayList<>();

    List<String> subCategories =
        request.subCategories() != null
            ? new ArrayList<>(request.subCategories())
            : new ArrayList<>();

    if (isEmpty(categories) && !isEmpty(subCategories)) {
      categories =
          subCategories.stream()
              .map(this::safeValueOf)
              .filter(Objects::nonNull)
              .map(s -> s.getParentCategory().name())
              .distinct()
              .toList();
    }

    if (isEmpty(subCategories) && !isEmpty(categories)) {
      List<String> finalCategories = categories;
      subCategories =
          (ALL_SUBCATEGORIES.stream()
              .filter(sc -> finalCategories.contains(sc.getParentCategory().name()))
              .map(Enum::name)
              .toList());
    }

    return new LandmarksRequest(
        request.requestId(),
        categories,
        subCategories,
        request.coordinates(),
        request.page(),
        request.pageSize(),
        request.radius());
  }

  private boolean isEmpty(List<?> list) {
    return list == null || list.isEmpty();
  }

  private SubCategory safeValueOf(String name) {
    try {
      return SubCategory.valueOf(name);
    } catch (Exception e) {
      return null;
    }
  }
}
