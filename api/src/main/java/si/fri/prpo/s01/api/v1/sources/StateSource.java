package si.fri.prpo.s01.api.v1.sources;


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

    @POST
    public Response addState(State state){
        state = statesBean.addState(state);

        return Response.ok(state).build();
    }

    @GET
    @Path("{id}")
    public Response getState(@PathParam("id") Integer stateId){

        State state = statesBean.getState(stateId);
        return Response.ok(state).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteState(@PathParam("id") Integer stateId){

        statesBean.deleteState(stateId);
        return Response.noContent().build();
    }

    @POST
    public Response createState(State state){
        state = statesBean.addState(state);
        return Response.status(Response.Status.CREATED).entity(state).build();
    }



}
