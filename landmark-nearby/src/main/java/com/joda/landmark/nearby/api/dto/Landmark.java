package com.joda.landmark.nearby.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Landmark {

  private String name;
  private Category category;
  private Coordinates coordinates;
  private double distance;

  public Landmark() {}

  public Landmark(String name, Category category, Coordinates coordinates, double distance) {
    this.name = name;
    this.category = category;
    this.coordinates = coordinates;
    this.distance = distance;
  }
}
