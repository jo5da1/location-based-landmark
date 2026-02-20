package com.joda.landmark.geoqueryengine.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PlanetOsmPointRepository extends Repository<PlanetOsmPoint, Long> {

  @Query(
      value =
          """
            SELECT
                p.osm_id,
                p.amenity,
                p.brand,
                p.name,
                ST_Transform(p.way, 4326) AS way
            FROM planet_osm_point p
            WHERE p.amenity = :amenity
          """,
      nativeQuery = true)
  List<PlanetOsmPoint> findByAmenity(String amenity);

  @Query(
      value =
          """
            SELECT
                p.osm_id,
                p.amenity,
                p.brand,
                p.name,
                ST_Transform(p.way, 4326) AS way
            FROM planet_osm_point p
            WHERE p.amenity IN (:amenities)
            AND ST_DWithin(
                p.way,
                ST_Transform(
                    ST_SetSRID(ST_MakePoint(:lon, :lat), 4326),
                    3857),
                :radius
            )
        """,
      nativeQuery = true)
  List<PlanetOsmPoint> findNearbyByAmenity(
      @Param("amenities") List<String> amenities,
      @Param("lat") double lat,
      @Param("lon") double lon,
      @Param("radius") double radius);
}
