package com.jo5da1.landmark.nearby.api.dto;

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
  private List<Category> categories;
  private Integer page;
  private Integer pageSize;

  public LandmarksRequest() {}

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    if (radius < 1 || radius > 50000) {
      throw new IllegalArgumentException("Radius must be between 1 and 50000 meters");
    }
    this.radius = radius;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
