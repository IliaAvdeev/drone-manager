package org.drone.management.api.service;

import org.drone.management.model.db.DbRecords;

import java.util.List;

public interface CheckDroneBatteryLevelLogService {
    List<DbRecords.CheckDroneBatteryLevelLog> getAll();

    void save(List<DbRecords.CheckDroneBatteryLevelLog> logs);
}