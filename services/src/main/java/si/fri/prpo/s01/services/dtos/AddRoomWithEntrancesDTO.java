package si.fri.prpo.s01.services.dtos;

import java.util.ArrayList;
import java.util.List;

public class AddRoomWithEntrancesDTO {
    String roomName;
    Integer roomSize;
    String owner;
    List<String> entrancesNames = new ArrayList<>();

    public AddRoomWithEntrancesDTO(String roomName, Integer roomSize, String owner) {
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.owner = owner;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
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
}