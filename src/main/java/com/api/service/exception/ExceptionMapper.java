package com.api.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        ErrorMessage message = new ErrorMessage(throwable.getMessage(),500,"http://documentation/todo/fake");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(message)
                        .build();
    }
}
