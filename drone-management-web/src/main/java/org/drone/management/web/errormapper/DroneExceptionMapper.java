package org.drone.management.web.errormapper;

import org.drone.management.model.exception.DroneException;
import org.drone.management.model.exception.ExceptionRecords;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DroneExceptionMapper implements ExceptionMapper<DroneException> {

    private static final int UNPROCESSABLE_ENTITY_HTTP_STATUS = 422;

    @Override
    public Response toResponse(DroneException exception) {
        return Response.status(UNPROCESSABLE_ENTITY_HTTP_STATUS)
                .entity(new ExceptionRecords.ErrorEntry(exception.getMessage()))
                .build();
    }
}