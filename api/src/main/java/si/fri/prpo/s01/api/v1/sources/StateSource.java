package si.fri.prpo.s01.api.v1.sources;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.s01.entities.State;
import si.fri.prpo.s01.services.beans.StatesBean;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("states")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secure
public class StateSource {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private StatesBean statesBean;

    @GET
    @Operation(summary = "Get list of states", description = "Returns list of states specified in the filter")
    @APIResponses({
            @APIResponse(description = "List of states", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @PermitAll
    public Response getStates(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        List<State> states = statesBean.getStates(query);

        return Response.
                ok(states).
                header("X-Total-Count", statesBean.countStates(query)).
                build();
    }

    @GET
    @Operation(summary = "Get a state", description = "Returns a state")
    @APIResponses({
            @APIResponse(description = "List of states", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @Path("{id}")
    @RolesAllowed("user")
    public Response getState(@PathParam("id") Integer stateId){

        State state = statesBean.getState(stateId);
        return Response.ok(state).build();
    }

    @DELETE
    @Operation(summary = "Delete a state", description = "Deletes a state")
    @APIResponses({
            @APIResponse(description = "State deleted", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @Path("{id}")
    @RolesAllowed("admin")
    public Response deleteState(@PathParam("id") Integer stateId){

        statesBean.deleteState(stateId);
        return Response.noContent().build();
    }

    @POST
    @Operation(summary = "Create a new state", description = "Creates a state")
    @APIResponses({
            @APIResponse(description = "New state created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @RolesAllowed("admin")
    public Response createState(State state){
        state = statesBean.addState(state);
        return Response.status(Response.Status.CREATED).entity(state).build();
    }
}
