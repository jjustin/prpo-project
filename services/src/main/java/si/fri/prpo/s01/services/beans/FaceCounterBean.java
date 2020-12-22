package si.fri.prpo.s01.services.beans;

import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.dtos.FaceCounterApiResponse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@RecordCalls
@ApplicationScoped
public class FaceCounterBean {
    private Logger log = Logger.getLogger(OccupancyRateBean.class.getName());

    Client client;
    String baseurl;

    @PostConstruct
    public void init() {
        log.info("Initializing bean" + FaceCounterBean.class.getSimpleName());

        client = ClientBuilder.newClient();
        baseurl = System.getenv("RAPID_API_HOST");
    }

    @PreDestroy
    public void remove() {
        log.info("Destroying bean" + FaceCounterBean.class.getSimpleName());
    }


    public int countFromPicture(String imgurl){
        String endpoint = "https://"+ baseurl+"/faceSearch/detectFaces";
        log.info("Calling "+ endpoint);

        try{
           FaceCounterApiResponse resp  = client
                .target(endpoint)
                .queryParam("objecturl", imgurl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("content-type", "application/x-www-form-urlencoded")
                .header("x-rapidapi-key", System.getenv("RAPID_API_KEY"))
                .header("x-rapidapi-host", baseurl)
                   .post(Entity.form(new Form("objecturl", imgurl)))
                   .readEntity(FaceCounterApiResponse.class);
           log.info("Got response with message: " + resp.getMessage());
           return resp.len();
        }catch (Exception ex){
            log.severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
}
