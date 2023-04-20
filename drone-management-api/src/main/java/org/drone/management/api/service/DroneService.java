package org.drone.management.api.service;

import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;

import java.util.List;
import java.util.UUID;

public interface DroneService extends CRUDService<Drone> {
    void load(UUID id, List<DbRecords.DroneLoadItem> items);

    Drone register(Drone drone);

    Integer checkBatteryLevel(UUID id);

    List<Drone> getAllAvailable();
}