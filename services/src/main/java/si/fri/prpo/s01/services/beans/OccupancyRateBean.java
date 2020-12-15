package si.fri.prpo.s01.services.beans;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.entitete.State;
import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.dtos.ChangeCheckDTO;
import si.fri.prpo.s01.services.dtos.PeopleEnterDTO;
import si.fri.prpo.s01.services.exceptions.InvalidNumberOfPeopleException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.sql.Time;
import java.sql.Date;
import java.util.UUID;
import java.util.logging.Logger;

@RecordCalls
@ApplicationScoped
public class OccupancyRateBean {
    private String uuid = UUID.randomUUID().toString();
    private Logger log = Logger.getLogger(OccupancyRateBean.class.getName());

    @Inject
    private EntrancesBean entrancesBean;

    @Inject
    private RoomsBean roomsBean;

    @Inject
    private StatesBean statesBean;

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void Init(){
        log.info("Initializing bean " + OccupancyRateBean.class.getSimpleName() + " with id: " + uuid);
        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("integrations.history-system.base-url")
                .orElse("http://localhost:8081/v1");
    }
    @PreDestroy
    private void Remove(){
        log.info("Destroying bean " + OccupancyRateBean.class.getSimpleName()+ " with id: " + uuid);
    }


    @Transactional
    public State peopleEnter(PeopleEnterDTO pe){
        Entrance entrance = entrancesBean.getEntrance(pe.getEntranceId());
        Room room = entrance.getRoom();

        ChangeCheckDTO cc =new ChangeCheckDTO();
        cc.setRoomId(room.getId());cc.setNumberOfPpl(pe.getNumber());
        if (!canMoreEnter(cc)){
            throw new InvalidNumberOfPeopleException("Too many people would enter");
        }

        // update room
        Integer newInRoom = room.getInRoom() +  pe.getNumber();
        room.setInRoom(newInRoom);
        roomsBean.updateRoom(room.getId(), room);
        
        // create new state
        State state = new State();

        state.setEntrance(entrance);
        entrance.getStateList().add(state);
        state.setCurrentlyIn(newInRoom);
        state.setNumberIn(pe.getNumber());
        state.setDate(new Date(System.currentTimeMillis()));
        state.setTime(new Time(System.currentTimeMillis()));

        state = statesBean.addState(state);

        return state;
    }

    @Transactional
    public State peopleExit(PeopleEnterDTO pe) throws InvalidNumberOfPeopleException {
        Entrance entrance = entrancesBean.getEntrance(pe.getEntranceId());
        Room room = entrance.getRoom();

        ChangeCheckDTO cc =new ChangeCheckDTO();

        cc.setRoomId(room.getId());cc.setNumberOfPpl(pe.getNumber());
        if (!canExit(cc)){
            throw new InvalidNumberOfPeopleException("Too many people would exit");
        }

        // update room
        Integer newInRoom = room.getInRoom() - pe.getNumber();
        room.setInRoom(newInRoom);
        roomsBean.updateRoom(room.getId(), room);

        // create new state
        State state = new State();

        state.setEntrance(entrance);
        entrance.getStateList().add(state);
        state.setCurrentlyIn(newInRoom);
        state.setNumberOut(pe.getNumber());
        state.setDate(new Date(System.currentTimeMillis()));
        state.setTime(new Time(System.currentTimeMillis()));

        state = statesBean.addState(state);

        updateHistory(cc);

        return state;
    }

    public Boolean canMoreEnter(ChangeCheckDTO cc){
        Room room = roomsBean.getRoom(cc.getRoomId());

        return room.getInRoom() + cc.getNumberOfPpl() <= room.getSize();
    }

    public Boolean canExit(ChangeCheckDTO cc) {
        Room room = roomsBean.getRoom(cc.getRoomId());

        return room.getInRoom()  >= cc.getNumberOfPpl();
    }

    private void updateHistory(ChangeCheckDTO change){
        try {
            httpClient.target(baseUrl + "/history")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(change));
        } catch (WebApplicationException | ProcessingException e){
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }


}
