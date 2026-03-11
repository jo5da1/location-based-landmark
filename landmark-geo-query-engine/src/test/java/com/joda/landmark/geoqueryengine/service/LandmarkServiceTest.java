package com.joda.landmark.geoqueryengine.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.joda.landmark.geoqueryengine.messaging.dto.*;
import com.joda.landmark.geoqueryengine.messaging.preprocessor.LandmarksRequestNormalizer;
import com.joda.landmark.geoqueryengine.messaging.publisher.LandmarkResponsePublisher;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPoint;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPointRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

@Slf4j
class LandmarkServiceTest {

  @Mock private LandmarkResponsePublisher landmarkResponsePublisher;

  @Mock private PlanetOsmPointRepository planetOsmPointRepository;

  @Mock private LandmarksRequestNormalizer normalizer;

  @InjectMocks private LandmarkService landmarkService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private LandmarksRequest createValidRequest() {
    return new LandmarksRequest(
        "req-1",
        List.of("FOOD_AND_DRINK"),
        List.of("RESTAURANT"),
        new Coordinates(57.7, 11.9),
        1,
        10,
        500);
  }

  @Test
  void getLandmarks_shouldReturnLandmarks() {

    LandmarksRequest request = createValidRequest();

    when(normalizer.normalize(request)).thenReturn(request);

    PlanetOsmPoint planetOsmPoint = mock(PlanetOsmPoint.class);
    var way = new org.locationtech.jts.geom.Point(null, null, 0);

    when(planetOsmPoint.getName()).thenReturn("Test Restaurant");
    when(planetOsmPoint.getAmenity()).thenReturn("restaurant");

    // mock geometry
    var geometry = mock(org.locationtech.jts.geom.Point.class);
    when(geometry.getY()).thenReturn(57.7);
    when(geometry.getX()).thenReturn(11.9);

    when(planetOsmPoint.getWay()).thenReturn(geometry);

    when(planetOsmPointRepository.findNearbyByAmenity(
            anyList(), anyDouble(), anyDouble(), anyDouble()))
        .thenReturn(List.of(planetOsmPoint));

    LandmarksResponse response = landmarkService.getLandmarks(request);
    log.info("LandmarksResponse : {}", response);

    assertNotNull(response);
    assertEquals(1, response.totalCount());
    assertEquals("Test Restaurant", response.landmarks().get(0).name());
  }

  @Test
  void getLandmarks_shouldThrowIfRequestNull() {
    assertThrows(IllegalArgumentException.class, () -> landmarkService.getLandmarks(null));
  }

  @Test
  void process_shouldHandleValidationErrorsWithoutThrowing() {

    LandmarksRequest invalidRequest =
        new LandmarksRequest("req-2", List.of(), List.of(), null, 1, 10, 500);

    assertDoesNotThrow(() -> landmarkService.process(invalidRequest));

    verifyNoInteractions(landmarkResponsePublisher);
  }

  @Test
  void process_shouldThrowInvalidDataAccessResourceUsageExceptionWhenDbSchemaMissing() {

    LandmarksRequest request = createValidRequest();

    when(planetOsmPointRepository.findNearbyByAmenity(
            anyList(), anyDouble(), anyDouble(), anyDouble()))
        .thenThrow(new InvalidDataAccessResourceUsageException("table missing"));

    assertThrows(AmqpRejectAndDontRequeueException.class, () -> landmarkService.process(request));
  }

  @Test
  void process_shouldPublishResponse() {

    LandmarksRequest request = createValidRequest();

    when(normalizer.normalize(request)).thenReturn(request);

    when(planetOsmPointRepository.findNearbyByAmenity(
            anyList(), anyDouble(), anyDouble(), anyDouble()))
        .thenReturn(List.of());

    landmarkService.process(request);

    verify(landmarkResponsePublisher, times(1)).sendToQueue(any(LandmarksResponse.class));
  }
}
