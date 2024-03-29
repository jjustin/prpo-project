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
import si.fri.prpo.s01.services.beans.EntrancesBean;
import si.fri.prpo.s01.services.beans.OccupancyRateBean;
import si.fri.prpo.s01.services.dtos.PeopleChangeDTO;
import si.fri.prpo.s01.services.dtos.PeopleChangeFromImageDTO;

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

    @GET
    @Operation(summary = "Get list of entrances", description = "Returns list of entrances specified in the filter")
    @APIResponses({
            @APIResponse(description = "List of entraces",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Entrance.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of all enrances")}
                    )
    })
    public Response getEntrances(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();

        List<Entrance> entrances = entrancesBean.getEntrances(query);

        return Response.
                ok(entrances).
                header("X-Total-Count", entrancesBean.countEntrances(query)).
                build();
    }

    @GET
    @Operation(summary = "Get a single entrance", description = "Returns a single entrance")
    @APIResponses({
            @APIResponse(description = "List of entrances", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @Path("{id}")
    public Response getEntrance(@PathParam("id") Integer entranceId){
        Entrance entrance = entrancesBean.getEntrance(entranceId);
        return Response.ok(entrance).build();
    }

    @DELETE
    @Operation(summary = "Delete an entrance", description = "Deletes an entrance")
    @APIResponses({
            @APIResponse(description = "Removed entrance's ID", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    @Path("{id}")
    public Response deleteEntrance(@PathParam("id") Integer entranceID){
        entrancesBean.deleteEntrance(entranceID);
        return Response.ok(entranceID).build();
    }

    @POST
    @Operation(summary = "Create an entrance", description = "Creates a new entrance")
    @APIResponses({
            @APIResponse(description = "New entrance created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    public Response createEntrance(@RequestBody(
            description =  "Info of entrance to add",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Entrance.class, type = SchemaType.OBJECT)
            )) Entrance entrance){

        entrance = entrancesBean.addEntrance(entrance);
        return Response.status(Response.Status.CREATED).entity(entrance).build();
    }

    @POST
    @Operation(summary = "Number of people entered", description = "Counts people that entered")
    @APIResponses({
            @APIResponse(description = "new number of people in the room", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Entrance.class)))
    })
    @Path("{id}/change")
    public Response peopleChange(@PathParam("id") Integer entranceID,
                                @RequestBody(
                                        description =  "Info of people entering",
                                        required = true,
                                        content = @Content(
                                                schema = @Schema(implementation = PeopleChangeDTO.class)
                                        )) PeopleChangeDTO peopleEnterDTO) {
        peopleEnterDTO.setEntranceId(entranceID);
        Entrance entrance = occupancyRateBean.peopleChange(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(entrance).build();
    }

    @POST
    @Path("{id}/changeFromImage")
    public Response peopleChangeFromImage(@PathParam("id") Integer entranceID,
                                 @RequestBody(
                                         description =  "Info of people entering",
                                         required = true,
                                         content = @Content(
                                                 schema = @Schema(implementation = PeopleChangeFromImageDTO.class)
                                         )) PeopleChangeFromImageDTO peopleEnterDTO) {
        peopleEnterDTO.setEntranceId(entranceID);
        Entrance entrance = occupancyRateBean.peopleChangeFromImageUrl(peopleEnterDTO);
        return Response.status(Response.Status.CREATED).entity(entrance).build();
    }
}
