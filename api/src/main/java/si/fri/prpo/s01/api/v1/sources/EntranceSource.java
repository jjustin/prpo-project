package si.fri.prpo.s01.api.v1.sources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.OccupancyRateBean;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;
import si.fri.prpo.s01.services.dtos.PeopleEnterDTO;
import si.fri.prpo.s01.services.exceptions.InvalidNumberOfPeopleException;

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

    @Inject
    private OccupancyRateBean occupancyRateBean;


    @Operation(summary = "Get list of entrances", description = "Returns list of entrances specified in the filter")
    @APIResponses({
            @APIResponse(description = "List of entraces",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Entrance.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of all enrances")}
                    )
    })
    //@RolesAllowed("user")
    @GET
    public Response getEntrances(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        List<Entrance> entrances = entrancesBean.getEntrances(query);

        return Response.
                ok(entrances).
                header("X-Total-Count", entrancesBean.countEntrances(query)).
                build();
    }


    @Operation(summary = "Get a single entrance", description = "Returns a single entrance")
    @APIResponses({
            @APIResponse(description = "List of entrances", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @GET
    @Path("{id}")
    public Response getEntrance(@PathParam("id") Integer entranceId){
        Entrance entrance = entrancesBean.getEntrance(entranceId);
        return Response.ok(entrance).build();
    }

    @Operation(summary = "Delete an entrance", description = "Deletes an entrance")
    @APIResponses({
            @APIResponse(description = "Entrance deleted", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @DELETE
    @Path("{id}")
    public Response deleteEntrance(@PathParam("id") Integer entranceID){
        entrancesBean.deleteEntrance(entranceID);
        return Response.noContent().build();
    }

    @Operation(summary = "Create an entrance", description = "Creates a new entrance")
    @APIResponses({
            @APIResponse(description = "New entrance created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @POST
    public Response createEntrance(@RequestBody(
            description =  "Info of entrance to add",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Entrance.class, type = SchemaType.OBJECT)
            )) Entrance entrance){

        entrance = entrancesBean.addEntrance(entrance);
        return Response.status(Response.Status.CREATED).entity(entrance).build();
    }

    @Operation(summary = "Number of people entered", description = "Counts people that entered")
    @APIResponses({
            @APIResponse(description = "new number of people in the room", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @POST
    @Path("{id}/enter")
    public Response peopleEnter(@PathParam("id") Integer entranceID,
                                @RequestBody(
                                        description =  "Info of entrance to add",
                                        required = true,
                                        content = @Content(
                                                schema = @Schema(implementation = PeopleEnterDTO.class)
                                        ))PeopleEnterDTO peopleEnterDTO) {
        peopleEnterDTO.setEntranceId(entranceID);
        State state = occupancyRateBean.peopleEnter(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(state.getEntrance()).build();
    }

    @Operation(summary = "Number of people leave", description = "Counts people that left")
    @APIResponses({
            @APIResponse(description = "new number of people that left", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @POST
    @Path("{id}/exit")
    public Response peopleExit(@PathParam("id") Integer entranceID, PeopleEnterDTO peopleEnterDTO) throws Exception{
        peopleEnterDTO.setEntranceId(entranceID);
        State state = occupancyRateBean.peopleEnter(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(state.getEntrance()).build();
    }

}
