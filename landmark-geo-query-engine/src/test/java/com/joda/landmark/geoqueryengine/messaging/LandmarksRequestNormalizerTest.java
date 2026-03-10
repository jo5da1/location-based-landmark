package com.joda.landmark.geoqueryengine.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import com.joda.landmark.geoqueryengine.messaging.dto.Category;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.SubCategory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class LandmarksRequestNormalizerTest {

  private LandmarksRequestNormalizer normalizer;

  @BeforeEach
  void setup() {
    normalizer = new LandmarksRequestNormalizer();
  }

  @Test
  void shouldKeepValidCategoriesAndSubCategories() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            List.of(Category.FOOD_AND_DRINK.name()),
            List.of(SubCategory.RESTAURANT.name()),
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    assertThat(result.categories()).containsExactly(Category.FOOD_AND_DRINK.name());
    assertThat(result.subCategories()).containsExactly(SubCategory.RESTAURANT.name());

    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldPopulateCategoriesFromSubCategories() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            null,
            List.of(SubCategory.RESTAURANT.name()),
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    assertThat(result.categories()).isNotEmpty();
    assertThat(result.categories()).size().isEqualTo(1);
    assertThat(result.categories()).containsExactly(Category.FOOD_AND_DRINK.name());

    assertThat(result.subCategories()).isNotEmpty();
    assertThat(result.subCategories()).size().isEqualTo(1);
    assertThat(result.subCategories()).containsExactly(SubCategory.RESTAURANT.name());

    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldPopulateSubCategoriesFromCategory() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            List.of(Category.FOOD_AND_DRINK.name()),
            null,
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    //
    assertThat(result.categories()).containsExactly(Category.FOOD_AND_DRINK.name());
    assertThat(result.categories().size()).isEqualTo(1);

    assertThat(result.subCategories())
        .containsAll(normalizer.getSubCategories(Category.FOOD_AND_DRINK));
    assertThat(result.subCategories().size())
        .isEqualTo(normalizer.getSubCategories(Category.FOOD_AND_DRINK).size());

    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldPopulateSubCategoriesFromTwoCategory() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            List.of(Category.FOOD_AND_DRINK.name(), Category.ACCOMMODATION.name()),
            null,
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    // assert
    assertThat(result.categories())
        .containsAll(List.of(Category.FOOD_AND_DRINK.name(), Category.ACCOMMODATION.name()));
    assertThat(result.categories().size()).isEqualTo(2);

    assertThat(result.subCategories())
        .containsAll(normalizer.getSubCategories(Category.FOOD_AND_DRINK));
    assertThat(result.subCategories())
        .containsAll(normalizer.getSubCategories(Category.ACCOMMODATION));

    assertThat(result.subCategories().size())
        .isEqualTo(
            normalizer.getSubCategories(Category.FOOD_AND_DRINK).size()
                + normalizer.getSubCategories(Category.ACCOMMODATION).size());

    //
    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldPopulateCategoriesFromTwoSubCategory() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            null,
            List.of(SubCategory.RESTAURANT.name(), SubCategory.SPA.name()),
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    // assert
    assertThat(result.categories())
        .containsAll(List.of(Category.FOOD_AND_DRINK.name(), Category.ACCOMMODATION.name()));
    assertThat(result.categories().size()).isEqualTo(2);

    assertThat(result.subCategories()).contains(SubCategory.RESTAURANT.name());
    assertThat(result.subCategories()).contains(SubCategory.SPA.name());

    assertThat(result.subCategories().size()).isEqualTo(2);

    //
    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldIgnoreInvalidValues() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req1",
            List.of("INVALID"),
            List.of("NOT_REAL"),
            new Coordinates(1.1, 1.2),
            1,
            10,
            1000);

    LandmarksRequest result = normalizer.normalize(request);

    assertThat(result.categories()).isEmpty();
    assertThat(result.subCategories()).isEmpty();

    System.out.println(request);
    System.out.println(result);
  }

  @Test
  void shouldHandleNullLists() {

    LandmarksRequest request =
        new LandmarksRequest("req1", null, null, new Coordinates(1.1, 1.2), 1, 10, 1000);

    LandmarksRequest result = normalizer.normalize(request);

    assertThat(result.categories()).isEmpty();
    assertThat(result.subCategories()).isEmpty();

    System.out.println(request);
    System.out.println(result);
  }
}
