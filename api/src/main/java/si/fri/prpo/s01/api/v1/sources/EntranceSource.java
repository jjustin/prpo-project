package si.fri.prpo.s01.api.v1.sources;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.OccupancyRateBean;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;
import si.fri.prpo.s01.services.dtos.PeopleEnterDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@ApplicationScoped
@Path("entrances")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntranceSource {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private EntrancesBean entrancesBean;

    @Inject
    private OccupancyRateBean occupancyRateBean;

    @GET
    @Path("{id}")
    public Response getEntrance(@PathParam("id") Integer entranceId){
        Entrance entrance = entrancesBean.getEntrance(entranceId);
        return Response.ok(entrance).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEntrance(@PathParam("id") Integer entranceID){
        entrancesBean.deleteEntrance(entranceID);
        return Response.noContent().build();
    }

    @POST
    public Response createEntrance(Entrance entrance){
        entrance = entrancesBean.addEntrance(entrance);
        return Response.status(Response.Status.CREATED).entity(entrance).build();
    }

    @POST
    @Path("{id}/enter")
    public Response peopleEnter(@PathParam("id") Integer entranceID, PeopleEnterDTO peopleEnterDTO){
        peopleEnterDTO.setEntranceId(entranceID);
        State state = occupancyRateBean.peopleEnter(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(state).build();
    }

    @POST
    @Path("{id}/exit")
    public Response peopleExit(@PathParam("id") Integer entranceID, PeopleEnterDTO peopleEnterDTO){
        peopleEnterDTO.setEntranceId(entranceID);
        State state = occupancyRateBean.peopleEnter(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(state).build();
    }


}
