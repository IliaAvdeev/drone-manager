package org.drone.management.impl.service;

import org.drone.management.api.service.DroneLifecycleLogService;
import org.drone.management.api.service.DroneLoadItemService;
import org.drone.management.api.service.DroneService;
import org.drone.management.impl.repository.DroneRepositoryImpl;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;
import org.drone.management.model.dto.DroneState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Named
@Transactional
@ApplicationScoped
public class DroneServiceImpl extends CRUDServiceImpl<Drone> implements DroneService {
    @Inject
    DroneRepositoryImpl droneRepository;
    @Inject
    DroneLoadItemService droneLoadItemService;
    @Inject
    DroneLifecycleLogService droneLifecycleLogService;

    @Override
    public void load(UUID droneId, List<DbRecords.DroneLoadItem> items) {
        List<DbRecords.DroneLoadItem> droneLoadItems = items.stream()
                .map(droneLoadItemDto -> createDroneLoadItem(droneId, droneLoadItemDto))
                .toList();

        droneLoadItemService.save(droneLoadItems);
        changeState(droneId);
    }

    @Override
    public Drone register(Drone drone) {
        droneRepository.persist(drone);

        UUID droneId = drone.getId();
        String stateTo = drone.getState();
        DbRecords.DroneLifecycleLog droneLifecycleLog = new DbRecords.DroneLifecycleLog(droneId, stateTo);
        droneLifecycleLogService.save(droneLifecycleLog);

        return getById(drone.getId());
    }

    @Override
    public Integer checkBatteryLevel(UUID id) {
        return getById(id).getBatteryCapacity();
    }

    @Override
    public List<Drone> getAllAvailable() {
        return droneRepository.list("state in ?1 and batteryCapacity >= 25",
                List.of(DroneState.IDLE.name(), DroneState.LOADING.name()));
    }

    private DbRecords.DroneLoadItem createDroneLoadItem(UUID droneId, DbRecords.DroneLoadItem droneLoadItemDto) {
        return new DbRecords.DroneLoadItem(droneId, droneLoadItemDto.medicationId(), droneLoadItemDto.quantity());
    }

    private void changeState(UUID droneId) {
        Drone drone = getById(droneId);
        String stateFrom = drone.getState();

        int load = droneLoadItemService.getLoadWeight(droneId);

        String newState = load < drone.getWeightLimit() ? DroneState.LOADING.name() : DroneState.LOADED.name();
        drone.setState(newState);
        droneRepository.persist(drone);

        String stateTo = drone.getState();
        if (!stateTo.equals(stateFrom)) {
            DbRecords.DroneLifecycleLog droneLifecycleLog = new DbRecords.DroneLifecycleLog(droneId, stateFrom, stateTo);
            droneLifecycleLogService.save(droneLifecycleLog);
        }
    }
}