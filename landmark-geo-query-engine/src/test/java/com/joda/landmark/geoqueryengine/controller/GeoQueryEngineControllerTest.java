package com.joda.landmark.geoqueryengine.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.joda.landmark.geoqueryengine.messaging.dto.AmenityCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.AmenitySubCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.Coordinates;
import com.joda.landmark.geoqueryengine.messaging.dto.Landmark;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategory;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarkCategoryResponse;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksRequest;
import com.joda.landmark.geoqueryengine.messaging.dto.LandmarksResponse;
import com.joda.landmark.geoqueryengine.messaging.publisher.CategoryRequestPublisher;
import com.joda.landmark.geoqueryengine.messaging.publisher.LandmarkRequestPublisher;
import com.joda.landmark.geoqueryengine.service.CategoryService;
import com.joda.landmark.geoqueryengine.service.LandmarkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(GeoQueryEngineController.class)
@AutoConfigureJson
class GeoQueryEngineControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private LandmarkRequestPublisher landmarkRequestPublisher;

  @MockitoBean private LandmarkService landmarkService;

  @MockitoBean private CategoryRequestPublisher categoryRequestPublisher;

  @MockitoBean private CategoryService categoryService;

  private LandmarksRequest createLandmarksRequest() {
    return new LandmarksRequest(
        "req-1",
        List.of("FOOD_AND_DRINK"),
        List.of("RESTAURANT"),
        new Coordinates(57.7, 11.9),
        1,
        10,
        500);
  }

  private LandmarksResponse createLandmarksResponse() {
    return new LandmarksResponse(
        "req-1",
        1,
        List.of(
            new Landmark(
                "Pizza Place",
                AmenityCategory.FOOD_AND_DRINK,
                AmenitySubCategory.RESTAURANT,
                new Coordinates(57.701, 11.901),
                1)));
  }

  private LandmarkCategoryResponse createLandmarkCategoryResponse() {
    LandmarkCategoryResponse response = new LandmarkCategoryResponse();
    response.setCategories(
        List.of(
            new LandmarkCategory(
                1,
                AmenityCategory.FOOD_AND_DRINK.name(),
                List.of(AmenitySubCategory.RESTAURANT.name(), AmenitySubCategory.CAFE.name()))));

    return response;
  }

  @Test
  void testHealthCheck() throws Exception {
    mockMvc
        .perform(get("/api/landmark/geoquery/"))
        .andExpect(status().isOk())
        .andExpect(content().string("Welcome to Landmark GeoQueryEngine App"));
  }

  @Test
  void testNearbyAsync() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    LandmarksRequest request = createLandmarksRequest();

    mockMvc
        .perform(
            post("/api/landmark/geoquery/nearby")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isAccepted())
        .andExpect(content().string("Request sent for processing"));

    Mockito.verify(landmarkRequestPublisher).sendToQueue(any(LandmarksRequest.class));
  }

  @Test
  void testNearbySync() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    LandmarksRequest request = createLandmarksRequest();
    LandmarksResponse response = createLandmarksResponse();

    Mockito.when(landmarkService.getLandmarks(any(LandmarksRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/landmark/geoquery/nearby-sync")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));

    Mockito.verify(landmarkService).getLandmarks(any(LandmarksRequest.class));
  }

  @Test
  void testCategoryAsync() throws Exception {

    mockMvc
        .perform(get("/api/landmark/geoquery/category"))
        .andExpect(status().isAccepted())
        .andExpect(content().string("Request sent for processing"));

    Mockito.verify(categoryRequestPublisher).sendToQueue(eq("category"));
  }

  @Test
  void testCategorySync() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    LandmarkCategoryResponse response = createLandmarkCategoryResponse();

    Mockito.when(categoryService.getCategory(eq("category-sync"))).thenReturn(response);

    mockMvc
        .perform(get("/api/landmark/geoquery/category-sync").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));

    Mockito.verify(categoryService).getCategory(eq("category-sync"));
  }
}
