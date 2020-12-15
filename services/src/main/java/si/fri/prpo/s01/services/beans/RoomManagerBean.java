package si.fri.prpo.s01.services.beans;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.auth.login.Configuration;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RecordCalls
@ApplicationScoped
public class RoomManagerBean {
    @Inject
    private RoomsBean roomsBean;

    @Inject
    private EntrancesBean entrancesBean;


    @Transactional
    public Room addRoomWithEntrances(AddRoomWithEntrancesDTO in){
        Room room = new Room();
        room.setName(in.getRoomName());
        room.setOwner(in.getOwner());
        room.setInRoom(in.getInRoom());
        room.setSize(in.getRoomSize());

        List<Entrance> entrances = new ArrayList<Entrance>();
        room.setEntranceList(entrances);
        room = roomsBean.addRoom(room);

        for (String entranceName: in.getEntrancesNames()){
            Entrance entrance = new Entrance();
            entrance.setName(entranceName);
            entrance.setRoom(room);

            entrance = entrancesBean.addEntrance(entrance);
            entrances.add(entrance);
        }

        return room;
    }
}
