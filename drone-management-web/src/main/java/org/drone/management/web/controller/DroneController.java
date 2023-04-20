package org.drone.management.web.controller;

import org.drone.management.api.service.DroneService;
import org.drone.management.impl.mapper.DroneLoadItemMapper;
import org.drone.management.impl.mapper.DroneMapper;
import org.drone.management.impl.validator.DroneValidator;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.dto.DtoRecords;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/droneManagement/drone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DroneController {
    @Inject
    DroneService droneService;
    @Inject
    DroneMapper droneMapper;
    @Inject
    DroneValidator droneValidator;
    @Inject
    DroneLoadItemMapper droneLoadItemMapper;

    @POST
    @Path("/register")
    public DtoRecords.DroneDto register(@Valid DtoRecords.DroneDto droneDto) {
        return droneMapper.toDto(droneService.register(droneMapper.toDomainObject(droneDto)));
    }

    @POST
    @Path("/{id}/load")
    public Response load(@PathParam("id") UUID id, List<DtoRecords.DroneLoadItemDto> items) {
        List<DbRecords.DroneLoadItem> droneLoadItems = droneLoadItemMapper.toDomainObject(items);
        droneValidator.validateLoad(id, droneLoadItems);
        droneService.load(id, droneLoadItems);
        return Response.ok().build();
    }

    @GET
    @Path("/available")
    public List<DtoRecords.DroneDto> getAllAvailable() {
        return droneMapper.toDto(droneService.getAllAvailable());
    }

    @GET
    @Path("/{id}/checkBatteryLevel")
    public Integer checkBatteryLevel(@PathParam("id") UUID id) {
        return droneService.checkBatteryLevel(id);
    }
}