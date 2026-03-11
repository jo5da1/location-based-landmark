package com.joda.landmark.geoqueryengine.messaging.preprocessor;

import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.SubCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LandmarksRequestNormalizer {

  public static final List<Category> ALL_CATEGORIES = List.of(Category.values());
  public static final List<SubCategory> ALL_SUBCATEGORIES = List.of(SubCategory.values());

  public LandmarksRequest normalize(LandmarksRequest request) {

    log.info("LandmarksRequestNormalizer");

    List<String> categories =
        request.categories() != null ? new ArrayList<>(request.categories()) : new ArrayList<>();

    List<String> subCategories =
        request.subCategories() != null
            ? new ArrayList<>(request.subCategories())
            : new ArrayList<>();

    // category correction
    List<String> correctedCategories = new ArrayList<>();
    List<String> correctedSubCategories = new ArrayList<>();

    for (String value : categories) {
      if (isSubCategory(value)) {
        correctedSubCategories.add(value);
      } else if (isCategory(value)) {
        correctedCategories.add(value);
      }
    }

    for (String value : subCategories) {
      if (isSubCategory(value)) {
        correctedSubCategories.add(value);
      } else if (isCategory(value)) {
        correctedCategories.add(value);
      }
    }

    categories = correctedCategories.stream().distinct().toList();
    subCategories = correctedSubCategories.stream().distinct().toList();

    // normalization
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

  public List<String> getSubCategories(Category category) {
    return LandmarksRequestNormalizer.ALL_SUBCATEGORIES.stream()
        .filter(s -> s.getParentCategory() == category)
        .map(Enum::name)
        .toList();
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

  private boolean isCategory(String name) {
    try {
      Category.valueOf(name);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isSubCategory(String name) {
    try {
      SubCategory.valueOf(name);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
