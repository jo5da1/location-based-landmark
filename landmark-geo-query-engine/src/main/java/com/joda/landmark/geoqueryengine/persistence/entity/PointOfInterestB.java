package com.joda.landmark.geoqueryengine.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Entity
@Table(name = "landmark_point_of_interest_b")
@ToString
public class PointOfInterestB {

  @Id
  @Column(name = "osm_id")
  private Long osmId;

  private String amenity;
  private String brand;
  private String name;

  @Column(name = "point", columnDefinition = "geometry(Point,3857)")
  private Point location;
}
