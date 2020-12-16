package si.fri.prpo.s01.services.dtos;

public class ChangeCheckDTO {
    private Integer roomId;
    private Integer numberOfPpl;
    private Integer inRoom;

    public Integer getNumberOfPpl() {
        return numberOfPpl;
    }

    public void setNumberOfPpl(Integer numberOfPpl) {
        this.numberOfPpl = numberOfPpl;
    }

    public Integer getInRoom() {
        return inRoom;
    }

    public void setInRoom(Integer inRoom) {
        this.inRoom = inRoom;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
