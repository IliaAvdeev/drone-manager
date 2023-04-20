package org.drone.management.model.db;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

public class DbRecords {
    @Entity
    @Table(name = "medication")
    public record Medication(@Id @GeneratedValue UUID id, String name, Integer weight, String code,
                             String image) implements HasId {
        public Medication() {
            this(null, null, null, null, null);
        }
    }

    @Entity
    @Table(name = "drone_load_item")
    public record DroneLoadItem(@Id @GeneratedValue UUID id,
                                @Column(name = "drone_id") UUID droneId,
                                @Column(name = "medication_id") UUID medicationId, Integer quantity) implements HasId {
        public DroneLoadItem() {
            this(null, null, null, null);
        }

        public DroneLoadItem(UUID medicationId, Integer quantity) {
            this(null, null, medicationId, quantity);
        }

        public DroneLoadItem(UUID droneId, UUID medicationId, Integer quantity) {
            this(null, droneId, medicationId, quantity);
        }
    }

    @Entity
    @Table(name = "drone_check_battery_level_log")
    public record CheckDroneBatteryLevelLog(@Id @GeneratedValue UUID id, @Column(name = "drone_id") UUID droneId,
                                            @Column(name = "battery_level") Integer batteryLevel) implements HasId {
        public CheckDroneBatteryLevelLog(UUID droneId, Integer batteryLevel) {
            this(null, droneId, batteryLevel);
        }

        public CheckDroneBatteryLevelLog() {
            this(null, null, null);
        }
    }

    @Entity
    @Table(name = "drone_lifecycle_log")
    public record DroneLifecycleLog(@Id @GeneratedValue UUID id, @Column(name = "drone_id") UUID droneId,
                                    @Column(name = "state_from") String stateFrom,
                                    @Column(name = "state_to") String stateTo,
                                    @Column(name = "transition_timestamp") @CreationTimestamp OffsetDateTime transitionTimestamp) implements HasId {
        public DroneLifecycleLog() {
            this(null, null, null, null, null);
        }

        public DroneLifecycleLog(UUID droneId, String stateTo) {
            this(null, droneId, null, stateTo, null);
        }

        public DroneLifecycleLog(UUID droneId, String stateFrom, String stateTo) {
            this(null, droneId, stateFrom, stateTo, null);
        }
    }
}