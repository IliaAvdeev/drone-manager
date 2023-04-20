package org.drone.management.impl.service;

import org.drone.management.api.service.DroneLoadItemService;
import org.drone.management.api.service.MedicationService;
import org.drone.management.impl.repository.DroneLoadItemRepositoryImpl;
import org.drone.management.model.db.DbRecords;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Named
@Transactional
@ApplicationScoped
public class DroneLoadItemServiceImpl extends CRUDServiceImpl<DbRecords.DroneLoadItem> implements DroneLoadItemService {
    @Inject
    MedicationService medicationService;
    @Inject
    DroneLoadItemRepositoryImpl droneLoadItemRepository;

    @Override
    public List<DbRecords.DroneLoadItem> getByDroneId(UUID droneId) {
        return droneLoadItemRepository.find("droneId", droneId)
                .stream()
                .collect(Collectors.toMap(DbRecords.DroneLoadItem::medicationId, DbRecords.DroneLoadItem::quantity, Integer::sum))
                .entrySet()
                .stream()
                .map(entry -> new DbRecords.DroneLoadItem(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public void deleteByDroneId(UUID droneId) {
        droneLoadItemRepository.delete("droneId", droneId);
    }

    @Override
    public int getLoadWeight(UUID droneId) {
        List<DbRecords.DroneLoadItem> droneLoadItems = getByDroneId(droneId);
        return getLoadWeight(droneLoadItems);
    }

    @Override
    public int getLoadWeight(List<DbRecords.DroneLoadItem> droneLoadItems) {
        List<UUID> medicationIds = droneLoadItems.stream()
                .map(DbRecords.DroneLoadItem::medicationId)
                .toList();
        Map<UUID, DbRecords.Medication> medicationIndex = medicationService.getByIds(medicationIds)
                .stream()
                .collect(toMap(DbRecords.Medication::id, Function.identity()));

        return droneLoadItems.stream()
                .mapToInt(droneLoadItem -> {
                    UUID medicationId = droneLoadItem.medicationId();
                    Integer quantity = droneLoadItem.quantity();
                    DbRecords.Medication medication = medicationIndex.get(medicationId);
                    return medication.weight() * quantity;
                }).sum();
    }
}