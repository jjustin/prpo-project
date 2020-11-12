package si.fri.prpo.s01.services.v1.sources;

import si.fri.prpo.s01.services.beans.RoomsBean;
import si.fri.prpo.s01.entitete.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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

//    @Operation(description="Returns list of all rooms", summary="List of rooms")
//    @APIResponses({
//            @APIResponse(ResponseCode = "200",
//                    description = "List of rooms"
//                    content = @Content(schema = @Schema(implementation = Room.class, type = SchemaType.ARRAY))
//            )
//    })
    @GET
    public Response GetRooms(){
        // FIXME pagination
        List<Room> rooms = roomsBean.getRooms();

        return Response.ok(rooms).header("X-Total-Count", rooms.size()).build();
    }
}
