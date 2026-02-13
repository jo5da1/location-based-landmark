package com.jo5da1.landmark.nearby.api.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearByLandmarksRequest {

  private Coordinates coordinates;
  private int radius;
  private List<LandmarkCategory> categories;
  private Integer page;
  private Integer pageSize;

  public NearByLandmarksRequest() {}

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

  public List<LandmarkCategory> getCategories() {
    return categories;
  }

  public void setCategories(List<LandmarkCategory> categories) {
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
