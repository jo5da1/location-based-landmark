package com.jo5da1.landmark.nearify.ws.dto;

import lombok.Data;

@Data
public class BoundingBox {
  private LatLng northEast;
  private LatLng southWest;

  @Data
  public static class LatLng {
    private double lat;
    private double lng;
  }
}
