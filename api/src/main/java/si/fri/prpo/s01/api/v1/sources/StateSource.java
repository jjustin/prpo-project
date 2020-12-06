package si.fri.prpo.s01.api.v1.sources;


import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.StatesBean;
import si.fri.prpo.s01.services.dtos.StateDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("states")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StateSource {
    @Inject
    private StatesBean statesBean;

    @Operation(summary = "Get a state", description = "Returns a state")
    @APIResponses({
            @APIResponse(description = "List of states", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @GET
    @Path("{id}")
    public Response getState(@PathParam("id") Integer stateId){

        State state = statesBean.getState(stateId);
        return Response.ok(state).build();
    }

    @Operation(summary = "Delete a state", description = "Deletes a state")
    @APIResponses({
            @APIResponse(description = "State deleted", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @DELETE
    @Path("{id}")
    public Response deleteState(@PathParam("id") Integer stateId){

        statesBean.deleteState(stateId);
        return Response.noContent().build();
    }

    @Operation(summary = "Create a new state", description = "Creates a state")
    @APIResponses({
            @APIResponse(description = "New state created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = State.class)))
    })
    @POST
    public Response createState(State state){
        state = statesBean.addState(state);
        return Response.status(Response.Status.CREATED).entity(state).build();
    }



}
