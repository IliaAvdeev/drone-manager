package org.drone.management.impl.mapper;

import org.drone.management.model.db.DbRecords;
import org.drone.management.model.dto.DtoRecords;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.JSR330;

@Mapper(componentModel = JSR330)
public interface CheckDroneBatteryLevelLogMapper
        extends DtoMapper<DtoRecords.CheckDroneBatteryLevelLogDto, DbRecords.CheckDroneBatteryLevelLog> {
}