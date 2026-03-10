package com.joda.landmark.nearby.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LandmarksRequest {

  private String requestId;
  private Coordinates coordinates;
  private int radius;
  private List<String> categories;
  private List<String> subCategories;
  private Integer page;
  private Integer pageSize;

  public void setRadius(int radius) {
    if (radius < 1 || radius > 50000) {
      throw new IllegalArgumentException("Radius must be between 1 and 50000 meters");
    }
    this.radius = radius;
  }
}
