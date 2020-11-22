package si.fri.prpo.s01.api.v1.sources;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.RoomsBean;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.beans.StatesBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@ApplicationScoped
@Path("rooms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoomSource {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private RoomsBean roomsBean;

    @Inject
    private EntrancesBean entrancesBean;
    @Inject
    private StatesBean statesBean;

//    @Operation(description="Returns list of all rooms", summary="List of rooms")
//    @APIResponses({
//            @APIResponse(ResponseCode = "200",
//                    description = "List of rooms"
//                    content = @Content(schema = @Schema(implementation = Room.class, type = SchemaType.ARRAY))
//            )
//    })
    @GET
    public Response getRooms(){
        // FIXME pagination
        List<Room> rooms = roomsBean.getRooms();

        return Response.
                ok(rooms).
                header("X-Total-Count", rooms.size()).
                build();
    }

    @GET
    @Path("{id}")
    public Response getRoom(@PathParam("id") Integer roomId){
        Room room = roomsBean.getRoom(roomId);
        return Response.ok(room).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRoom(@PathParam("id") Integer roomID){
        roomsBean.deleteRoom(roomID);
        return Response.noContent().build();
    }

    @POST
    public Response createRoom(Room room){
        room = roomsBean.addRoom(room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("{id}/entrances")
    public Response getEntrancesForRoom(@PathParam("id") Integer roomId){
        // FIXME pagination
        List<Entrance> entrances = entrancesBean.getEntrancesForRoom(roomId);

        return Response.
                ok(entrances).
                header("X-Total-Count", entrances.size()).
                build();
    }

    @GET
    @Path("{id}/states")
    public Response getStatesForRoom(@PathParam("id") Integer roomID){
        List<State> states = statesBean.getStatesForRoom(roomID);

        return Response.
                ok(states).
                header("X-Total-Count", states.size()).
                build();
    }

}
