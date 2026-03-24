package com.joda.landmark.geoqueryengine.persistence.repository;

import com.joda.landmark.geoqueryengine.persistence.entity.PointOfInterestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointOfInterestLogRepository extends JpaRepository<PointOfInterestLog, Long> {
  PointOfInterestLog findTopByOrderByStartTimeDesc();
}
