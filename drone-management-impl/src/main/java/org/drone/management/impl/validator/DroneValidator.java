package org.drone.management.impl.validator;

import org.drone.management.api.service.DroneLoadItemService;
import org.drone.management.api.service.DroneService;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;
import org.drone.management.model.dto.DroneState;
import org.drone.management.model.exception.DroneException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DroneValidator {
    @Inject
    DroneService droneService;
    @Inject
    DroneLoadItemService droneLoadItemService;

    public void validateLoad(UUID droneId, List<DbRecords.DroneLoadItem> items) {
        Drone drone = droneService.getById(droneId);

        if (!(drone.getState().equals(DroneState.IDLE.name()) || drone.getState().equals(DroneState.LOADING.name()))) {
            throw new DroneException("A drone can be loaded only if has 'Idle' or 'Loading' state");
        }
        if (drone.getBatteryCapacity() <= 25) {
            throw new DroneException("A drone can be loaded only if has battery capacity more than 25%");
        }

        Integer weightLimit = drone.getWeightLimit();
        int currentLoadWeight = droneLoadItemService.getLoadWeight(droneId);
        int incomingLoadWeight = droneLoadItemService.getLoadWeight(items);

        if (currentLoadWeight + incomingLoadWeight > weightLimit) {
            throw new DroneException("A drone cannot carry more weight than its weight limit");
        }
    }
}