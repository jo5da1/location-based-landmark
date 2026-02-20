package com.joda.landmark.geoqueryengine.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Entity
@Table(name = "planet_osm_point")
@ToString
public class PlanetOsmPoint {

  @Id
  @Column(name = "osm_id")
  private Long osmId;

  private String amenity;
  private String brand;
  private String name;

  @Column(name = "way", columnDefinition = "geometry(Point,3857)")
  private Point way;
}
