package org.drone.management.web.controller;

import org.drone.management.api.service.DroneLoadItemService;
import org.drone.management.impl.mapper.DroneLoadItemMapper;
import org.drone.management.model.dto.DtoRecords;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/droneManagement/drone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DroneLoadItemController {
    @Inject
    DroneLoadItemService droneLoadItemService;
    @Inject
    DroneLoadItemMapper droneLoadItemMapper;

    @GET
    @Path("/{droneId}/medications")
    public List<DtoRecords.DroneLoadItemDto> getLoadedMedications(@PathParam("droneId") UUID droneId) {
        return droneLoadItemMapper.toDto(droneLoadItemService.getByDroneId(droneId));
    }
}