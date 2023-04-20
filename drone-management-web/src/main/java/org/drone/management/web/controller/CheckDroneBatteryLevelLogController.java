package org.drone.management.web.controller;

import org.drone.management.api.service.CheckDroneBatteryLevelLogService;
import org.drone.management.impl.mapper.CheckDroneBatteryLevelLogMapper;
import org.drone.management.model.dto.DtoRecords;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/droneManagement/drone/log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckDroneBatteryLevelLogController {
    @Inject
    CheckDroneBatteryLevelLogService checkDroneBatteryLevelLogService;
    @Inject
    CheckDroneBatteryLevelLogMapper checkDroneBatteryLevelLogMapper;

    @GET
    @Path("/batteryLevel")
    public List<DtoRecords.CheckDroneBatteryLevelLogDto> getAll() {
        return checkDroneBatteryLevelLogMapper.toDto(checkDroneBatteryLevelLogService.getAll());
    }
}