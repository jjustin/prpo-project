package si.fri.prpo.s01.services.beans;

import si.fri.prpo.s01.entities.Entrance;
import si.fri.prpo.s01.entities.Room;
import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RecordCalls
@ApplicationScoped
public class RoomManagerBean {
    private Logger log = Logger.getLogger(OccupancyRateBean.class.getName());

    @Inject
    private RoomsBean roomsBean;

    @Inject
    private EntrancesBean entrancesBean;

    @PostConstruct
    public void init() {
        log.info("Initializing bean" + RoomManagerBean.class.getSimpleName());
    }

    @PreDestroy
    public void remove() {
        log.info("Destroying bean" + RoomManagerBean.class.getSimpleName());
    }

    @Transactional
    public Room addRoomWithEntrances(AddRoomWithEntrancesDTO in){
        Room room = new Room();
        room.setName(in.getName());
        room.setOwner(in.getOwner());
        room.setInRoom(in.getInRoom());
        room.setSize(in.getSize());

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
