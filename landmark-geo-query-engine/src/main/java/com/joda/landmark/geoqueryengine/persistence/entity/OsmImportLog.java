package com.joda.landmark.geoqueryengine.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "landmark_osm_import_log")
public class OsmImportLog {

  public enum ImportStatus {
    STARTED,
    IN_PROGRESS,
    COMPLETED,
    FAILED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "import_id", nullable = false, unique = true, updatable = false)
  private UUID importId = UUID.randomUUID();

  @Column(name = "start_time", nullable = false)
  private OffsetDateTime startTime;

  @Column(name = "end_time")
  private OffsetDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "import_status", nullable = false)
  private ImportStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "import_to", nullable = false)
  private TargetTable importTo; // "a" or "b"

  @Column(name = "description")
  private String description;
}
