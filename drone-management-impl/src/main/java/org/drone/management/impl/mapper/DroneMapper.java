package org.drone.management.impl.mapper;

import org.drone.management.model.db.Drone;
import org.drone.management.model.dto.DroneState;
import org.drone.management.model.dto.DtoRecords;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.JSR330;

@Mapper(componentModel = JSR330)
public interface DroneMapper extends DtoMapper<DtoRecords.DroneDto, Drone> {
    default String mapState(DroneState state) {
        return DroneState.IDLE.name();
    }
}