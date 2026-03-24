package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.messaging.dto.PointOfInterest;
import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestA;
import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestB;
import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestLog;
import com.joda.landmark.geoqueryengine.persistence.entity.TargetTable;
import com.joda.landmark.geoqueryengine.persistence.repository.PointOfInterestARepository;
import com.joda.landmark.geoqueryengine.persistence.repository.PointOfInterestBRepository;
import com.joda.landmark.geoqueryengine.persistence.repository.PointOfInterestLogRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointOfInterestService {

  private final EntityManager entityManager;
  private final PointOfInterestARepository poiRepoA;
  private final PointOfInterestBRepository poiRepoB;
  private final PointOfInterestLogRepository poiLogRepo;

  public PointOfInterestLog getPointOfInterestInUse() {
    return poiLogRepo.findTopByOrderByStartTimeDesc();
  }

  public List<PointOfInterestLog> getPointOfInterestLog() {
    return poiLogRepo.findAll();
  }

  public List<PointOfInterest> findAllPOIActive() {

    PointOfInterestLog poiLog = poiLogRepo.findTopByOrderByStartTimeDesc();
    if (poiLog == null) {
      throw new IllegalStateException("No active POI table found");
    }

    TargetTable active = poiLog.getInUse();

    if (TargetTable.A == active) {
      return poiRepoA.findAll().stream().map(this::mapToDTO).toList();
    } else {
      return poiRepoB.findAll().stream().map(this::mapToDTO).toList();
    }
  }

  public List<PointOfInterest> findPOIWithinDistance(double lon, double lat, double radius) {

    PointOfInterestLog poiLog = poiLogRepo.findTopByOrderByStartTimeDesc();
    if (poiLog == null) {
      throw new IllegalStateException("No active POI table found");
    }

    TargetTable active = poiLog.getInUse();
    String tableName = "landmark_point_of_interest_" + active.name().toLowerCase();

    log.info("Fetching from table: {}", tableName);

    String sql =
        "SELECT p.osm_id, p.amenity, p.brand, p.name, ST_Transform(p.point, 4326) as point "
            + "FROM "
            + tableName
            + " p"
            + " WHERE ST_DWithin(p.point, ST_Transform(ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), 3857), :distance)";

    if (TargetTable.A == active) {
      return findPoiAWithinDistance(sql, lon, lat, radius);
    } else {
      return findPoiBWithinDistance(sql, lon, lat, radius);
    }
  }

  @SuppressWarnings("unchecked")
  private List<PointOfInterest> findPoiBWithinDistance(
      String sql, double lon, double lat, double radius) {

    List<PointOfInterestB> listPoi =
        entityManager
            .createNativeQuery(sql, PointOfInterestB.class)
            .setParameter("lon", lon)
            .setParameter("lat", lat)
            .setParameter("distance", radius)
            .getResultList();

    return listPoi.stream().map(this::mapToDTO).toList();
  }

  @SuppressWarnings("unchecked")
  private List<PointOfInterest> findPoiAWithinDistance(
      String sql, double lon, double lat, double radius) {

    List<PointOfInterestA> listPoi =
        entityManager
            .createNativeQuery(sql, PointOfInterestA.class)
            .setParameter("lon", lon)
            .setParameter("lat", lat)
            .setParameter("distance", radius)
            .getResultList();

    return listPoi.stream().map(this::mapToDTO).toList();
  }

  private PointOfInterest mapToDTO(Object entity) {

    PointOfInterest poi = new PointOfInterest();

    if (entity instanceof PointOfInterestA a) {
      poi.setOsmId(a.getOsmId());
      poi.setAmenity(a.getAmenity());
      poi.setName(a.getName());
      poi.setBrand(a.getBrand());
      poi.setPointWkt(a.getLocation().toText());
    } else if (entity instanceof PointOfInterestB b) {
      poi.setOsmId(b.getOsmId());
      poi.setAmenity(b.getAmenity());
      poi.setName(b.getName());
      poi.setBrand(b.getBrand());
      poi.setPointWkt(b.getLocation().toText());
    }
    return poi;
  }
}
