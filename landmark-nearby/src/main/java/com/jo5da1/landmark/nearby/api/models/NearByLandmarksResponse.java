package com.jo5da1.landmark.nearby.api.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearByLandmarksResponse {

  private int totalCount;
  private List<Landmark> landmarks;

  public NearByLandmarksResponse() {}

  public NearByLandmarksResponse(int totalCount, List<Landmark> landmarks) {
    this.totalCount = totalCount;
    this.landmarks = landmarks;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public List<Landmark> getLandmarks() {
    return landmarks;
  }

  public void setLandmarks(List<Landmark> landmarks) {
    this.landmarks = landmarks;
  }
}
