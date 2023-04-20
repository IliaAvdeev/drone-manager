package org.drone.management.impl.job.state;

import io.quarkus.scheduler.Scheduled;
import org.drone.management.api.service.DroneLifecycleLogService;
import org.drone.management.api.service.DroneLoadItemService;
import org.drone.management.api.service.DroneService;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;
import org.drone.management.model.dto.DroneState;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.drone.management.model.dto.DroneState.DELIVERED;

@Transactional
@ApplicationScoped
public class DroneLifecycleManagementJob {
    @Inject
    DroneService droneService;
    @Inject
    DroneLoadItemService droneLoadItemService;
    @Inject
    DroneLifecycleLogService droneLifecycleLogService;
    @ConfigProperty(name = "drone-management.job.drone-lifecycle-management.interval")
    String batteryDecrementInterval;

    @Scheduled(every = "${drone-management.job.drone-lifecycle-management.interval}")
    void updateState() {
        List<DbRecords.DroneLifecycleLog> droneLifecycleLogs = new ArrayList<>();
        List<Drone> drones = droneService.getAll()
                .stream()
                .filter(drone -> nonNull(getNextState(drone)))
                .filter(this::hasExpiredState)
                .filter(this::hasEnoughBatteryCapacity)
                .peek(drone -> droneLifecycleLogs.add(generateDroneLifecycleLog(drone)))
                .peek(drone -> drone.setState(getNextState(drone).name()))
                .peek(this::dropDroneLoadIfNeeded)
                .toList();
        droneService.save(drones);
        droneLifecycleLogService.save(droneLifecycleLogs);
    }

    private DroneState getNextState(Drone drone) {
        return DroneState.getNextState(drone.getState());
    }

    private boolean hasExpiredState(Drone drone) {
        long current = OffsetDateTime.now().toEpochSecond();
        long latest = droneLifecycleLogService.getLatestStateTransitionTimestamp(drone.getId()).toEpochSecond();
        return current - latest >= DroneState.getDuration(drone.getState());
    }

    private boolean hasEnoughBatteryCapacity(Drone drone) {
        DroneState nextState = getNextState(drone);
        int interval = Math.toIntExact(Duration.parse(batteryDecrementInterval).getSeconds());
        return drone.getBatteryCapacity() >= nextState.getMinimumBatteryLevel(interval);
    }

    private DbRecords.DroneLifecycleLog generateDroneLifecycleLog(Drone drone) {
        String nextStateName = getNextState(drone).name();
        return new DbRecords.DroneLifecycleLog(drone.getId(), drone.getState(), nextStateName);
    }

    private void dropDroneLoadIfNeeded(Drone drone) {
        if (DELIVERED.equals(getNextState(drone))) {
            droneLoadItemService.deleteByDroneId(drone.getId());
        }
    }
}