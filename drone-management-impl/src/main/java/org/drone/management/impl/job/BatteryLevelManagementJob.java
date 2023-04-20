package org.drone.management.impl.job;

import io.quarkus.scheduler.Scheduled;
import org.drone.management.api.service.CheckDroneBatteryLevelLogService;
import org.drone.management.api.service.DroneService;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;
import org.drone.management.model.dto.DroneState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@ApplicationScoped
public class BatteryLevelManagementJob {
    @Inject
    DroneService droneService;
    @Inject
    CheckDroneBatteryLevelLogService droneCheckBatteryLevelLogService;

    @Scheduled(every = "18s")
    void decrementBatteryCapacity() {
        List<Drone> drones = droneService.getAll()
                .stream()
                .peek(drone -> drone.setBatteryCapacity(getDecrementedBatteryCapacity(drone)))
                .toList();
        droneService.save(drones);
    }

    @Scheduled(every = "2s")
    void checkBatteryCapacity() {
        List<DbRecords.CheckDroneBatteryLevelLog> list = droneService.getAll()
                .stream()
                .map(drone -> new DbRecords.CheckDroneBatteryLevelLog(drone.getId(), drone.getBatteryCapacity()))
                .toList();
        droneCheckBatteryLevelLogService.save(list);
    }

    private int getDecrementedBatteryCapacity(Drone drone) {
        return Math.max(0, drone.getBatteryCapacity() - DroneState.getBatteryUsage(drone.getState()));
    }
}