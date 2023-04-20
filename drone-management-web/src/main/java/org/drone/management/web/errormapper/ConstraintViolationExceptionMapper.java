package org.drone.management.web.errormapper;

import org.drone.management.model.exception.ExceptionRecords;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    private static final int BAD_REQUEST_HTTP_STATUS = 400;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ExceptionRecords.ErrorEntry> errorEntries = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .map(ExceptionRecords.ErrorEntry::new)
                .toList();

        return Response.status(BAD_REQUEST_HTTP_STATUS)
                .entity(errorEntries)
                .build();
    }
}