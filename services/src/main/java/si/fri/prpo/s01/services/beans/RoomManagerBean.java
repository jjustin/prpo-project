package si.fri.prpo.s01.services.beans;

import si.fri.prpo.s01.entities.Entrance;
import si.fri.prpo.s01.entities.Room;
import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
