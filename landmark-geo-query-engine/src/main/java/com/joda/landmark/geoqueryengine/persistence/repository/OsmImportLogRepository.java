package com.joda.landmark.geoqueryengine.persistence.repository;

import com.joda.landmark.geoqueryengine.persistence.entity.OsmImportLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsmImportLogRepository extends JpaRepository<OsmImportLog, Long> {
  OsmImportLog findByImportId(UUID importId);
}
