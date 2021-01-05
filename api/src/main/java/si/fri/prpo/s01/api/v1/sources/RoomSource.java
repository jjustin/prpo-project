package si.fri.prpo.s01.api.v1.sources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.s01.entities.Entrance;
import si.fri.prpo.s01.services.beans.RoomManagerBean;
import si.fri.prpo.s01.services.beans.RoomsBean;
import si.fri.prpo.s01.entities.Room;
import si.fri.prpo.s01.services.beans.StatesBean;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

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
    private RoomManagerBean roomManagerBean;

    @Inject
    private StatesBean statesBean;

    @GET
    @Operation(summary = "Get list of rooms", description = "Returns list of rooms specified in the filter")
    @APIResponses({
            @APIResponse(description = "List of rooms",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Room.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of all enrances")}
            )
    })
    public Response getRooms(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        List<Room> rooms = roomsBean.getRooms(query);

        return Response.
                ok(rooms).
                header("X-Total-Count", roomsBean.countRooms(query)).
                build();
    }

    @GET
    @Operation(summary = "Get list of owners", description = "Returns list of all room owners")
    @APIResponses({
            @APIResponse(description = "List of owners", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class, type = SchemaType.ARRAY))
            )
    })
    @Path("owners")
    public Response getOwners(){
        List<String> rooms = roomsBean.getOwners();

        return Response.
                ok(rooms).
                build();
    }

    @GET
    @Operation(summary = "Get a single room", description = "Returns a single room")
    @APIResponses({
            @APIResponse(description = "Room", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @Path("{id}")
    public Response getRoom(@PathParam("id") Integer roomId){
        Room room = roomsBean.getRoom(roomId);
        return Response.ok(room).build();
    }

    @DELETE
    @Operation(summary = "Delete a room", description = "Deletes a room")
    @APIResponses({
            @APIResponse(description = "Removed room's ID", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    @Path("{id}")
    public Response deleteRoom(@PathParam("id") Integer roomID){
        roomsBean.deleteRoom(roomID);
        return Response.ok(roomID).build();
    }

    @POST
    @Operation(summary = "Create room with entrances", description = "Create room with entrances")
    @APIResponses({
            @APIResponse(description = "New room created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    public Response createRoomWithEntrances(@RequestBody(
            description = "DTO for adding rooms", required = true,
            content = @Content(schema = @Schema(implementation = AddRoomWithEntrancesDTO.class)))
                                    AddRoomWithEntrancesDTO roomWithEntrancesDTO){

        Room room = roomManagerBean.addRoomWithEntrances(roomWithEntrancesDTO);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
}
