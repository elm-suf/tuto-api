package com.api.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements javax.ws.rs.ext.ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        ErrorMessage message = new ErrorMessage
                (e.getMessage(), 404, "http://documentation/todo/fake");
        return Response.status(Response.Status.NOT_FOUND)
                        .entity(message)
                        .build();
    }
}