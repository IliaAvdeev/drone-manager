package org.drone.management.web.controller;

import org.drone.management.api.service.MedicationService;
import org.drone.management.impl.mapper.MedicationMapper;
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
import java.util.List;
import java.util.UUID;

@Path("/droneManagement/medication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicationController {
    @Inject
    MedicationService medicationService;
    @Inject
    MedicationMapper medicationMapper;

    @POST
    public DtoRecords.MedicationDto create(@Valid DtoRecords.MedicationDto medicationDto) {
        return medicationMapper.toDto(medicationService.save(medicationMapper.toDomainObject(medicationDto)));
    }

    @GET
    @Path("/{id}")
    public DtoRecords.MedicationDto getById(@PathParam("id") UUID id) {
        return medicationMapper.toDto(medicationService.getById(id));
    }

    @GET
    public List<DtoRecords.MedicationDto> getAll() {
        return medicationMapper.toDto(medicationService.getAll());
    }
}