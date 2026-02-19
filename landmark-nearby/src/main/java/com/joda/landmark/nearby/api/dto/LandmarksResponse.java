package com.joda.landmark.nearby.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LandmarksResponse {
  private String requestId;

  private int totalCount;
  private List<Landmark> landmarks;

  public LandmarksResponse() {}

  public LandmarksResponse(String requestId, int totalCount, List<Landmark> landmarks) {
    this.requestId = requestId;
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
