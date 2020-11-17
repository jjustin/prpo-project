package si.fri.prpo.s01.services.beans;

import si.fri.prpo.s01.entitete.Entrance;
import si.fri.prpo.s01.entitete.Room;
import si.fri.prpo.s01.services.dtos.AddRoomWithEntrancesDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        room.setInRoom(0);
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
