package si.fri.prpo.s01.api.v1.sources;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.RoomsBean;
import si.fri.prpo.s01.entitete.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@ApplicationScoped
@Path("entrances")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntranceSource {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private EntrancesBean entrancesBean;

    @POST
    public Response AddEntrance(Entrance entrance){
        // FIXME pagination
        entrance = entrancesBean.addEntrance(entrance);

        return Response.ok(entrance).build();
    }
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



}
