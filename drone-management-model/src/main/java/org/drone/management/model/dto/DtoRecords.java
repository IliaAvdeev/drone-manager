package org.drone.management.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

public final class DtoRecords {
    public record DroneDto(UUID id, @Size(max = 100) String serialNumber, DroneModel model,
                           @Max(500) Integer weightLimit, @Max(100) Integer batteryCapacity,
                           DroneState state) {
    }

    public record MedicationDto(UUID id, Integer weight, String image,
                                @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{javax.validation.constraints.pattern.medication.name.message}") String name,
                                @Pattern(regexp = "^[A-Z0-9_]+$", message = "{javax.validation.constraints.pattern.medication.code.message}") String code) {
    }

    public record DroneLoadItemDto(UUID medicationId, Integer quantity) {}

    public record CheckDroneBatteryLevelLogDto(UUID id, UUID droneId, Integer batteryLevel) {}
}