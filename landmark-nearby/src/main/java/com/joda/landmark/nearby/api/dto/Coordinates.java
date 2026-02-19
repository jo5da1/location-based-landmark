package com.joda.landmark.nearby.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class Coordinates {

  private double latitude;
  private double longitude;

  public Coordinates() {}

  public Coordinates(double latitude, double longitude) {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90");
    }
    if (longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180");
    }
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
