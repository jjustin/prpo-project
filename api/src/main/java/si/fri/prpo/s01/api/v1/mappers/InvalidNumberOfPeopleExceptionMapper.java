package si.fri.prpo.s01.api.v1.mappers;

import si.fri.prpo.s01.services.exceptions.InvalidNumberOfPeopleException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidNumberOfPeopleExceptionMapper implements
        ExceptionMapper<InvalidNumberOfPeopleException> {
    @Override
    public Response toResponse(InvalidNumberOfPeopleException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                String.format("{\"error\":\"%s\"}", e.getMessage()))
                .build();
    }
}
