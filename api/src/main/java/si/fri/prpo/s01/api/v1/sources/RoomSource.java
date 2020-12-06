package si.fri.prpo.s01.api.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.RoomManagerBean;
import si.fri.prpo.s01.services.beans.RoomsBean;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.beans.StatesBean;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

import javax.annotation.security.RolesAllowed;
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
    private EntrancesBean entrancesBean;
    @Inject
    private StatesBean statesBean;

    @Operation(summary = "Get list of rooms", description = "Returns list of rooms specified in the filter")
    @APIResponses({
            @APIResponse(description = "List of rooms", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @RolesAllowed("user")
    @GET
    public Response getRooms(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        List<Room> rooms = roomsBean.getRooms(query);

        return Response.
                ok(rooms).
                header("X-Total-Count", rooms.size()).
                build();
    }

    @Operation(summary = "Get a signle room", description = "Returns a single room")
    @APIResponses({
            @APIResponse(description = "List of rooms", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @GET
    @Path("{id}")
    public Response getRoom(@PathParam("id") Integer roomId){
        Room room = roomsBean.getRoom(roomId);
        return Response.ok(room).build();
    }

    @Operation(summary = "Delete a room", description = "Deletes a room")
    @APIResponses({
            @APIResponse(description = "Room deleted", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @DELETE
    @Path("{id}")
    public Response deleteRoom(@PathParam("id") Integer roomID){
        roomsBean.deleteRoom(roomID);
        return Response.noContent().build();
    }

    @Operation(summary = "Create room", description = "Creates room with an entrance")
    @APIResponses({
            @APIResponse(description = "New room created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Room.class)))
    })
    @RolesAllowed("admin")
    @POST
    public Response createRoomWithEntrances(@RequestBody(
            description = "DTO for adding rooms", required = true,
            content = @Content(schema = @Schema(implementation = Room.class)))
                                    AddRoomWithEntrancesDTO roomWithEntrancesDTO){

        Room room = roomManagerBean.addRoomWithEntrances(roomWithEntrancesDTO);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @Operation(summary = "Get entrance for room", description = "Returns an entrance for a room")
    @APIResponses({
            @APIResponse(description = "New entrance", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Room.class))) //a kle mogoce Entrance.class
    })
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

    @Operation(summary = "Get state for room", description = "Returns a state for a room")
    @APIResponses({
            @APIResponse(description = "New state", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Room.class))) //a kle mogoce State.class
    })
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
