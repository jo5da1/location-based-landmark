package com.jo5da1.landmark.nearby.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Landmark {

  private String name;
  private LandmarkCategory category;
  private Coordinates coordinates;
  private double distance;

  public Landmark() {}

  public Landmark(
      String name, LandmarkCategory category, Coordinates coordinates, double distance) {
    this.name = name;
    this.category = category;
    this.coordinates = coordinates;
    this.distance = distance;
  }
}
