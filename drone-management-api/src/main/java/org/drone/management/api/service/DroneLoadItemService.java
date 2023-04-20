package org.drone.management.api.service;

import org.drone.management.model.db.DbRecords;

import java.util.List;
import java.util.UUID;

public interface DroneLoadItemService extends CRUDService<DbRecords.DroneLoadItem> {
    void deleteByDroneId(UUID droneId);

    List<DbRecords.DroneLoadItem> getByDroneId(UUID droneId);

    int getLoadWeight(UUID droneId);

    int getLoadWeight(List<DbRecords.DroneLoadItem> droneLoadItems);
}