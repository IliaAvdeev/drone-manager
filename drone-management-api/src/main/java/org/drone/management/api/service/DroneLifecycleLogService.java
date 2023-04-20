package org.drone.management.api.service;

import org.drone.management.model.db.DbRecords;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface DroneLifecycleLogService {
    void save(DbRecords.DroneLifecycleLog log);

    void save(List<DbRecords.DroneLifecycleLog> logs);

    OffsetDateTime getLatestStateTransitionTimestamp(UUID droneId);
}