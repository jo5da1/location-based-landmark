package com.joda.landmark.nearby.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Landmark {

  private String name;
  private String category;
  private String subCategory;
  private Coordinates coordinates;
  private double distance;

  public Landmark() {}

  public Landmark(
      String name, String category, String subCategory, Coordinates coordinates, double distance) {
    this.name = name;
    this.category = category;
    this.subCategory = subCategory;
    this.coordinates = coordinates;
    this.distance = distance;
  }
}
