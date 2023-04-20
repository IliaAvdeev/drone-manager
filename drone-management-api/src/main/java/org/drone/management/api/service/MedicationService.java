package org.drone.management.api.service;

import org.drone.management.model.db.DbRecords;

import java.util.List;
import java.util.UUID;

public interface MedicationService extends CRUDService<DbRecords.Medication> {
    List<DbRecords.Medication> getByIds(List<UUID> ids);
}