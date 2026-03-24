package com.joda.landmark.geoqueryengine.service;

import com.joda.landmark.geoqueryengine.persistence.entity.OsmImportLog;
import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestLog;
import com.joda.landmark.geoqueryengine.persistence.entity.TargetTable;
import com.joda.landmark.geoqueryengine.persistence.repository.OsmImportLogRepository;
import com.joda.landmark.geoqueryengine.persistence.repository.PointOfInterestLogRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointOfInterestUpdateService {

  private final AtomicBoolean running = new AtomicBoolean(false);

  private final PointOfInterestLogRepository poiLogRepo;
  private final OsmImportLogRepository osmImportLogRepo;
  private final JdbcTemplate jdbcTemplate;

  public void swapActiveTable(String table) {

    if (!table.equalsIgnoreCase(TargetTable.A.name())
        && !table.equalsIgnoreCase(TargetTable.B.name())) {
      throw new IllegalArgumentException("Table must be 'a' or 'b'");
    }
    swapActiveTable(TargetTable.valueOf(table.toUpperCase()));
  }

  private void swapActiveTable(TargetTable table) {

    PointOfInterestLog lastPoiLog = poiLogRepo.findTopByOrderByStartTimeDesc();

    if (lastPoiLog == null) {
      createNewPointOfInterestLog(table);
    }

    if (lastPoiLog != null && lastPoiLog.getInUse() != table) {
      lastPoiLog.setEndTime(OffsetDateTime.now());
      poiLogRepo.save(lastPoiLog);
      createNewPointOfInterestLog(table);
    }
  }

  private void createNewPointOfInterestLog(TargetTable table) {
    PointOfInterestLog poiLog = new PointOfInterestLog();
    poiLog.setInUse(table);
    poiLog.setStartTime(OffsetDateTime.now());
    poiLogRepo.save(poiLog);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public OsmImportLog saveImportLog(OsmImportLog osmImportLog) {
    return osmImportLogRepo.save(osmImportLog);
  }

  public OsmImportLog initiateImport() {

    if (!running.compareAndSet(false, true)) {
      throw new IllegalStateException("Import already running");
    }

    PointOfInterestLog lastLog = poiLogRepo.findTopByOrderByStartTimeDesc();

    TargetTable active = (lastLog != null) ? lastLog.getInUse() : TargetTable.B;

    TargetTable inactive = active == TargetTable.A ? TargetTable.B : TargetTable.A;

    String tableName = "landmark_point_of_interest_" + inactive.name().toLowerCase();

    log.info("Import will started for table: {}", tableName);

    // Create OsmImportLog
    OsmImportLog osmImportLog = new OsmImportLog();
    osmImportLog.setStartTime(OffsetDateTime.now());
    osmImportLog.setImportTo(inactive);
    osmImportLog.setStatus(OsmImportLog.ImportStatus.STARTED);
    osmImportLog.setDescription("Import to " + inactive);
    return saveImportLog(osmImportLog);
  }

  @Transactional
  public void triggerImport(UUID importId) {

    OsmImportLog osmImportLog = osmImportLogRepo.findByImportId(importId);

    if (osmImportLog == null || osmImportLog.getStatus() != OsmImportLog.ImportStatus.STARTED) {
      log.error("Not Valid ImportId : {}", importId);
      throw new IllegalStateException("Not Valid ImportId");
    }

    String tableName =
        "landmark_point_of_interest_" + osmImportLog.getImportTo().name().toLowerCase();

    try {
      // Clear inactive table
      jdbcTemplate.execute("TRUNCATE TABLE " + tableName);

      osmImportLog.setStatus(OsmImportLog.ImportStatus.IN_PROGRESS);
      osmImportLog = saveImportLog(osmImportLog);

      // Import data
      String sql =
          "INSERT INTO "
              + tableName
              + " (osm_id, amenity, brand, name, point)\n"
              + "SELECT osm_id, amenity, brand, name, way\n"
              + "FROM planet_osm_point "
              + "WHERE amenity IS NOT NULL;";

      int rows = jdbcTemplate.update(sql);

      log.info("Import finished for {} with {} rows", tableName, rows);

      osmImportLog.setDescription("Imported rows: " + rows);

      if (rows == 0) {
        throw new IllegalStateException("Import produced 0 rows");
      }

      // Mark completed
      osmImportLog.setStatus(OsmImportLog.ImportStatus.COMPLETED);
      osmImportLog.setEndTime(OffsetDateTime.now());
      osmImportLog = saveImportLog(osmImportLog);

      // Swap ONLY AFTER success
      swapActiveTable(osmImportLog.getImportTo());

    } catch (Exception e) {
      log.error("Import FAILED", e);

      osmImportLog.setStatus(OsmImportLog.ImportStatus.FAILED);
      osmImportLog.setEndTime(OffsetDateTime.now());
      osmImportLog.setDescription(e.getMessage());
      osmImportLog = saveImportLog(osmImportLog);

      throw e; // rethrow so transaction rolls back
    } finally {
      running.set(false);
    }
  }
}
