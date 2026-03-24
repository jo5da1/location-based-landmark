package com.joda.landmark.geoqueryengine.messaging.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointOfInterest {

  private Long osmId;
  private String amenity;
  private String brand;
  private String name;
  private String pointWkt;
}
