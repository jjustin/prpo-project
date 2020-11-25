package si.fri.prpo.s01.services.dtos;

import java.util.ArrayList;
import java.util.List;

public class AddRoomWithEntrancesDTO {
    String name;
    Integer size;
    Integer inRoom;
    String owner;
    List<String> entrancesNames = new ArrayList<>();

    public AddRoomWithEntrancesDTO(){}
    public AddRoomWithEntrancesDTO(String name, Integer size, String owner) {
        this.name = name;
        this.size = size;
        this.owner = owner;
    }

    public String getRoomName() {
        return name;
    }

    public void setRoomName(String name) {
        this.name = name;
    }

    public Integer getRoomSize() {
        return size;
    }

    public void setRoomSize(Integer size) {
        this.size = size;
    }

    public List<String> getEntrancesNames() {
        return entrancesNames;
    }

    public void setEntrancesNames(List<String> entrancesNames) {
        this.entrancesNames = entrancesNames;
    }

    public void addEntrance(String name){
        entrancesNames.add(name);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getInRoom() {
        return inRoom;
    }

    public void setInRoom(Integer inRoom) {
        this.inRoom = inRoom;
    }
}