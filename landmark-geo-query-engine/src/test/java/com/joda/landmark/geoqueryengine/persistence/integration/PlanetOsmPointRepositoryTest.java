package com.joda.landmark.geoqueryengine.persistence.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.joda.landmark.geoqueryengine.AbstractIntegrationTest;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPoint;
import com.joda.landmark.geoqueryengine.persistence.PlanetOsmPointRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql({"/sql/extension.sql", "/sql/schema.sql", "/sql/test-data.sql"})
@ActiveProfiles("test")
class PlanetOsmPointRepositoryTest extends AbstractIntegrationTest {

  @Autowired PlanetOsmPointRepository repository;

  @Autowired TestEntityManager entityManager;

  GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 3857);

  @BeforeEach
  void setup() {
    Point point = geometryFactory.createPoint(new Coordinate(11.9746, 57.7089));

    PlanetOsmPoint osmPoint = new PlanetOsmPoint();
    osmPoint.setOsmId(1L);
    osmPoint.setAmenity("restaurant");
    osmPoint.setName("Test Restaurant");
    osmPoint.setBrand("Local Vendor");
    osmPoint.setWay(point);

    entityManager.persist(osmPoint);
    entityManager.flush();
  }

  @Test
  void findByAmenity_shouldReturnRestaurant() {

    List<PlanetOsmPoint> result = repository.findByAmenity("restaurant");

    System.out.println(result);
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getAmenity()).isEqualTo("restaurant");
    assertThat(result.get(0).getName()).isEqualTo("Test Restaurant");
  }

  @Test
  void findNearbyByAmenity_shouldReturnEmptyWhenNotFound() {

    List<PlanetOsmPoint> result =
        repository.findNearbyByAmenity(
            List.of("cafe"), 57.7089, 11.9746, 1000 // meters
            );

    System.out.println(result);

    assertThat(result).isEmpty();
  }

  @Test
  void findNearbyByAmenity_shouldReturnCafeWithinRadius() {

    List<PlanetOsmPoint> result =
        repository.findNearbyByAmenity(
            List.of("cafe"), 57.72495531608793, 11.949546931031295, 1000 // meters
            );

    System.out.println(result);

    assertThat(result).isNotEmpty();
  }
}
