package com.joda.landmark.geoqueryengine.service.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.joda.landmark.geoqueryengine.AbstractIntegrationTest;
import com.joda.landmark.geoqueryengine.messaging.dto.AmenityCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.AmenitySubCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.service.LandmarkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Sql(
    scripts = {"/sql/extension.sql", "/sql/schema.sql", "/sql/test-data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
class LandmarkServiceIT extends AbstractIntegrationTest {

  @Value("${message.queue.landmark-response}")
  String landmarkResponseQueue;

  @Autowired LandmarkService landmarkService;

  @Autowired RabbitTemplate rabbitTemplate;

  @Autowired ObjectMapper objectMapper;

  @Test
  void shouldReturnNearbyLandmarks() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req-1",
            List.of(AmenityCategory.FOOD_AND_DRINK.name()),
            List.of(AmenitySubCategory.CAFE.name()),
            new Coordinates(57.72264960063176, 11.952931599999998),
            1,
            10,
            5);

    LandmarksResponse response = landmarkService.getLandmarks(request);

    assertThat(response).isNotNull();
    assertThat(response.totalCount()).isGreaterThan(0);

    // this asserts may not a good thing
    assertThat(response.landmarks().get(0).name()).isEqualTo("Cofee Corner");
  }

  @Test
  void shouldSendLandmarksResponseToMessageQueue() {

    LandmarksRequest request =
        new LandmarksRequest(
            "req-1",
            List.of(AmenityCategory.FOOD_AND_DRINK.name()),
            List.of(AmenitySubCategory.CAFE.name()),
            new Coordinates(57.72264960063176, 11.952931599999998),
            1,
            10,
            5);

    landmarkService.process(request);

    Object message = rabbitTemplate.receiveAndConvert(landmarkResponseQueue, 5000);

    System.out.println(message);

    assertThat(message).isNotNull();

    LandmarksResponse response = objectMapper.convertValue(message, LandmarksResponse.class);

    assertThat(response.totalCount()).isEqualTo(1);
    assertThat(response.landmarks().get(0).name()).isEqualTo("Cofee Corner");
  }
}
